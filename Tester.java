package lesson7;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;


public class Tester {

    private static Method firsttMethodToInvoke = null;
    private static Method lastMethodToInvoke = null;
    private static List<Method> testMethods = new ArrayList<>();

    public static void startTest(Class classObj) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {

        Method[] allMethods = classObj.getDeclaredMethods();

        //сортируем методы по аннотациям:
        sortWithAnnotations(allMethods);

        //сортируем @Test-методы по приоритету:
        MethodComparator methodComparator = new MethodComparator();
        Collections.sort(testMethods, methodComparator);

        Constructor<SomeClass> constructor = classObj.getConstructor();
        SomeClass constructedObj = constructor.newInstance();
        InvokeMethods(testMethods, constructedObj);

    }

    public static void startTest(String className) throws InvocationTargetException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException {
        Class classObj = Class.forName(className);
        startTest(classObj);

    }

    private static void sortWithAnnotations(Method[] allMethods) {
        //List<Method> sortedMethods = new ArrayList<>();
        for (Method method : allMethods) {
            if(method.isAnnotationPresent(BeforeSuite.class) && firsttMethodToInvoke != null){
                throw new ExeedingAnnotationQuantityException();
            } else if(method.isAnnotationPresent(BeforeSuite.class) && firsttMethodToInvoke == null){
                firsttMethodToInvoke = method;
            }
            if(method.isAnnotationPresent(AfterSuite.class) && lastMethodToInvoke != null){
                throw new ExeedingAnnotationQuantityException();
            } else if(method.isAnnotationPresent(AfterSuite.class) && lastMethodToInvoke == null){
                lastMethodToInvoke = method;
            }
            if(method.isAnnotationPresent(Test.class)){
                testMethods.add(method);
            }
        }
        //return sortedMethods;
    }

    private static void InvokeMethods(List<Method> sortedMethods, SomeClass constructedObj) throws IllegalAccessException, InvocationTargetException {
        if(firsttMethodToInvoke != null){
            firsttMethodToInvoke.invoke(constructedObj);
        }
        for (Method method : sortedMethods) {
            method.invoke(constructedObj);
        }
        if(lastMethodToInvoke != null){
            lastMethodToInvoke.invoke(constructedObj);
        }
    }

    private static class MethodComparator implements Comparator<Method> {

        @Override
        public int compare(Method m1, Method m2) {
            return -(m1.getAnnotation(Test.class).priority() - m2.getAnnotation(Test.class).priority());
        }
    }
}

