package org.example.Server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server{

    public static final int PORT = 8081;
    private List<ClientHandler> clients;
    private AuthorizationService authService;
    public static final int AUTH_TIMEOUT = 120;
    private final File GLOBAL_MESSAGE_HISTORY = new File("GlobalMessageHistory.txt");
    private final int NUMBER_OF_MESSAGES = 99;
    protected final static Logger LOGGER = LogManager.getLogger(Server.class);
    ExecutorService executorService;


    public Server(){
        //начинаем слушать порт
        try (ServerSocket serverSocket = new ServerSocket(PORT)){
            LOGGER.info("сервер ожидает подключения");

            authService = new BaseAuthorizationService();
            authService.start();

            clients = new ArrayList<>();

            executorService = Executors.newCachedThreadPool();
            while (true){
                Socket socket = serverSocket.accept();

                //замеряем время открытия сокета, отсчитываем таймаут от этого момента
                LocalDateTime calculatedTerminationTime = LocalDateTime.now().plusSeconds(AUTH_TIMEOUT);

                LOGGER.info("клиент {} пытается подключиться", socket.getInetAddress().getHostName());

                //передаем время закрытия авторизации клиентхендлеру
                executorService.execute(new ClientHandler(this, socket, calculatedTerminationTime)); //для каждого нового юзера, свой хэндлер
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (authService != null) {
                authService.stop();
            }
            executorService.shutdown();
        }
    }

    //отправляем всем сообщение
    public synchronized void broadcast(Message messageObj) {
        //сначала сохраняем историю сообщений
        saveMessage(messageObj);
        for (ClientHandler client : clients) {
            client.sendMessage(messageObj);
        }
    }

    //сохраняет всю переписку из чата
    private void saveMessage(Message messageObj) {
        try {
            FileWriter fileWriter = new FileWriter(GLOBAL_MESSAGE_HISTORY, true);
            String nick = messageObj.getNickname();
            String msg = messageObj.getMessage();
            if (nick != null) {
                fileWriter.append(nick).append(": ");
            }
            fileWriter.append(msg).append("\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //отправляем всем список подключенных юзеров
    public synchronized void broadcastClientList(){
        StringBuilder sb = new StringBuilder();
        sb.append("/clients ");
        for (ClientHandler client : clients) {
            sb.append(client.getNickname()).append(" ");
        }

        Message msg = new Message();
        msg.setMessage(sb.toString());
        broadcast(msg);
        saveMessage(msg);
    }

    //отправляет клиенту последние Х сообщений
    public void sendMessageHistory(ClientHandler client) {
        try {
            int noOfLines = getNumberOfLines();
            int startToWrite = 0;
            if(noOfLines > NUMBER_OF_MESSAGES){
                startToWrite = noOfLines - NUMBER_OF_MESSAGES;
            }

            String line;
            int i = 0;
            BufferedReader bufferedReader = new BufferedReader(new FileReader(GLOBAL_MESSAGE_HISTORY));
            while ((line = bufferedReader.readLine()) != null){
                if (i >= startToWrite) {
                    Message oldMessage = new Message(null, line);
                    client.sendMessage(oldMessage);
                }
                i++;
            }
            bufferedReader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //возврщает количество строк в общей переписке
    private int getNumberOfLines() throws IOException {
        LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(GLOBAL_MESSAGE_HISTORY));
        lineNumberReader.skip(Long.MAX_VALUE); //прыгаем в конец файла
        int lineCounter = lineNumberReader.getLineNumber();
        lineNumberReader.close();
        return lineCounter;
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

    //если в списке подключенных юзеров уже есть такой ник, то возвращаем true
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
        broadcastClientList();
    }

    public synchronized void unsubscribe(ClientHandler clientHandler) {
        clients.remove(clientHandler);
        broadcastClientList();
    }

}
