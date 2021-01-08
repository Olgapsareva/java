/*1. Создать массив с набором слов (10-20 слов, среди которых должны встречаться повторяющиеся).
    Найти и вывести список уникальных слов, из которых состоит массив (дубликаты не считаем).
    Посчитать, сколько раз встречается каждое слово.
*/

package geekbrains.lesson11;

import java.util.HashMap;
import java.util.Map;

public class Task1 {

    static String[] animals = {"cat", "dog", "giraffe", "wolf", "cat", "rat",
            "cat", "cat", "wolf", "elephant", "horse", "dog",
            "rat","cat", "horse"};

    public static void main(String[] args) {

        System.out.println(countedAnimals());

    }

    private static Map<String, Integer> countedAnimals() {
        Map<String, Integer> countedAnimals = new HashMap<>();

        for (String animal : animals) {
            countedAnimals.put(animal, increaseValue(countedAnimals.get(animal)));
        }
        return countedAnimals;
    }

    private static Integer increaseValue(Integer count) {
        if(count == null){
            return 1;
        }
            return count +1;

    }




}
