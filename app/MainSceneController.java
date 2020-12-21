package quizz;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class MainSceneController {

    String secretWord = chooseRandomWord();

    @FXML
    private TextField userInput;

    @FXML
    private Label gameField;

    @FXML
    private Label systemMessage;

    @FXML
    private Button button;

    @FXML
    void takeInput() {
        final int[] tryCount = {3};
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                char[] gameBoard = gameField.getText().toCharArray();
                String userAnswer;

                if(tryCount[0] > 0){
                    userAnswer = userInput.getText().toLowerCase();
                    userInput.clear();

                    if (userAnswer.equals(secretWord)) {
                        showVictoryMessage();

                    } else {
                        showLetters(gameBoard, userAnswer, secretWord);
                        printMessage(--tryCount[0], secretWord);
                    }

                }
            }
        });
    }


    private String chooseRandomWord() {
        String[] words = {"apple", "orange", "lemon", "banana", "apricot", "avocado",
                "broccoli", "carrot", "cherry", "garlic", "grape", "melon", "leak",
                "kiwi", "mango", "mushroom", "nut", "olive", "pea", "peanut",
                "pear", "pepper", "pineapple", "pumpkin", "potato"};
        return words[(int) (Math.random() * words.length)];

    }

    private void showVictoryMessage() {
        systemMessage.setText("Поздравляю, вы угадали!");
        gameField.setText(secretWord);
    }


    private String addSuffix(int tryCount) {
        String suffix = "";
        switch (tryCount){
            case 2, 3, 4 -> suffix = "ки";
            case 1 -> suffix = "ка";
            case 0, 5, 6, 7, 8, 9 -> suffix = "ок";
        }
        return suffix;
    }

    private void showLetters(char[] gameBoard, String userAnswer, String secretWord) {
        char letter;
        int indexOfFoundLetter;

        for (int i = 0; i < secretWord.length(); i++) {
            letter = secretWord.charAt(i);
            indexOfFoundLetter = userAnswer.indexOf(letter);

            if (indexOfFoundLetter != -1) {

                gameBoard[i] = letter;
            }

        }
        gameField.setText(String.valueOf(gameBoard));
    }

    private void printMessage(int tryCount, String secretWord) {
        if(tryCount == 0){
            String lostGameMessage = String.format("У вас больше не осталось попыток:(%nЗагаданное слово было: %s%n", secretWord);
            systemMessage.setText(lostGameMessage);
        }
        else{
            String newTryMessage = String.format("Не верно :( Попробуйте ещё раз.%n" +
                    "У вас осталось %d попыт%s%n", tryCount, addSuffix(tryCount));
            systemMessage.setText(newTryMessage);
        }
    }




}