package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server{

    public static final int PORT = 8081;
    private List<ClientHandler> clients;
    private AuthorizationService authService;
    StringBuilder stringBuilder = new StringBuilder();

    public Server(){
        try (ServerSocket serverSocket = new ServerSocket(PORT)){
            System.out.println("сервер ожидает подключения");
            authService = new BaseAuthorizationService();
            authService.start();
            clients = new ArrayList<>();

            while (true){
                Socket socket = serverSocket.accept();
                System.out.printf("клиент %s вошел в чат", socket.getInetAddress().getHostName());
                new ClientHandler(this, socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (authService != null) {
                authService.stop();
            }
        }
    }


    public synchronized void broadcast(Message messageObj) {
        for (ClientHandler client : clients) {
            client.sendMessage(messageObj);
        }
    }

    public synchronized void sendPersonalMessage(Message messageObj) {
        String[] splittedToWords = messageObj.getMessage().split("\\s");
        String recipient = splittedToWords[1];
        String sender = messageObj.getNickname();

        for (ClientHandler client : clients) {
            if(recipient.equals(client.getNickname())){
                client.sendMessage(getExtractedMessage(sender, splittedToWords));
                stringBuilder.delete(0, stringBuilder.length());
            }
            if(messageObj.getNickname().equals(client.getNickname())) {
                client.sendMessage(messageObj);
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
        stringBuilder.append(" приватно: ").append(sender).append(" ");
        for (int i = 2; i<splittedToWords.length; i++ ) {
            stringBuilder.append(splittedToWords[i]).append(" ");
        }
        return stringBuilder.toString();
    }


    public synchronized boolean isNickBusy(String nickname){
        for (ClientHandler client : clients) {
           if(nickname.equals(client.getNickname())){
               return true;
           }
        }
        return false;

    }

    public AuthorizationService getAuthService() {
        return authService;
    }

    public synchronized void subscribe(ClientHandler clientHandler) {
        clients.add(clientHandler);
    }

    public synchronized void unsubscribe(ClientHandler clientHandler) {
        clients.remove(clientHandler);
    }
}
