package org.example.Client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.Client.*;

public class chatController {

    private MessageReader messageReader;

    @FXML
    private TextArea textWindow;

    @FXML
    private TextField textInputField;

    @FXML
    private void initialize() {
        //читаем сообщения от сервера в отдельном потоке:
        messageReader = new MessageReader();
        textWindow.textProperty().bind(messageReader.messageProperty());
        new Thread(messageReader).start();
    }

    @FXML
    void sendMessage() {
        App.serverService.sendMessage(textInputField.getText());
        textInputField.clear();
    }

}
