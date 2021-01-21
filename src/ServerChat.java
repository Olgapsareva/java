package geekbrains.lesson14;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerChat {

    private static Scanner scanner = new Scanner(System.in);
    private static String clientMessage;
    private static String serverMessage;
    private static DataInputStream inputData;
    private static DataOutputStream outputData;

    public static void main(String[] args) {

        try {
            ServerSocket serverSocket = new ServerSocket(6543);
            System.out.println("server is waiting...");
            Socket socket = serverSocket.accept();
            System.out.println("client connected");
            inputData = new DataInputStream(socket.getInputStream());
            outputData = new DataOutputStream(socket.getOutputStream());

            InputFromUserThread in = new InputFromUserThread();
            in.start();

            while (!"/quit".equals(clientMessage) && !"/quit".equals(serverMessage)) {
                serverMessage = scanner.nextLine();
                outputData.writeUTF(serverMessage);
                outputData.flush();

            }

            in.join();
            socket.close();
            serverSocket.close();
            scanner.close();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    static class InputFromUserThread extends Thread{
        @Override
        public void run() {
            while (!"/quit".equals(clientMessage) && !"/quit".equals(serverMessage)) {
                try {
                    clientMessage = inputData.readUTF();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("user: " + clientMessage);
            }
        }

    }
}
