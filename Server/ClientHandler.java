package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import com.google.gson.Gson;

public class ClientHandler {
    private Socket socket;
    Server server;
    private DataOutputStream out;
    private DataInputStream in;
    private String nickname;
    StringBuilder stringBuilder = new StringBuilder();


    public ClientHandler(Server server, Socket socket) {
        try {
            this.server = server;
            this.socket = socket;

            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());

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
        while (true) {
            try {
                authMessage = new Gson().fromJson(in.readUTF(), AuthMessage.class);

                if(authMessage.isTimeExpired()){
                    System.out.println("auth session expired");
                    return -1;
                }
                //проверяем, есть ли в списке пользователей юзер с таким логином и паролем:
                String nickname = server.getAuthService().nickByLoginAndPass(authMessage.getLogin(), authMessage.getPassword());

                if (nickname != null && !server.isNickBusy(nickname)){ //если нашелся -отправляем клиенту AuthMessage с присвоенным ником...

                    authMessage.setAuthenticated(true);
                    authMessage.setNickname(nickname);
                    this.nickname = nickname;
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
                        //sendExitMessage();
                        closeConnection();
                        return;
                    }
                    case"/w":{
                        if(splittedToWords.length <3){
                            Message commandErrorMessage = new Message();
                            commandErrorMessage.setMessage("некорректный ввод. ВВедите сообщение в формате /команда <ник> <сообщение>");
                            sendMessage(commandErrorMessage);
                        } else{
                            Message personalMessage = getExtractedMessage(nickname, splittedToWords);
                            server.sendPersonalMessage(personalMessage, this, splittedToWords[1]);
                            stringBuilder.delete(0, stringBuilder.length());
                        }
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
        //stringBuilder.append(" приватно: ").append(sender).append(" ");
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
        Message msgToBroadcast = new Message();
        msgToBroadcast.setMessage(nickname + " вышел из чата");
        server.broadcast(msgToBroadcast);
        sendExitMessage();
        System.out.println(msgToBroadcast.getMessage());
        server.unsubscribe(this);
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getNickname() {
        return nickname;
    }
}
