//5. ** Задать одномерный массив и найти в нем минимальный и максимальный элементы
// (без помощи интернета);

package geekbrains.lesson2;

import java.util.Arrays;

public class Task5 {
    public static void main(String[] args) {
        final int SIZE_OF_AN_ARRAY = 10;
        int[] numbers = randomArray(SIZE_OF_AN_ARRAY);
        System.out.println("Cгенерированный массив:\n" + Arrays.toString(numbers));
        System.out.println("Максимальное значение в массиве: " + findMax(numbers));
        System.out.println("Минимальное значение в массиве: " + findMin(numbers));

    }

    private static int[] randomArray(int size){
        int[] numArray = new int[size];
        for(int i = 0; i < size; i++){
            numArray[i] = -99+(int)(Math.random()*200);
        }
        return numArray;
    }

    private static int findMax(int[] numArray) {
        int max = numArray[0];
        for (int num : numArray){
            if (num > max) max = num;
        }
        return max;
    }

    private static int findMin(int[] numArray) {
        int min = numArray[0];
        for (int num : numArray){
            if (num < min) min = num;
        }
        return min;
    }
}
