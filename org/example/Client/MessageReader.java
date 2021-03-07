package org.example.Client;

import javafx.concurrent.Task;
import org.example.Server.Message;


public class MessageReader extends Task<Integer> {

    private StringBuilder sb;

    public MessageReader() {
        this.sb = new StringBuilder();
    }

    @Override
    protected Integer call() throws Exception {
        Message messageFromServer;
        while (true) {
            messageFromServer = App.serverService.readMessage();
            App.serverService.saveMessage(messageFromServer);
            if(messageFromServer.getNickname() != null){
                sb.append(messageFromServer.toString());
            } else{
                sb.append(messageFromServer.getMessage());
            }
            sb.append("\n");

            if ("/quit".equals(messageFromServer.getMessage())) {
                App.serverService.closeConnection();
            }
            printToUI(sb.toString());
        }
    }

    //вывод сообщения на экран

    private void printToUI(Message message) {
        if (message.getNickname() != null) {
            updateMessage(message.toString());
        } else{
            updateMessage(message.getMessage());

        }
    }

    private void printToUI(String message) {
        updateMessage(message);
    }

}
