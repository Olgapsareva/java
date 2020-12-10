package geekbrains.lesson4;

import java.util.Random;
import java.util.Scanner;

public class XO {

    static final int SIZE = 4;

    static final char DOT_EMPTY = '.';
    static final char DOT_HUMAN = 'X';
    static final char DOT_AI = '0';
    static final char HEADER_FIRST_SYMBOL = '#';

    static final String HEADER_EMPTY_SYMBOL = " ";
    static final char[][] map = new char[SIZE][SIZE];

    static final Scanner scanner = new Scanner(System.in);

    static final Random random = new Random();
    static int humanRow, humanColumn;

    static boolean victoryHuman, victoryAI;

    static int aiRow;
    static int aiColumn;
    static boolean rowToBlock, columnToBlock, diagonal1ToBlock, diagonal2ToBlock;  //переменные для блокировки хода
    static boolean winningRow, winningColumn, winningDiagonal1, winningDiagonal2; //переменные для оценки возможности победы ИИ


    public static void main(String[] args) {

        printGameBoard();
        playGame();

    }

    private static void printGameBoard() {
        printHeader();
        printMap();
    }

    private static void printHeader() {
        System.out.print(HEADER_FIRST_SYMBOL + HEADER_EMPTY_SYMBOL);
        for (int i = 1; i <= SIZE; i++) {
            System.out.print(i + HEADER_EMPTY_SYMBOL);

        }
        System.out.println();
    }

    private static void printMap() {
        for (int i = 0; i < SIZE; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] == '\u0000') {
                    map[i][j] = DOT_EMPTY;
                }
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static void playGame() {
        int count = 0;
        int threashold = findThreashold(); //устанавливаем порог, при котором возникает возможность выиграша


        while (count < SIZE*SIZE) {
            if (victoryHuman || victoryAI) {
                break;
            } else {
                humanTurn();
                printGameBoard();
                victoryHuman = isVictory(humanRow, humanColumn, DOT_HUMAN, threashold);
                count++;

                if(count == SIZE*SIZE || victoryHuman){
                    break;
                }

                aiTurn(threashold);
                printGameBoard();
                victoryAI = isVictory(aiRow, aiColumn, DOT_AI, threashold);
                count++;

            }

        }
        printWinner(victoryAI, victoryHuman);

    }

    private static int findThreashold() {
        int n = -1;
        switch(SIZE){
            case 3,4,5 -> n= 3;
            case 6,7,8,9 -> n= 4;
            case 10 -> n= 5;
        }
        return n;
    }

    private static void humanTurn() {

        do {
            System.out.println("введите значение строки");
            humanRow = checkInt();
            System.out.println("введите значение столбца");
            humanColumn = checkInt();
        } while (!isWithinBounds(humanRow, humanColumn) || !isEmpty(humanRow, humanColumn, true));

        map[humanRow][humanColumn] = DOT_HUMAN;
    }

    private static int checkInt() {
        if (scanner.hasNextInt()) {
            return scanner.nextInt() - 1;
        }
        scanner.nextLine();
        return -1;
    }

    private static boolean isWithinBounds(int row, int column) {
        if ((row < SIZE & row >= 0) && (column < SIZE & column >= 0)) {
            return true;
        } else {
            System.out.println("проверьте значения стоки и столбца. Попробуйте ещё раз");
            scanner.nextLine();
            return false;
        }
    }

    private static boolean isEmpty(int row, int column, boolean human) {
        if (map[row][column] == DOT_EMPTY) {
            return true;
        } else {
            if (human) {
                System.out.println("данная ячейка занята. Попробуйте ещё раз");
            }
            return false;
        }
    }

    private static void aiTurn(int threashold) {
        boolean block = anythingToBlock(threashold); //проверяет, есть ли строки/столбцы/вертикали на которых может выиграть человек, записывет значения таких строк/столбцов/вертикалей в переменные класса
        boolean ableToWin = ableToWin(threashold); //проверяет, есть ли строки/столбцы/вертикали на которых может выиграть ИИ, записывет значения аких строк/столбцов/вертикалей в переменные класса
        int[] nextCell;
        do {
            if (ableToWin) {
                nextCell = findBestPosToWin();
                aiRow = nextCell[0];
                aiColumn = nextCell[1];

            } else if (block) {
                nextCell = findBestPosToBlock();
                aiRow = nextCell[0];
                aiColumn = nextCell[1];
            } else {
                aiRow = random.nextInt(SIZE);
                aiColumn = random.nextInt(SIZE);
            }

        } while (!isEmpty(aiRow, aiColumn, false));

        map[aiRow][aiColumn] = DOT_AI;

    }


    private static boolean isVictory(int row, int column, char dot, int threashold) { //проверяем строку, колонку и 2 диагонали (если стоим на диагонали) на победу

        boolean victoryDiagonal1 = false;
        boolean victoryDiagonal2 = false;
        boolean victoryRow;
        boolean victoryColumn;

        if (row == column) {
            victoryDiagonal1 = checkDiagonal1OnVictory(dot, threashold);
        } else if(row + column == SIZE - 1){
            victoryDiagonal2 = checkDiagonal2OnVictory(dot, threashold);
        }


        victoryRow = checkRowOnVictory(row, dot, threashold);
        victoryColumn = checkColumnOnVictory(column, dot, threashold);

        return victoryRow || victoryColumn || victoryDiagonal1 || victoryDiagonal2;
    }

    private static boolean checkDiagonal1OnVictory(char dot, int threashold) {
        int count = 0;

        for (int i = 0; i < SIZE; i++) {
            if (map[i][i] == dot) {
                count++;
                if(count == threashold)
                    return true;
            } else if(map[i][i] != dot){
                count = 0;
            }
        }
        return false;
    }

    private static boolean checkDiagonal2OnVictory(char dot, int threashold){
        int count = 0;
        for (int i = 0, j = SIZE - 1; i < SIZE & j >= 0; i++, j--) {
            if (map[i][j] == dot) {
                count++;
                if(count == threashold)
                    return true;
            }else if(map[i][j] != dot){
                count = 0;
            }
        }
        return false;
    }

    private static boolean checkRowOnVictory(int row, char dot, int threashold) { //победа определяется подсчетом количества знаков
        int count = 0;
        for (int i = 0; i < SIZE; i++) {
            if (map[row][i] == dot) {
                count++;
                if(count == threashold)
                    return true;
            } else if(map[row][i] != dot){
                count = 0;
            }
        }
        return false;
    }

    private static boolean checkColumnOnVictory(int column, char dot, int threashold) {
        int count = 0;
        for (int i = 0; i < SIZE; i++) {
            if (map[i][column] == dot) {
                count++;
                if(count == threashold)
                    return true;
            } else if(map[i][column] != dot){
                count = 0;
            }
        }
        return false;
    }

    private static boolean anythingToBlock(int threashold) { //проверяем, есть ли строка/колонка/диагональ на которой может выиграть человек
        diagonal1ToBlock = ifAlmostFilledDiagonal1(DOT_HUMAN, threashold);
        diagonal2ToBlock = ifAlmostFilledDiagonal2(DOT_HUMAN, threashold);
        rowToBlock = ifAlmostFilledRow(DOT_HUMAN, humanRow, threashold);
        columnToBlock = ifAlmostFilledColumn(DOT_HUMAN, humanColumn, threashold);

        return diagonal1ToBlock || diagonal2ToBlock || rowToBlock || columnToBlock;
    }


    private static boolean ifAlmostFilledDiagonal1(char dot, int threashold) { //используется и для блокировки и для оценки победы ИИ
        int count = 0;
        int emptySlot = 0;
        for (int i = 0; i < SIZE; i++) {
            if (map[i][i] == dot) {
                count++;
            } else if (map[i][i] == DOT_EMPTY) {  //проверка: тут пустое место или занятое?
                emptySlot++;
            } else {
                count--;
            }
        }
        return count == threashold-1 && emptySlot > 0; //возврщает если есть есть опасное количество точек подряд и минимум 1 свободное место

    }

    private static boolean ifAlmostFilledDiagonal2(char dot, int threashold) { //используется и для блокировки и для оценки победы ИИ
        int count = 0;
        int emptySlot = 0;
        for (int i = 0, j = SIZE - 1; i < SIZE; i++, j--) {
            if (map[i][j] == dot) {
                count++;
            } else if (map[i][j] == DOT_EMPTY) { //проверка: тут пустое место или занятое?
                emptySlot++;
            } else {
                count--;
            }
        }
        return count == threashold-1 && emptySlot >0; //возврщает если есть опасное количество точек подряд и минимум 1 свободное место
    }

    private static boolean ifAlmostFilledRow(char dot, int row, int threashold) { //используется и для блокировки и для оценки победы ИИ

        int count = 0;
        int emptySlot = 0;
        for (int i = 0; i < SIZE; i++) {
            if (map[row][i] == dot) {
                count++;
            } else if (map[row][i] == DOT_EMPTY) {   //проверка: тут пустое место или занятое?
                emptySlot++;
            } else {
                count--;
            }
        }
        return count == threashold-1 && emptySlot > 0;// возвращает, если есть есть опасное количество точек подряд и минимум 1 свободное место

    }

    private static boolean ifAlmostFilledColumn(char dot, int column, int threashold) { //используется и для блокировки и для оценки победы ИИ
        int count = 0;
        int emptySlot = 0;
        for (int i = 0; i < SIZE; i++) {

            if (map[i][column] == dot) {
                count++;
            } else if (map[i][column] == DOT_EMPTY) {   //проверка: тут пустое место или занятое?
                emptySlot++;
            } else {
                count--;
            }

        }
        return count == threashold-1 && emptySlot > 0; // возвращает, если там есть есть опасное количество точек подряд и минимум 1 свободное место

    }

    private static boolean ableToWin(int threashold) { //проверяем, есть ли строка/колонка/диагональ, на которой может выиграть ИИ
        winningDiagonal1 = ifAlmostFilledDiagonal1(DOT_AI, threashold);
        winningDiagonal2 = ifAlmostFilledDiagonal2(DOT_AI, threashold);
        winningRow = ifAlmostFilledRow(DOT_AI, aiRow, threashold);
        winningColumn = ifAlmostFilledColumn(DOT_AI, aiColumn, threashold);
        return winningRow || winningColumn || winningDiagonal1 || winningDiagonal2;

    }

    private static int[] findBestPosToWin() { //найти и вернуть пустую ячейку
        int[] bestPos = new int[2];
        if (winningRow) {
            bestPos = findPosInRow(DOT_AI, aiRow);

        } else if (winningColumn) {
            bestPos = findPosInColumn(DOT_AI, aiColumn);

        } else if (winningDiagonal1) {
            bestPos = findPosInDiagonal1(DOT_AI);

        } else if (winningDiagonal2) {
            bestPos = findPosInDiagonal2(DOT_AI);
        }
        return bestPos;
    }

    private static int[] findBestPosToBlock() { //найти и вернуть пустую ячейку
        int[] bestPos = new int[2];
        if (rowToBlock) {
            bestPos = findPosInRow(DOT_HUMAN, humanRow);

        } else if (columnToBlock) {
            bestPos = findPosInColumn(DOT_HUMAN, humanColumn);

        } else if (diagonal1ToBlock) {
            bestPos = findPosInDiagonal1(DOT_HUMAN);

        } else if (diagonal2ToBlock) {
            bestPos = findPosInDiagonal2(DOT_HUMAN);
        }
        return bestPos;
    }

    private static int[] findPosInRow(char dot, int row) { //смотрим, есть ли 2 подряд или через одну фишки противника/свои
        int[] foundPos = {row, -1};
        for (int i = 0; i < SIZE-2; i++) {
            if (map[row][i] == dot && map[row][i+1] == dot && map[row][i+2] == DOT_EMPTY){
                foundPos[1] = i+2;
                break;
            } else if (map[row][i] == dot && map[row][i+2] == dot && map[row][i+1] == DOT_EMPTY){
                foundPos[1] = i+1;
                break;
            } else if (map[row][i+1] == dot && map[row][i+2] == dot && map[row][i] == DOT_EMPTY){
                foundPos[1] = i;
                break;
            }
        }
        if(foundPos[1] == -1){ // если не нашел пустую клетку рядом или между - ставь в любую свободную
            for (int i = 0; i < SIZE; i++) {
                if(map[row][i] == DOT_EMPTY){
                    foundPos[1] = i;
                    break;
                }

            }
        }
        return foundPos;
    }

    private static int[] findPosInColumn(char dot, int column) { //смотрим, есть ли 2 подряд или через одну фишки противника/свои
        int[] foundPos = {-1, column};
        for (int i = 0; i < SIZE-2; i++) {
            if (map[i][column] == dot && map[i+1][column] == dot && map[i+2][column] == DOT_EMPTY){
                foundPos[0] = i+2;
                break;
            } else if (map[i][column] == dot && map[i+2][column] == dot && map[i+1][column] == DOT_EMPTY){
                foundPos[0] = i+1;
                break;
            } else if (map[i+1][column] == dot && map[i+2][column] == dot && map[i][column] == DOT_EMPTY){
                foundPos[0] = i;
                break;
            }
        }
        if(foundPos[0] == -1){ // если не нашел пустую клетку рядом или между - ставь в любую свободную
            for (int i = 0; i < SIZE; i++) {
                if(map[i][column]==DOT_EMPTY){
                    foundPos[0] = i;
                    break;
                }

            }
        }
        return foundPos;

    }

    private static int[] findPosInDiagonal1(char dot) { //смотрим, есть ли 2 подряд или через одну фишки противника/свои
        int[] foundPos = {-1, -1};
        for (int i = 0; i < SIZE-2; i++) {
            if (map[i][i] == dot && map[i+1][i+1] == dot && map[i+2][i+2] == DOT_EMPTY){
                foundPos[0] = i+2;
                foundPos[1] = i+2;
                break;
            } else if (map[i][i] == dot && map[i+2][i+2] == dot && map[i+1][i+1] == DOT_EMPTY){
                foundPos[0] = i+1;
                foundPos[1] = i+1;
                break;
            } else if (map[i+1][i+1] == dot && map[i+2][i+2] == dot && map[i][i] == DOT_EMPTY){
                foundPos[0] = i;
                foundPos[1] = i;
                break;
            }
        }
        if(foundPos[0] ==-1){ //если не нашел пустую клетку рядом или между - ставь в любую свободную
            for (int i = 0; i < SIZE; i++) {
                if(map[i][i]==DOT_EMPTY){
                    foundPos[0] = i;
                    foundPos[1] = i;
                    break;
                }
            }
        }
        return foundPos;

    }

    private static int[] findPosInDiagonal2(char dot) { //смотрим, есть ли 2 фишки подряд или через одну фишки противника/свои
        int[] foundPos = {-1, -1};
        for (int i = 0, j = SIZE - 1; i < SIZE-2; i++, j--) {
            if (map[i][j] == dot && map[i+1][j-1] == dot && map[i+2][j-2] == DOT_EMPTY){
                foundPos[0] = i+2;
                foundPos[1] = j-2;
                break;
            } else if (map[i][j] == dot && map[i+2][j-2] == dot && map[i+1][j-1] == DOT_EMPTY){
                foundPos[0] = i+1;
                foundPos[1] = j-1;
                break;
            } else if (map[i+1][j-1] == dot && map[i+2][j-2] == dot && map[i][j] == DOT_EMPTY){
                foundPos[0] = i;
                foundPos[1] = j;
                break;
            }
        }
        if(foundPos[0] == -1){ // если не нашел пустую клетку рядом или между - ставь в любую свободную
            for (int i = 0, j = SIZE - 1; i < SIZE; i++, j--) {
                if(map[i][j] == DOT_EMPTY){
                    foundPos[0] = i;
                    foundPos[1] = j;
                    break;
                }

            }
        }
        return foundPos;

    }

    private static void printWinner(boolean victoryAI, boolean victoryHuman) {
        if (victoryAI) {
            System.out.printf("Победил компьютер%nИгра окончена");
        } else if (victoryHuman) {
            System.out.printf("Победил человек%nИгра окончена");
        } else{
            System.out.printf("ничья%nИгра окончена");
        }
    }


    }



