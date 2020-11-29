//+ Создать пустой проект в IntelliJ IDEA и прописать метод main();
//+ Создать переменные всех пройденных типов данных, и инициализировать их значения;
//+ Написать метод вычисляющий выражение a * (b + (c / d)) и возвращающий результат,где a, b, c, d – входные параметры этого метода;
//+ Написать метод, принимающий на вход два числа, и проверяющий что их сумма лежит в пределах от 10 до 20(включительно), если да – вернуть true, в противном случае – false;
//+ Написать метод, которому в качестве параметра передается целое число, метод должен напечатать в консоль положительное ли число передали, или отрицательное; Замечание: ноль считаем положительным числом.
//+ Написать метод, которому в качестве параметра передается целое число, метод должен вернуть true, если число отрицательное;
//+ Написать метод, которому в качестве параметра передается строка, обозначающая имя, метод должен вывести в консоль сообщение «Привет, указанное_имя!»;
//+ * Написать метод, который определяет является ли год високосным, и выводит сообщение в консоль. Каждый 4-й год является високосным, кроме каждого 100-го, при этом каждый 400-й – високосный.


package geekbrains.lesson1;

public class Main {

    public static void main(String[] args) {
        int a = 1;
        float b = 4.5f;
        double c = 3.0;
        long d = 9_000_000_000L;
        String str = "this is a string";
        boolean flag = true;
        char letter = 'a';

//        System.out.println(doSomeCalc(3, 65, -34, 8));
//        System.out.println(isInBetween(1,12));
//        isPositive(-90);
//        System.out.println(isNegative(10));
//        sayHello("9");

        leapYear(0);
        leapYear(4);
        leapYear(100);
        leapYear(400);
        leapYear(109);
        leapYear(80);


    }


    private static int doSomeCalc(int a, int b, int c, int d) {
        if(d != 0) return a * (b + (c / d));
        else
            System.out.println("параметр d не может быть равен 0");
            return -1;
    }

    private static boolean isInBetween(int a, int b) {

        return a+b<=20 & a+b>=10;
    }

    private static void isPositive(int n) { //метод печатает в консоль, не возвращает, согласно заданию
        if(n>=0) System.out.println("число положительное");
        else System.out.println("число отрицательное");
    }

    private static boolean isNegative(int n) {

        return n<0;
    }

    private static void sayHello(String name) {

        System.out.println("Привет, " + name + "!");
    }

    private static void leapYear(int year) {
        String result = (year%100 == 0 && year%400 !=0 || year%4 !=0) ? "не високосный": "високосный";
        System.out.printf("год %s" + "\n",  result);

//        if (year%100 == 0 && year%400 !=0 || year%4 !=0) System.out.println("год не високосный");
//        else System.out.println("год високосный");
    }

}
