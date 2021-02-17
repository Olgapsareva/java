package Client;

import Server.Server;
import Server.AuthMessage;
import Server.Message;
import com.google.gson.Gson;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

public class SocketServerService implements ServerService {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private volatile boolean isConnected = false;
    private File localMessageHistory;

    @Override
    public void openConnection() {
        try {
            socket = new Socket("localhost", Server.PORT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            localMessageHistory = new File("LocalMessageHistory.txt");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String authorization(String login, String password) throws IOException {
        AuthMessage authMessage = new AuthMessage();
        authMessage.setLogin(login);
        authMessage.setPassword(password);
        out.writeUTF(new Gson().toJson(authMessage));

        authMessage = new Gson().fromJson(in.readUTF(), AuthMessage.class);

        if(authMessage.isAuthenticated()){
            isConnected = true;
        } else if(authMessage.isTimeExpired()){ //если получаем сообщение о timeout auth - закрываем коннекшн
            return "timeout";
        }

        return authMessage.getNickname();
    }

    @Override
    public void closeConnection() {

        try {
            out.close();
            in.close();
            socket.close();
            isConnected = false;

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void sendMessage(String message) {
        Message msg = new Message();
        msg.setMessage(message);

        try {
            out.writeUTF(new Gson().toJson(msg));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Message readMessage() {
        try {
            return new Gson().fromJson(in.readUTF(), Message.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Message();
    }

    @Override
    public void saveMessage(Message messageFromServer) {
        try {
            FileWriter fileWriter = new FileWriter(localMessageHistory, true);
            String nick = messageFromServer.getNickname();
            String msg = messageFromServer.getMessage();
            fileWriter.append(nick).append(": ").append(msg).append("\n");
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public boolean isConnected() { //проверяем статус. статус мменяется в методе authorization()
        return isConnected;
    }
}
