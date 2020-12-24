package geekbrains.lesson9;

public class Human implements Movable {

    private String name;
    private int age;
    private int runLimit;
    private float jumpLimit;


    public Human(String name,  int age) {
        this.name = name;
        this.age = age;
        runLimit = calculateRunLimit();
        jumpLimit = calculateJumpLimit();

    }

    private int calculateRunLimit() {
        if(age > 25){
            return (int)(Math.random()*100+20*0.5);
        } else {
            return (int)(Math.random()*100+20);
        }

    }

    private float calculateJumpLimit() {
        if(age > 25){
            return (float) (Math.random()*2) + 0.1f;
        } else {
            return (float) (Math.random()*2) + 0.5f;
        }

    }

    @Override
    public boolean jump(float height) {
        boolean result = height <= jumpLimit ? true:false;
        String message = result ? " перепрыгнул(-а) препятствие":" не перерыгнул препятствие";
        System.out.println(name + message);
        return result;
    }
    @Override
    public boolean run(int distance) {
        boolean result = distance <= runLimit ? true:false;
        String message = result? " пробежал(-а) дистанцию":" не осилил дистанцию";
        System.out.println(name + message);
        return result;
    }

    public void printInfo(){
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "Human{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", runLimit=" + runLimit +
                ", jumpLimit=" + jumpLimit +
                '}';
    }
}
