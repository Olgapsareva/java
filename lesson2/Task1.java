//1. Задать целочисленный массив, состоящий из элементов 0 и 1.
// Например: [ 1, 1, 0, 0, 1, 0, 1, 1, 0, 0 ].
// С помощью цикла и условия заменить 0 на 1, 1 на 0;

package geekbrains.lesson2;

import java.util.Arrays;

public class Task1 {

    public static void main(String[] args) {

        int[] numbers = {1, 0, 0, 0, 1, 1, 0, 1, 0, 1, 0, 0, 0, 1};
        changeElement(numbers);
        System.out.println(Arrays.toString(numbers));
    }

    private static int[] changeElement(int[] numArray) {
        for (int i = 0, len = numArray.length; i < len; i++) {
            if (numArray[i] == 1) numArray[i] = 0;
            else numArray[i] = 1;
        }
        return numArray;
    }
}
