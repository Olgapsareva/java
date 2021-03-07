package org.example.Client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.Client.*;
import java.io.IOException;

public class AuthController {

    private AuthTimer authTimer;

    @FXML
    private TextField login;

    @FXML
    private TextField password;

    @FXML
    private Label timer;

    @FXML
    private Button authButton;

    @FXML
    private void initialize(){
        authTimer = new AuthTimer();
        timer.textProperty().bind(authTimer.messageProperty());
        new Thread(authTimer).start();
    }


    @FXML
    void register() {
        String lgn = login.getText();
        login.clear();
        String pass = password.getText();
        password.clear();

        if(lgn != null && pass != null && !lgn.isEmpty() && !pass.isEmpty()) {
            try {
                String loggedIn = App.serverService.authorization(lgn, pass);
                if (loggedIn != null) {
                    authTimer.cancel();
                    if ("timeout".equals(loggedIn)) {
                        App.serverService.closeConnection();
                        authButton.setDisable(true);
                    } else{
                        App.setRoot("chatWindow");
                    }

                }
            } catch (IOException e) {
                App.serverService.closeConnection();
                authButton.setDisable(true);
            }
        }

    };

}
