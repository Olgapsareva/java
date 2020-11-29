//6. ** Написать метод, в который передается не пустой одномерный целочисленный массив,
// метод должен вернуть true, если в массиве есть место, в котором сумма левой и правой части массива равны.
// Примеры: checkBalance([2, 2, 2, 1, 2, 2, || 10, 1]) → true,
// checkBalance([1, 1, 1, || 2, 1]) → true,
// граница показана символами ||, эти символы в массив не входят.

package geekbrains.lesson2;

import java.util.Arrays;

public class Task6 {
    public static void main(String[] args) {
        int[] numbers1 = {1, 1, 1, 1};
        int[] numbers2 = {1, 1, 1, 2};
        int[] numbers3 = {1, 0, 1, 2};
        int[] numbers4 = {0, 0, 6, 1, 2, 4};
        int[] numbers5 = {2, 2, 2, 1, 2, 2, 10, 1};
        int[] numbers6 = {1, 1, 1, 2, 1};

        System.out.println(Arrays.toString(numbers1) + "-->" + checkBalance(numbers1));
        System.out.println(Arrays.toString(numbers2) + "-->" + checkBalance(numbers2));
        System.out.println(Arrays.toString(numbers3) + "-->" + checkBalance(numbers3));
        System.out.println(Arrays.toString(numbers4) + "-->" + checkBalance(numbers4));
        System.out.println(Arrays.toString(numbers5) + "-->" + checkBalance(numbers5));
        System.out.println(Arrays.toString(numbers6) + "-->" + checkBalance(numbers6));

    }

    private static boolean checkBalance(int[] numArray){
       for (int i = 0; i < numArray.length; i++) {
            if (sumUp(numArray, 0, i+1) == sumUp(numArray, i+1, numArray.length))
                return true;
        }
        return false;


    }

    private static int sumUp(int[] numArray, int start, int end) {
        int sum = 0;
        for (int i = start; i < end; i++) {
            sum += numArray[i];
        }
        return sum;
    }
}
