/*Создать три потока,
каждый из которых выводит определенную букву (A, B и C) 5 раз (порядок – ABСABСABС).
Используйте wait/notify/notifyAll.*/

package lesson4;

public class ABC {
    private final Object monitor = new Object();
    private volatile char currentLetter = 'A';

    public void printA(){
        synchronized (monitor) {
            for (int i = 0; i < 5; i++) {
                while (currentLetter != 'A') {
                    try {
                        monitor.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print('A');
                currentLetter = 'B';
                monitor.notifyAll();
            }
        }
    }
    public void printB(){
        synchronized (monitor) {
            for (int i = 0; i < 5; i++) {
                while (currentLetter != 'B') {
                    try {
                        monitor.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print('B');
                currentLetter = 'C';
                monitor.notifyAll();
            }
        }
    }
    public void printC(){
        synchronized (monitor) {
            for (int i = 0; i < 5; i++) {
                while (currentLetter != 'C') {
                    try {
                        monitor.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print('C');
                currentLetter = 'A';
                monitor.notifyAll();
            }
        }
    }

    public static void main(String[] args) {
        ABC abc = new ABC();
        new Thread(()->{
            abc.printA();
        }).start();

        new Thread(()->{
            abc.printB();
        }).start();

        new Thread(()->{
            abc.printC();
        }).start();
    }


}
