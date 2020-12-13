package geekbrains.lesson6;

public abstract class Animal {
    private String name;
    private String color;
    protected int age;
    protected String type; //так можно: не кидать переменные в конструктор через аргументы?
    protected int runLimit;
    protected double jumpLimit;
    protected int swimLimit;

    public Animal(String name, String color, int age){
        this.name = name;
        this.color = color;
        this.age = age >=0 ? age: 0;

    }

    public Animal(String name){
        this(name, "N/D", 0);
    }

    public Animal(){
        this("No name");
    }

    public abstract void run(int distance);
    public abstract void swim(int distance);
    public abstract void jump(double height);

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void printInfo(){
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "{" + "type= " + type +
                " name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", age=" + age +
                '}';
    }
}
