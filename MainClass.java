/*Организуем гонки:
+Все участники должны стартовать одновременно, несмотря на то, что на подготовку у каждого их них уходит разное время.
+В тоннель не может заехать одновременно больше половины участников (условность).
+Попробуйте все это синхронизировать.
+Только после того, как все завершат гонку, нужно выдать объявление об окончании.
Можете корректировать классы (в т.ч. конструктор машин) и добавлять объекты классов из пакета util.concurrent.*/


package lesson5;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class MainClass {

    public static final int CARS_COUNT = 4;

    public static void main(String[] args) {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");

        CyclicBarrier cyclicBarrier = new CyclicBarrier(CARS_COUNT+1); //синхронизируем главный поток и потоки машин
        CountDownLatch countDownСars = new CountDownLatch(CARS_COUNT);
        Semaphore semaphore = new Semaphore(MainClass.CARS_COUNT/2);

        Race race = new Race(new Road(60), new Tunnel(semaphore), new Road(40));
        Car[] cars = new Car[CARS_COUNT];

        //создаем машины
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10),cyclicBarrier, countDownСars);
        }
        //стартуем, нужно подождать пока все потоки запустятся
        for (int i = 0; i < cars.length; i++) {
            new Thread(cars[i]).start();
        }
        try {
            cyclicBarrier.await(); 
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
            countDownСars.await(); //главный поток ждет пока закончат работу потоки машин
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}




