/*2. Написать метод, которому в качестве аргумента передается не пустой одномерный целочисленный массив.
Метод должен вернуть новый массив, который получен путем вытаскивания из исходного массива элементов,
    идущих после последней четверки.
Входной массив должен содержать хотя бы одну четверку,
   иначе в методе необходимо выбросить RuntimeException.
Написать набор тестов для этого метода (по 3-4 варианта входных данных).
   Вх: [ 1 2 4 4 2 3 4 1 7 ] -> вых: [ 1 7 ].
3. Написать метод, который проверяет состав массива из чисел 1 и 4.
Если в нем нет хоть одной четверки или единицы, то метод вернет false;
Написать набор тестов для этого метода (по 3-4 варианта входных данных).*/


package lesson6;

import java.util.Arrays;

public class ArrayExtractor {
    public static void main(String[] args) {
        Integer[] nums = {1,2,4,4,2,3,4,1,7};
        //Integer[] nums = {1,2,2,3,0,1,7};
        Integer[] newNums = extractArrayAfterGivenNumber(nums, 4);
        System.out.println(Arrays.toString(newNums));

    }

    private static Integer[] extractArrayAfterGivenNumber(Integer[] nums, int i) {
        int lastIndexOf = getLastIndexOf(nums, i);
        if (lastIndexOf != -1) {
            return Arrays.copyOfRange(nums, lastIndexOf+1, nums.length);
        } else {
            throw new MyRuntimeException("no such element");
        }
    }

    private static int getLastIndexOf(Integer[] nums, int i) {
        return Arrays.asList(nums).lastIndexOf(i);
    }
}
