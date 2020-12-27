/*1. Напишите метод, на вход которого подается двумерный строковый массив размером 4х4, 
   при подаче массива другого размера необходимо бросить исключение MyArraySizeException.
+. Далее метод должен пройтись по всем элементам массива, преобразовать в int, и просуммировать.
   Если в каком-то элементе массива преобразование не удалось
   (например, в ячейке лежит символ или текст вместо числа),
   должно быть брошено исключение MyArrayDataException – с детализацией, 
   в какой именно ячейке лежат неверные данные.
+. В методе main() вызвать полученный метод,
   обработать возможные исключения MySizeArrayException и MyArrayDataException и вывести результат расчета.*/

package geekbrains.lesson10;

public class Main {
    public static void main(String[] args) {
        String[][]  myArray = {{"1", "3", "6", "5"}, {"-10", "7", "13", "18"}, {"-34", "47", "1", "-46"}, {"-1", "2","-9", "8"}};
        try {
            System.out.println(sumArrayElements(myArray));
        } catch(MySizeArrayException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        catch (MyArrayDataException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            System.out.println("программа завершила работу");
        }

    }

    private static int sumArrayElements(String[][] inputArray) throws MyArrayDataException, MySizeArrayException {

        int sum = 0;
        for (int i = 0; i < inputArray.length; i++) {
            for (int j = 0; j < inputArray[i].length; j++) {
                try {
                    if (inputArray.length != 4 | inputArray[i].length != 4){
                        throw new MySizeArrayException("ошибка длинны массива");
                    }
                    sum += Integer.parseInt(inputArray[i][j]);
                } catch (NumberFormatException e) {
                    throw new MyArrayDataException(i, j, e);
                }
            }

        }
        return sum;
    }
}
