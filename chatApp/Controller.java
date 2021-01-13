/*1. Создать окно для клиентской части чата:
+большое текстовое поле для отображения переписки в центре окна.
+Однострочное текстовое поле для ввода сообщений и кнопка для отсылки сообщений на нижней панели.
+Сообщение должно отсылаться либо по нажатию кнопки на форме, либо по нажатию кнопки Enter.
+При «отсылке» сообщение перекидывается из нижнего поля в центральное.*/

package chatApplication;

import javafx.fxml.FXML;
import javafx.scene.control.*;


public class Controller {

    MessageHistory messageHistory = new MessageHistory();


    @FXML
    private TextArea textWindow;

    @FXML
    private TextField textInputField;

    @FXML
    void sendMessage(){
        Long timeStamp = System.currentTimeMillis(); //временная отметка сообщения
        if (!textInputField.getText().isEmpty()) { //если не пустое поле - сохраняем в историю сообщений и выводим на экран
            messageHistory.add(timeStamp, textInputField.getText());
            textWindow.appendText(messageHistory.getMessage(timeStamp));
        }
        textInputField.clear();

    }

}
