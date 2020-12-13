package geekbrains.lesson6;

import java.util.Random;

public class Cat extends Animal{

    static final Random random = new Random();

    public Cat(String name, String color, int age){
        super(name, color, age);
        System.out.println("Котик " + name + " создан");
        type = "кот";
        runLimit =  random.nextInt(150)+100;
        jumpLimit = 1.5+Math.random()*1.3;
        swimLimit = 0;
    }

    public Cat(String name) {
        super(name);
    }

    public Cat(){
    }

    @Override
    public void run(int distance) {
        if(distance >0 && distance <= runLimit){
            System.out.printf("котик %s пробежал %d м%n" , getName(), distance );
        } else{
            System.out.printf("котик %s столько не бегает, максимум %d м%n", getName(), runLimit);
        }

    }

    @Override
    public void swim(int distance) {
        if(swimLimit >0){
            System.out.printf("котик %s проплыл %d м%n" , getName(), distance );
        }
            System.out.printf("котик %s утонул, так как не умел плавать :( %n", getName());

    }

    @Override
    public void jump(double height) {
        if(height >0 && height <= jumpLimit){
            System.out.printf("котик %s прыгнул на %.2f м%n" , getName(), height );
        } else{
            System.out.printf("котик %s так высоко не прыгнет, максимум %.2f м%n", getName(), jumpLimit);
        }

    }
}
