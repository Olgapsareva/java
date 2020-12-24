package geekbrains.lesson9;

public class Wall extends Obstacles{

    public Wall(){
        size = (int) (Math.random()*2)+1;
    }

    public void printInfo(){

        System.out.println(this);
    }


    @Override
    public String toString() {
        return String.format("Wall height = %d",  size);
    }
}
