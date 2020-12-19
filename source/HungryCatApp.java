/* Исходное задание
1. Расширить задачу про котов и тарелки с едой
+ Сделать так, чтобы в тарелке с едой не могло получиться отрицательного количества еды
    (например, в миске 10 еды, а кот пытается покушать 15-20)
+ Каждому коту нужно добавить поле сытость (когда создаем котов, они голодны).
    Если коту удалось покушать (хватило еды), сытость = true
+. Считаем, что если коту мало еды в тарелке, то он ее просто не трогает, то есть не может быть наполовину сыт
    (это сделано для упрощения логики программы)
+. Создать массив котов и тарелку с едой, попросить всех котов покушать из этой тарелки
    и потом вывести информацию о сытости котов в консоль
+ Добавить в тарелку метод, с помощью которого можно было бы добавлять еду в тарелку

 */

//Улучшения:
// +котик съедает всю еду которая есть в тарелке,
// +котик может быть наполовину голодным,
// +еда появляется рандомно по прошествиии х секунд
// +котик голодает со временем
//+цикл длится ограниченное время

package geekbrains.lesson7;

import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class HungryCatApp {

    static Random random = new Random();
    static Timer timer = new Timer("Timer");

    public static void main(String[] args) {

        Cat cat1 = new Cat("Том", random.nextInt(10)+1);
        Cat cat2 = new Cat("Фича", random.nextInt(10)+1);
        Cat cat3 = new Cat("Сильвестр", random.nextInt(10)+1);
        Cat cat4 = new Cat("Барсик", random.nextInt(10)+1);

        Cat[] cats = {cat1, cat2, cat3, cat4};

        Plate plate = new Plate(); //по дефолту в миске 10 еды.
        plate.printInfo();


        TimerTask task1 = new TimerTask() {
            @Override
            public void run() {
                feedCats(cats, plate);
                System.out.println("Task 1 performed on " + new Date());
            }
        };

        TimerTask task2 = new TimerTask() {
            public void run() {
                changeAppetiteAndSatiety(cats);
                //System.out.println("Task 2 performed on " + new Date());
            }

        };

        TimerTask task3 = new TimerTask() {
            public void run() {
                plate.addFood(random.nextInt(7));
                plate.printInfo();
                //System.out.println("Task 3 performed on " + new Date());
            }

        };

        //кормим котов каждые 4 сек, коты голодают каждые 2 сек, еда в миске появляется рандомно: от 1 до 3 сек
        timer.scheduleAtFixedRate(task1, 1000, 4000);
        timer.scheduleAtFixedRate(task2, 1000, 2000);
        timer.scheduleAtFixedRate(task3, 2000, (int)(Math.random()*3)*1000+1000);

        //цикл голодания и кармежки длится 20 сек
        try {
            Thread.sleep(20000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        timer.cancel();
        System.out.println("\nкотики ушли спать " + new Date());


    }

    private static void feedCats(Cat[] cats, Plate plate) {
        for (Cat cat : cats) {
            if (cat.isHungry) {
                System.out.printf("%s, ему нужно %d еды для полной сытости%n", cat, cat.getAppetite());
                cat.eat(plate);
                System.out.printf("в тарелке осталось %d еды%n", plate.getFood());
                cat.printInfo();
                System.out.println("--------------");
            }

        }

    }

    private static void changeAppetiteAndSatiety(Cat[] cats) {
        for (Cat cat : cats) {
            if (cat.getAppetite() < cat.getMaxAppetite()) {
                cat.changeAppetite(-1);
            }
            if (cat.getSatiety() > 0) {
                cat.changeSatiety(-1);
            }
            cat.ifHunger();
        }
    }
}
