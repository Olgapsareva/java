package geekbrains.lesson9;

import java.time.YearMonth;

public class Robot implements Movable {

    private String model;
    private String id;
    private int yearOfManufacture;
    private int runLimit;
    private float jumpLimit;
    static int count = 23456;

    public Robot(String model) {
        this.model = model;
        //TODO id должен присвоиваться как random из 16-ричной системы
        id = Integer.toHexString(++count);
        yearOfManufacture = YearMonth.now().getYear();
        runLimit = (int)(Math.random()*100+20);
        jumpLimit = (float) (Math.random()*2) + 0.5f;
    }
    @Override
    public boolean jump(float height) {

        boolean result = height <= jumpLimit ? true:false;
        String message = result ? " перепрыгнул препятствие":" не перерыгнул препятствие";
        System.out.println("Робот " + model + message);
        return result;
    }

    @Override
    public boolean run(int distance) {

        boolean result = distance <= runLimit ? true:false;
        String message = result? " пробежал дистанцию":" не осилил дистанцию";
        System.out.println("Робот " + model + message);
        return result;


    }

    public void printInfo(){
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "Robot{" +
                "model='" + model + '\'' +
                ", id=" + id +
                ", yearOfManufacture=" + yearOfManufacture +
                ", runLimit=" + runLimit +
                ", jumpLimit=" + jumpLimit +
                '}';
    }
}
