package Client;

import Server.Server;
import Server.AuthMessage;
import Server.Message;
import com.google.gson.Gson;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketServerService implements ServerService {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private boolean isConnected = false;
    private String login = "Leo";
    private String password = "password123";

    @Override
    public void openConnection() {
        try {
            socket = new Socket("localhost", Server.PORT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            AuthMessage authMessage = new AuthMessage();
            authMessage.setLogin(login);
            authMessage.setPassword(password);
            out.writeUTF(new Gson().toJson(authMessage));

            authMessage = new Gson().fromJson(in.readUTF(), AuthMessage.class);

            if(authMessage.isAuthenticated()){
                isConnected = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public boolean isConnected() {
        return isConnected;
    }
}
