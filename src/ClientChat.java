/*1. Написать консольный вариант клиент\серверного приложения,
в котором пользователь может писать сообщения, как на клиентской стороне, так и на серверной.
Т.е. если на клиентской стороне написать «Привет», нажать Enter,
то сообщение должно передаться на сервер и там отпечататься в консоли.
Если сделать то же самое на серверной стороне, то сообщение передается клиенту и печатается у него в консоли.

Есть одна особенность, которую нужно учитывать: клиент или сервер может написать несколько сообщений подряд.
Такую ситуацию необходимо корректно обработать.
Разобраться с кодом с занятия: он является фундаментом проекта-чата

*ВАЖНО! * Сервер общается только с одним клиентом,
т.е. не нужно запускать цикл, который будет ожидать второго/третьего/n-го клиентов*/

package geekbrains.lesson14;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientChat {

    private static String consoleInput;
    private static String serverMessage;
    private static Scanner scanner = new Scanner(System.in);
    private static DataOutputStream outputData;
    private static DataInputStream inputData;

    public static void main(String[] args) {


        try {
            Socket socket = new Socket("localhost", 6543);
            outputData = new DataOutputStream(socket.getOutputStream());
            inputData = new DataInputStream(socket.getInputStream());

            Thread in = new Thread(new InputFromServerThread());
            in.start();

            while (!socket.isClosed()){
                consoleInput = scanner.nextLine();
                outputData.writeUTF(consoleInput);
                outputData.flush();
                if("/quit".equals(consoleInput) || "/quit".equals(serverMessage)){
                    break;
                }
            }

            scanner.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    static class InputFromServerThread implements Runnable{
        @Override
        public void run() {
            while (!"/quit".equals(consoleInput) && !"/quit".equals(serverMessage)) {
                try {
                    serverMessage = inputData.readUTF();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("server: "+serverMessage);
            }
        }
    }

}
