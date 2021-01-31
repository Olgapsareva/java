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

    public Server(){
        //начинаем слушать порт
        try (ServerSocket serverSocket = new ServerSocket(PORT)){
            System.out.println("сервер ожидает подключения");
            authService = new BaseAuthorizationService();
            authService.start();
            clients = new ArrayList<>();

            while (true){
                Socket socket = serverSocket.accept();
                System.out.printf("клиент %s пытается подключиться%n", socket.getInetAddress().getHostName());
                new ClientHandler(this, socket); //для каждого нового юзера, свой хэндлер
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (authService != null) {
                authService.stop();
            }
        }
    }

    public synchronized void broadcast(Message messageObj) { //отправляем всем сообщение
        for (ClientHandler client : clients) {
            client.sendMessage(messageObj);
        }
    }

    public synchronized void broadcastClientList(){ //отправляем всем список подключенных юзеров
        StringBuilder sb = new StringBuilder();
        sb.append("/clients ");
        for (ClientHandler client : clients) {
            sb.append(client.getNickname()).append(" ");
        }

        Message msg = new Message();
        msg.setMessage(sb.toString());
        broadcast(msg);
    }

    public synchronized void sendPersonalMessage(Message privateMessage, ClientHandler sender,  String recipient){
        for (ClientHandler client : clients) {
            if(recipient.equals(client.getNickname())){

                client.sendMessage(privateMessage);
                sender.sendMessage(privateMessage);
                return;
            }
        }
        Message clientNotFound = new Message();
        clientNotFound.setMessage(String.format("клиент с ником %s не подключен к чату", recipient));
        sender.sendMessage(clientNotFound);
    }

    public synchronized boolean isNickBusy(String nickname){ //если в списке подключенных юзеров уже есть такой ник, то возвращаем true
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
        broadcastClientList();
    }

    public synchronized void unsubscribe(ClientHandler clientHandler) {
        clients.remove(clientHandler);
        broadcastClientList();
    }
}
