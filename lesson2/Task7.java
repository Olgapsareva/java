//7. **** Написать метод, которому на вход подается одномерный массив и число n
//        (может быть положительным, или отрицательным),
//        при этом метод должен сместить все элементы массива на n позиций.
//        Для усложнения задачи нельзя пользоваться вспомогательными массивами.

package geekbrains.lesson2;

import java.util.Arrays;

public class Task7 {
    public static void main(String[] args) {
        int[] numbers = {1,2,3,4,5,6};
        final int SHIFT = 8;

        shiftElements(numbers, SHIFT);

        System.out.println(Arrays.toString(numbers));
        System.out.println("фактическое число итераций цикла " + Math.abs(SHIFT%numbers.length));
    }

    private static void shiftElements(int[] numArray, int shift) {
        int temporary;
        int iterations = Math.abs(shift % numArray.length);

        if (shift >= 0) {
            for (int i = 0; i < iterations; i++) {
                temporary = numArray[numArray.length - 1];
                for (int j = numArray.length - 1; j >= 0; j--) {
                    if (j == 0) {
                        numArray[j] = temporary;
                        break;
                    }
                    numArray[j] = numArray[j - 1];
                }
            }
        } else {
            for (int i = 0; i < iterations; i++) {
                temporary = numArray[0];
                for (int j = 0; j < numArray.length; j++) {
                    if (j == numArray.length - 1) {
                        numArray[j] = temporary;
                        break;
                    }
                    numArray[j] = numArray[j + 1];
                }
            }
        }
    }

}
