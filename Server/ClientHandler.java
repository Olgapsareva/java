package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import com.google.gson.Gson;

public class ClientHandler {
    private Socket socket;
    Server server;
    private DataOutputStream out;
    private DataInputStream in;
    private String nickname;
    StringBuilder stringBuilder = new StringBuilder();
    Date timeToStopRunning;
    private boolean timeout;


    public ClientHandler(Server server, Socket socket, LocalDateTime calculatedTerminationTime) {
        try {
            this.server = server;
            this.socket = socket;
            this.timeout = false;

            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            //переводим время в Date
            this.timeToStopRunning = Date.from(calculatedTerminationTime.atZone(ZoneId.systemDefault()).toInstant());

            try {
                new Thread(()->{
                    if (authentication() != -1) { //возвращает -1, если время аутентификации вышло, 0 - успешная аутентификация
                        readClientMessages();
                    }
                }).start();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int authentication() {
        AuthMessage authMessage;

        TimerTask authTimer = new TimerTask() {
            @Override
            public void run() {
                if (nickname == null) {
                    timeout = true;
                    authExpired();
                    closeConnection();
                }
            }
        };
        Timer timer = new Timer();
        //запускаем timertask в посчитанный момент (указанное время от открытия сокета)
        timer.schedule(authTimer, timeToStopRunning);

        while (timeout == false) {
            try {

                authMessage = new Gson().fromJson(in.readUTF(), AuthMessage.class);
                //проверяем, есть ли в БД users пользователь с таким логином и паролем:
                String nickname = server.getAuthService().nickByLoginAndPass(authMessage.getLogin(), authMessage.getPassword());

                if (nickname != null && !server.isNickBusy(nickname)){ //если нашелся -отправляем клиенту AuthMessage с присвоенным ником...

                    authMessage.setAuthenticated(true);
                    authMessage.setNickname(nickname);
                    this.nickname = nickname; //пока не назначен ник - значит не авторизован
                    out.writeUTF(new Gson().toJson(authMessage));

                    //...и рассылаем всем подключенным юзерам благую весть

                    server.subscribe(this);
                    Message msgToBroadcast = new Message();
                    msgToBroadcast.setMessage(this.nickname + " вошел в чат");
                    server.broadcast(msgToBroadcast);
                    return 0;

                } else{
                    AuthMessage authFailed = new AuthMessage();
                    out.writeUTF(new Gson().toJson(authFailed));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }


    private void readClientMessages() {
        while(true){
            try {
                Message messageObj = new Gson().fromJson(in.readUTF(), Message.class);
                messageObj.setNickname(nickname);
                System.out.println(messageObj);

                if(!(messageObj.getMessage().startsWith("/"))){
                    server.broadcast(messageObj);
                    continue;
                }
                String[] splittedToWords = messageObj.getMessage().split("\\s");

                switch (splittedToWords[0]){
                    case"/quit":{
                        closeConnection();
                        return;
                    }
                    case"/w":{
                        if(splittedToWords.length <3){
                            Message commandErrorMessage = new Message();
                            commandErrorMessage.setMessage("некорректный ввод. ВВедите сообщение в формате /w <ник> <сообщение>");
                            sendMessage(commandErrorMessage);
                        } else{
                            Message personalMessage = getExtractedMessage(nickname, splittedToWords);
                            server.sendPersonalMessage(personalMessage, this, splittedToWords[1]);
                            stringBuilder.delete(0, stringBuilder.length());
                        }
                        break;
                    }
                    case"/cn":{
                        if(splittedToWords.length <2){
                            Message commandErrorMessage = new Message();
                            commandErrorMessage.setMessage("некорректный ввод. Введите команду в формате /cn <ник>");
                            sendMessage(commandErrorMessage);
                        } else {
                            String newNick = splittedToWords[1];
                            Message nickChangedNotification = new Message();
                            nickChangedNotification.setMessage(nickname + " изменил ник на " + newNick);
                            server.getAuthService().changeNickname(nickname, newNick);
                            nickname = newNick;
                            server.broadcast(nickChangedNotification);
                            server.broadcastClientList();
                        }
                        break;
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private Message getExtractedMessage(String sender, String[] splittedToWords) {
        Message message = new Message();
        message.setNickname(sender);
        String extractedMessage = extractMessageFromArray(splittedToWords,sender);
        message.setMessage(extractedMessage);
        return message;
    }

    private String extractMessageFromArray(String[] splittedToWords, String sender) {
        for (int i = 2; i < splittedToWords.length; i++) {
            stringBuilder.append(splittedToWords[i]).append(" ");
        }
        return stringBuilder.toString();
    }

    private void sendExitMessage() {
        Message exitMessage = new Message();
        exitMessage.setMessage("/quit");
        exitMessage.setNickname(nickname);
        sendMessage(exitMessage);
    }

    protected void sendMessage(Message messageObj) {
        try {
            out.writeUTF(new Gson().toJson(messageObj));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeConnection() {
        if (!timeout) {
            Message msgToBroadcast = new Message();
            msgToBroadcast.setMessage(nickname + " вышел из чата");
            server.broadcast(msgToBroadcast);
            System.out.println(msgToBroadcast.getMessage());
            sendExitMessage();
            server.unsubscribe(this);
        } else{
            System.out.println("время авторизации закончилось");
        }
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //отправляем authMessage с флагом клиенту
    public void authExpired() {
        AuthMessage authExpired = new AuthMessage();
        authExpired.setTimeExpired(true);
        try {
            out.writeUTF(new Gson().toJson(authExpired));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getNickname() {
        return nickname;
    }
}
