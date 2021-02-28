package Client;

import Server.AuthMessage;
import Server.Message;

import javax.swing.*;
import java.io.IOException;

public interface ServerService {

    void openConnection();
    void closeConnection();
    void sendMessage(String message);
    Message readMessage();
    String authorization(String login, String password) throws IOException;
    void saveMessage(Message messageFromServer);
}
