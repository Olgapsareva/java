//ЗАДАНИЕ 2: ПОЛЕ ЧУДЕС

package geekbrains.lesson3;

//import java.util.Random;

import java.util.Arrays;
import java.util.Scanner;


public class LimitedQuiz {

    static Scanner scanner = new Scanner(System.in);

    static String[] words = {"apple", "orange", "lemon", "banana", "apricot", "avocado",
            "broccoli", "carrot", "cherry", "garlic", "grape", "melon", "leak",
            "kiwi", "mango", "mushroom", "nut", "olive", "pea", "peanut",
            "pear", "pepper", "pineapple", "pumpkin", "potato"};

    static int tryCount = 3;


    public static void main(String[] args) {


        playGame(words, tryCount);

        scanner.close();

    }

    private static void playGame(String[] words, int tryCount) {

        String secretWord = chooseRandomWord(words);
        char[] gameBoard = createGameBoard();

        String gameBoardForPrint;
        String userAnswer;

        System.out.printf("Попробуйте угадать слово %nУ вас есть %d попыт%s %n", tryCount, addSuffix(tryCount));

        while (tryCount > 0) {
            gameBoardForPrint = new String(gameBoard);
            System.out.printf(gameBoardForPrint + "%n");

            userAnswer = getAnswer();

            if (userAnswer.equals(secretWord)) {
                System.out.println("Поздравляю, вы угадали!");
                break;
            } else {
                showLetters(gameBoard, userAnswer, secretWord);
                printMessage(--tryCount, secretWord);

            }

        }
        anotherRound();
    }

    private static String chooseRandomWord(String[] words) {
        return words[(int) (Math.random() * words.length)]; //прокомментируйте, пожалуйста, какое написание метода лучше. Это?

/*      int randomIndex = (int)(Math.random()*words.length); //или это? не могу понять, где грань между лаконичностью и нечитабельностью кода
        return words[randomIndex];*/

        /*Random randomizer = new Random();
        return words[randomizer.nextInt(words.length)];*/

    }

    private static char[] createGameBoard() {
        char[] placeholder = new char[15];
        Arrays.fill(placeholder, '#');
        return placeholder;

    }

    private static String addSuffix(int tryCount) {
        String suffix = "";
        switch (tryCount){
            case 2, 3, 4 -> suffix = "ки";
            case 1 -> suffix = "ка";
            case 0, 5, 6, 7, 8, 9 -> suffix = "ок";
        }
        return suffix;
    }

    private static String getAnswer() {
        String answer = scanner.next();
        return answer.toLowerCase();
    }

    private static void showLetters(char[] gameBoard, String userAnswer, String secretWord) {
        char letter;
        int indexOfFoundLetter;

        for (int i = 0; i < secretWord.length(); i++) {
            letter = secretWord.charAt(i);
            indexOfFoundLetter = userAnswer.indexOf(letter);

                if (indexOfFoundLetter != -1) {

                    gameBoard[i] = letter;
                }

        }
    }

    private static void printMessage(int tryCount, String secretWord) {
        if(tryCount == 0){
            System.out.printf("У вас больше не осталось попыток:(%nЗагаданное слово было: %s%n", secretWord);
        }
        else{
            System.out.printf("Не верно :( Попробуйте ещё раз.%n" +
                    "У вас осталось %d попыт%s%n", tryCount, addSuffix(tryCount));
        }
    }

    private static void anotherRound() {
        System.out.println("Сыграем ещё раунд? y/n?");
        switch(scanner.next()) {
            case "yes", "y", "da", "да", "+" -> playGame(words, tryCount);
            case "no", "n", "net", "нет", "-" -> {
                System.out.println("Игра окончена");
                System.exit(0);
            }
        }
    }
}
