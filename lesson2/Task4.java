//4. Создать квадратный двумерный целочисленный массив (количество строк и столбцов одинаковое),
// и с помощью цикла(-ов) заполнить его диагональные элементы единицами;


package geekbrains.lesson2;

public class Task4 {

    public static void main(String[] args) {
        final int SIZE_OF_AN_ARRAY = 5;
        int[][] two_D_Array = new int[SIZE_OF_AN_ARRAY][SIZE_OF_AN_ARRAY];

        //fillArray(two_D_Array); //можно заполнить массив произвольными числами (опционально), по умолчанию не используется
        System.out.println("Initial array:");
        printArrayAsMatrix(two_D_Array);

        changeValue(two_D_Array);


        System.out.println("Value changed array:");
        printArrayAsMatrix(two_D_Array);

    }

//    private static void fillArray(int[][] numArray){ // можно заполнить массив произвольными числами (опционально), по умолчанию не используется
//        for(int i = 0, leni = numArray.length; i <leni; i++){
//            for (int j = 0, lenj = numArray[i].length; j < lenj; j++){
//                numArray[i][j] = (int)(Math.random()*100);
//            }
//        }
//
//    }

    private static void changeValue(int[][] numArray){
        for(int i = 0, leni = numArray.length; i <leni; i++){
            for (int j = 0, lenj = numArray[i].length; j < lenj; j++){
                if (i == j)
                    numArray[i][j] = 1;
            }
        }
    }

    private static void printArrayAsMatrix(int[][] deepArray){ //функция для вывода массива в виде матрицы
        for(int[] arr : deepArray){
            for (int item : arr){
                System.out.printf("%2d " , item);
            }
            System.out.println();
        }
        System.out.println();
    }
}
