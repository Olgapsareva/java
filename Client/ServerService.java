package Client;

import Server.Message;

public interface ServerService {

    void openConnection();
    void closeConnection();
    void sendMessage(String message);
    Message readMessage();
}
