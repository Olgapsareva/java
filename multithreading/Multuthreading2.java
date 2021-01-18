package geekbrains.lesson13;

import java.util.Arrays;

public class Multuthreading2 {

    public static void main(String[] args) {
        MyThread3 myThread3 = new MyThread3();
        myThread3.start();
        MyThread4 myThread4 = new MyThread4();
        myThread4.start();

    }
}

class MyThread3 extends Thread{
    static final int size = 10000000;

    @Override
    public void run() {
        float[] arr = createArray();
        long start = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.out.println("MyThread1: " + getPerformanceTime(start));

    }

    protected float getPerformanceTime(long start) {
        long deltaTime = System.currentTimeMillis() - start;
        return ((float) deltaTime/1000.0f);
    }

    protected float[] createArray() {
        float[] arr = new float[size];
        Arrays.fill(arr, 1);
        return arr;
    }
}

class MyThread4 extends MyThread3{
    static final int h = size / 2;

    @Override
    public void run() {
        float[] arr = createArray();
        long start = System.currentTimeMillis();

        float[] a1 = new float[h];
        float[] a2 = new float[h];

        System.arraycopy(arr, 0, a1, 0, h);
        System.arraycopy(arr, h, a2, 0, h);


        Thread subThread = new Thread(() -> {

            for (int i = 0; i < h; i++) {
                a1[i] = (float)(a1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
            System.arraycopy(a1, 0, arr, 0, h);

        });
        subThread.start();

        for (int i = 0; i < h; i++) {
            a2[i] = (float)(a2[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.arraycopy(a2, 0, arr, h, h);
        try {
            subThread.join(); //ждет пока завершится subThread
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("MyThread2: " + getPerformanceTime(start));
    }
}
