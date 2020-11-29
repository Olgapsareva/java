//3. Задать массив [ 1, 5, 3, 2, 11, 4, 5, 2, 4, 8, 9, 1 ]
// пройти по нему циклом, и числа меньшие 6 умножить на 2;

package geekbrains.lesson2;

import java.util.Arrays;

public class Task3 {
    public static void main(String[] args) {
        int[] numbers = {1, 5, 3, 2, 11, 4, 5, 2, 4, 8, 9, 1};
        multiplySmallerSix(numbers);
        System.out.println(Arrays.toString(numbers));

    }

    private static void multiplySmallerSix(int[] numArray) {
        for (int i = 0, len = numArray.length; i < len; i++) {
            if (numArray[i] < 6) numArray[i] *= 2;
        }

    }
}
