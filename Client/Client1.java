/*
1. Добавить в сетевой чат запись локальной истории в текстовый файл на клиенте.
2. После загрузки клиента показывать ему последние 100 строк чата.
*/

package Client;

import Server.Message;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class Client1 extends JFrame {

    private SocketServerService serverService;


    public Client1() {

        serverService = new SocketServerService();
        serverService.openConnection();


        JPanel jPanel = new JPanel();

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.X_AXIS));
        jPanel.setSize(300, 50);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setBounds(400, 400, 500, 300);

        JTextArea mainChat = new JTextArea();
        mainChat.setSize(400, 250);

        JTextField myMessage = new JTextField();

        JButton send = new JButton("Send");

        send.addActionListener(actionEvent -> sendMessage(myMessage));

        myMessage.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendMessage(myMessage);
                }
            }
        });

        initLoginPanel(mainChat, send);


        if (serverService.isConnected()) {
            new Thread(()->{
                while(true) {
                    Message messageFromServer = serverService.readMessage();
                    if("/quit".equals(messageFromServer.getMessage())){
                        printToUI(mainChat,"вы вышли из чата");
                        serverService.closeConnection();
                        return;
                    } else{
                        printToUI(mainChat, messageFromServer);
                    }
                }
            });

        }

        add(mainChat);
        add(send);
        add(myMessage);
        add(jPanel);
    }

    private void initLoginPanel(JTextArea mainChat, JButton send) {
        JTextField login = new JTextField();
        login.setToolTipText("Логин");
        JPasswordField password = new JPasswordField();
        password.setToolTipText("Пароль");
        JLabel authLabel = new JLabel("offline");
        JButton authButton = new JButton("Войти");
        JLabel timerLabel = new JLabel();

        AuthTimer timer = new AuthTimer(authButton, send, timerLabel);
        timer.start();

        authButton.addActionListener(actionEvent ->{
            String lgn = login.getText();
            login.setText("");
            String pass = new String(password.getPassword());
            password.setText("");

            authButton.setEnabled(true);


            if(lgn != null && pass != null && !lgn.isEmpty() && !pass.isEmpty()) {
                try {
                    String loggedIn = serverService.authorization(lgn, pass);
                    if (loggedIn != null) {

                        timer.interrupt(); //останавливаем таймер
                        authButton.setEnabled(false);

                        if ("timeout".equals(loggedIn)) {
                            serverService.closeConnection();
                            timerLabel.setText("время вышло");
                            send.setEnabled(false);
                        } else {
                            authLabel.setText(loggedIn + " online"); //если успешно авторизовались, меняем статус юзера на онлайн
                            timerLabel.setText("");
                        }
                    }
                } catch (IOException e) {
                    //e.printStackTrace();
                    serverService.closeConnection();
                    timerLabel.setText("сервер недоступен. время вышло");
                    send.setEnabled(false);
                    authButton.setEnabled(false);
                }
            }

                //читаем сообщения от сервера в отдельном потоке, если прошла авторизация
            if (serverService.isConnected()) {
                authButton.setEnabled(false);
                new Thread(() -> {
                    Message messageFromServer;
                    while (true) {
                        messageFromServer = serverService.readMessage();
                        if ("/quit".equals(messageFromServer.getMessage())) {
                            serverService.closeConnection();
                            authLabel.setText("offline");
                            send.setEnabled(false);
                            return;
                        }
                        printToUI(mainChat, messageFromServer);
                        serverService.saveMessage(messageFromServer);

                    }
                }).start();
            }

        });

        add(login);
        add(password);
        add(authButton);
        add(timerLabel);
        add(authLabel);

    }


    private void sendMessage(JTextField myMessage) {
        serverService.sendMessage(myMessage.getText());
        myMessage.setText("");
    }

    private void printToUI(JTextArea mainChat, Message message) { //вывод сообщения на экран
        if (message.getNickname() != null) {
            mainChat.append(message.toString());
        } else{
            mainChat.append(message.getMessage());
        }

        mainChat.append("\n");
    }

    private void printToUI(JTextArea mainChat, String message) {
        mainChat.append("\n");
        mainChat.append(message);
    }


}

