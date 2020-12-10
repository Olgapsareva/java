package geekbrains.lesson5;

public class Main {


    public static void main(String[] args) {

        Employee[] employees = new Employee[5];

        employees[0] = new Employee("Иванов Иван Иванович", "директор", "ivanov@mail.ru", "89191232323", 300000, 50 );
        employees[1] = new Employee("Смирнов Сергей Иванович", "Инженер", "smirnov@mail.ru", "89191232324", 200000, 35 );
        employees[2] = new Employee("Пупкин Василий Семенович", "курьер", "pupkin@mail.ru", "89191232325", 30000, 19 );
        employees[3] = new Employee("Иванова Мария Николаевна", "Бухгалтер", "ivanova@mail.ru", "89191232326", 2500000, 40 );
        employees[4] = new Employee("Петрова Анна Сергеевна", "Техдир", "petrova@mail.ru", "89191232327", 250000, 50 );

        showOver40(employees);


    }

    private static void showOver40(Employee[] employees) {
        for (Employee employee : employees) {
            if (employee.age > 40) {
                employee.printInfo();
            }
        }
    }
}
