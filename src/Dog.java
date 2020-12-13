package geekbrains.lesson6;

import java.util.Random;

public class Dog extends Animal{

    Random random = new Random();

    public Dog(String name, String color, int age){
        super(name, color, age);
        System.out.println("Песель " + name + " создан");
        type = "собакен";
        runLimit = random.nextInt(200)+400;
        jumpLimit = 0.5 + Math.random();
        swimLimit = random.nextInt(5)+10;
    }

    public Dog(String name) {
        super(name);
    }

    public Dog(){
    }

    @Override
    public void run(int distance) {
        if(distance >0 && distance <= runLimit){
            System.out.printf("песель %s пробежал %d м%n" , getName(), distance );
        } else{
            System.out.printf("песель %s столько не бегает, максимум %d м%n", getName(), runLimit);
        }

    }

    @Override
    public void swim(int distance) {
        if(distance >0 && distance <= swimLimit){
            System.out.printf("песель %s проплыл %d м%n" , getName(), distance );
        } else{
            System.out.printf("песель %s столько не плавает, максимум %d м%n", getName(), swimLimit);
        }
    }

    @Override
    public void jump(double height) {
        if(height >0 && height <= jumpLimit){
            System.out.printf("песель %s прыгнул на %.2f м%n" , getName(), height );
        } else{
            System.out.printf("песель %s так высоко не прыгнет, максимум %.2f м%n", getName(), jumpLimit);
        }
    }
}
