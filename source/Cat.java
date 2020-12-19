package geekbrains.lesson7;

public class Cat {

    private String name;
    protected boolean isHungry;
    private int appetite;
    private int satiety;
    private final int MAX_APPETITE;


    public Cat(String name, int appetite){
        this.name = name;
        isHungry = true;
        this.appetite = appetite;
        MAX_APPETITE = appetite;
        satiety = 0;
    }

    public Cat(){
        this("no name", 10);
    }

    public int getAppetite() {
        return appetite;
    }

    public int getMaxAppetite() {
        return MAX_APPETITE;
    }

    public int getSatiety() {
        return satiety;
    }

    public void eat(Plate plate) {

        plate.decreaseFood(this);
        ifHunger();
        System.out.println("котик ест");

    }

    protected void changeAppetite(int food) {
        appetite -=food;
    }
    protected void changeAppetite() { //перегруженный метод: если не даем параметр, значит убираем аппетит на 0
        appetite = 0;
    }

    protected void changeSatiety(int food) {
        satiety +=food;
    }

    protected void ifHunger() {
        isHungry = (satiety >= appetite) ? false: true; //котик больше хочет есть или больше сыт?
    }

    public void printInfo() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        String hunger = (isHungry) ? "голоден":"не голоден";
        return "Котик " + name + " " + hunger;
    }
}
