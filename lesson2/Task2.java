//2. Задать пустой целочисленный массив размером 8.
// С помощью цикла заполнить его значениями 0 3 6 9 12 15 18 21;


package geekbrains.lesson2;

import java.util.Arrays;

public class Task2 {

    public static void main(String[] args) {
        final int SIZE_OF_AN_ARRAY = 8;
        int[] numbers = new int[SIZE_OF_AN_ARRAY];
        fillArray(numbers);
        System.out.println(Arrays.toString(numbers));
    }

    private static void fillArray(int[] numArray){

        for (int i = 0, len = numArray.length, j =0; i < len;  i++, j+=3){
            numArray[i] = j;
        }
    }
}
