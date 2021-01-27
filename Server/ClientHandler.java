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

    public ClientHandler(Server server, Socket socket) {
        try {
            this.server = server;
            this.socket = socket;

            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());

            try {
                new Thread(()->{
                    authentication();
                    readClientMessages();
                }).start();
            } catch (Exception e) {
                e.printStackTrace();
            } /*finally {
                closeConnection();
            }*/

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void authentication() {
        while (true) {
            try {
                AuthMessage AuthMessageObj = new Gson().fromJson(in.readUTF(), AuthMessage.class);
                String nickname = server.getAuthService().nickByLoginAndPass(AuthMessageObj.getLogin(), AuthMessageObj.getPassword());
                if (nickname != null && !server.isNickBusy(nickname)){
                    AuthMessageObj.setAuthenticated(true);
                    out.writeUTF(new Gson().toJson(AuthMessageObj));

                    Message msgToBroadcast = new Message();
                    msgToBroadcast.setMessage(nickname + " вошел в чат");
                    server.broadcast(msgToBroadcast);
                    server.subscribe(this);

                    this.nickname = nickname;
                    break;
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
                if("/quit".equals(messageObj.getMessage())){
                    sendExitMessage();
                    closeConnection();
                    return;
                } else if(messageObj.getMessage().startsWith("/w")){
                    server.sendPersonalMessage(messageObj);
                } else{
                    server.broadcast(messageObj);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void sendExitMessage() {
        Message exitMessage = new Message();
        exitMessage.setMessage("/quit");
        exitMessage.setNickname(nickname);
        sendMessage(exitMessage);
    }

    public void sendMessage(Message messageObj) {
        try {
            out.writeUTF(new Gson().toJson(messageObj));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeConnection() {
        server.unsubscribe(this);
        Message msgToBroadcast = new Message();
        msgToBroadcast.setMessage(nickname + " вышел из чата");
        System.out.println(msgToBroadcast.getMessage());
        server.broadcast(msgToBroadcast);
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
