package geekbrains.lesson7;

public class Plate {

    private int food;

    public Plate(int food) {
        this.food = food;
    }

    public Plate() {
        this(10);
    }

    public int getFood() {
        return food;
    }

    public void addFood(int food) {
        this.food += food;
        System.out.printf("добавлено %d еды в тарелку%n", food);
    }
    public void addFood() {
        addFood(1);
    }


    // если еды в тарелке <= аппетиту кота, то он съедает всю еду
    // аппетит уменьшается на съеденную еду, а сытость - увеличивается

    //если еды в тарелке > аппетита кота, то он съедает еды == его аппетиту,
    //сытость увеличивается на съеденную еду (== аппетиту), аппетит уходит на 0

    protected void decreaseFood(Cat cat) {
        if (food > cat.getAppetite()) {
            food -= cat.getAppetite();
            cat.changeSatiety(cat.getAppetite());
            cat.changeAppetite();
        } else {
            cat.changeSatiety(food);
            cat.changeAppetite(food);
            food = 0;
        }
    }


    public void printInfo() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "Food in plate = "  + food;
    }
}
