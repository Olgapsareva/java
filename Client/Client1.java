package Client;

import Server.Server;
import Server.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Client1 extends JFrame {

    private SocketServerService serverService;


    public Client1() {

        serverService = new SocketServerService();
        serverService.openConnection();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout());
        setBounds(400, 400, 500, 500);

        TextArea mainChat = new TextArea();
        mainChat.setSize( 100, 300);

        TextArea myMessage = new TextArea();
        myMessage.setSize(100, 300);

        Button send = new Button("Send");
        send.setSize(50,200);
        send.addActionListener(actionEvent -> sendMessage(myMessage));

        myMessage.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendMessage(myMessage);
                }
            }
        });



        add(mainChat);
        add(send);
        add(myMessage);

        new Thread(() -> {
            while(true) {
                Message messageFromServer = serverService.readMessage();
                if("/quit".equals(messageFromServer.getMessage())){
                    printToUI(mainChat,"вы вышли из чата");
                    serverService.closeConnection();
                    return;
                } else{
                    printToUI(mainChat,messageFromServer);
                }
            }

        }).start();
    }

    private void sendMessage(TextArea myMessage) {
        serverService.sendMessage(myMessage.getText());
        myMessage.setText("");
    }

    private void printToUI(TextArea mainChat, Message message) {
        mainChat.append(message.getNickname()+" написал: "+message.getMessage());
        mainChat.append("\n");
    }

    private void printToUI(TextArea mainChat, String message) {
        mainChat.append("\n");
        mainChat.append(message);
    }


}
