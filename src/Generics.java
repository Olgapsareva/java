/*1. Написать метод, который меняет два элемента массива местами (массив может быть любого ссылочного типа);
2. Написать метод, который преобразует массив в ArrayList;

3. Большая задача:
    +Есть классы Fruit -> Apple, Orange (больше фруктов не надо);
    +(вес яблока – 1.0f, апельсина – 1.5f. Не важно, в каких это единицах);
    +Класс Box, в который можно складывать фрукты.
    +Коробки условно сортируются по типу фрукта, поэтому в одну коробку нельзя сложить и яблоки, и апельсины;
    +Для хранения фруктов внутри коробки можно использовать ArrayList;
    +Сделать метод getWeight(), который высчитывает вес коробки, зная количество фруктов и вес одного фрукта
    +Внутри класса Коробка сделать метод compare, который позволяет сравнить текущую коробку с той,
    которую подадут в compare в качестве параметра,
    true – если она равны по весу, false – в противном случае
    (коробки с яблоками мы можем сравнивать с коробками с апельсинами);
    +Написать метод, который позволяет пересыпать фрукты из текущей коробки в другую (помним про сортировку фруктов:
    нельзя яблоки высыпать в коробку с апельсинами).
    +Соответственно, в текущей коробке фруктов не остается, а в другую перекидываются объекты, которые были в этой коробке;
    +Не забываем про метод добавления фрукта в коробку.*/


package lesson1;

import java.util.ArrayList;


public class Generics {


    public static void main(String[] args) {

        Integer[] nums = {1,2,3,4,5,6};
        String[] words = {"one", "two", "three"};
        swapElements(nums, 0,nums.length-1);
        swapElements(words, 0,words.length-1);
        ArrayList<Integer> newNums = convertArraytoList(nums);
        ArrayList<String> newWords = convertArraytoList(words);

    }

    private static <T> T[] swapElements(T[] array, int firstElemPos, int secondElemPos) {
        T temp = array[firstElemPos];
        array[firstElemPos] = array[secondElemPos];
        array[secondElemPos] = temp;
        return array;
    }

    private static <T> ArrayList<T> convertArraytoList(T[] array) {

        ArrayList<T> newArrList = new ArrayList<>();
        for (T element : array) {
            newArrList.add(element);
        }
        return newArrList;

    }
}
