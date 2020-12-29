package geekbrains.lesson9;

public class RunningTack extends Obstacles{


    public RunningTack() {
        size = (int)(Math.random()*100+20);
    }

    public void printInfo(){

        System.out.println(this);
    }

    @Override
    public String toString() {
        return "RunningTack distance= " + size;
    }
}
