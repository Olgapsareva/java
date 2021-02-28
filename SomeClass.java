package lesson7;

public class SomeClass {
    private final int MAX_VALUE = 10;
    private final int MIN_VALUE = 0;

    public int setPriority(int priority) {
        if(priority < MIN_VALUE || priority > MAX_VALUE){
            throw new IllegalArgumentException("приоритет должен быть задан целым числом от 0 до 10");
        }
        return priority;
    }

    @BeforeSuite
    public void beforeSuiteMethod(){
        System.out.println("before suite method");
    }


    @AfterSuite
    public void afterSuiteMethod(){
        System.out.println("after suite method");
    }


    @Test
    public void test1(){
        System.out.println("test1 method");
    }

    @Test(priority = 1)
    public void test2(){
        System.out.println("test2 method");
    }

    @Test(priority = 10)
    public void test3(){
        System.out.println("test3 method");
    }

    @Test
    public void test4(){
        System.out.println("test4 method");
    }
}
