package geekbrains.lesson5;

public class Employee {
    protected String name;
    protected String jobTitle;
    protected String email;
    protected String phone;
    protected int salary;
    protected int age;

    public Employee(String name, String jobTitle, String email, String phone, int salary, int age){
        this.name = name;
        this.jobTitle = jobTitle.toLowerCase();
        this.email = email.toLowerCase();
        this.phone = phone;
        this.salary = salary;
        this.age = age;

        //System.out.println("Заведена карточка сотрудника: " + name);

    }

    void printInfo(){
        System.out.println(this);
        //System.out.printf("ФИО: %s%nДолжность: %s%nЭл.почта: %s%nТел.: %s%nЗ/П: %d руб%nвозраст: %d лет %n", name, jobTitle, email, phone, salary, age);
    }

    @Override
    public String toString(){
        return String.format("ФИО: %s%nДолжность: %s%nЭл.почта: %s%nТел.: %s%nЗ/П: %d руб%nвозраст: %d лет %n", name, jobTitle, email, phone, salary, age);
    }


}

