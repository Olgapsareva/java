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


import java.util.Arrays;
import java.util.List;

public class ArrayExtractor {

    public Integer[] extractArrayAfterGivenNumber(Integer[] nums, int i) {
        if (nums.length == 0){
            throw new MyRuntimeException("empty list");
        }
        int lastIndexOf = getLastIndexOf(nums, i);
        if (lastIndexOf != -1) {
            return Arrays.copyOfRange(nums, lastIndexOf+1, nums.length);
        } else {
            throw new MyRuntimeException("no such element");
        }
    }

    private int getLastIndexOf(Integer[] nums, int i) {
        return Arrays.asList(nums).lastIndexOf(i);
    }

    public boolean checkForNumbers(Integer[] nums, int a, int b) {
        List<Integer> arrAsList = Arrays.asList(nums);
        return (arrAsList.contains(a) || arrAsList.contains(b));
    }
}
