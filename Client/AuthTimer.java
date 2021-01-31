package Client;

import javax.swing.*;

public class AuthTimer extends Thread {

    private final int TIME_LIMIT = 120;
    private SocketServerService serverService;
    private JButton authButton;
    private JButton send;
    private JLabel timerLabel;


    public AuthTimer(SocketServerService serverService, JButton authButton, JButton send, JLabel timerLabel){
        this.serverService = serverService;
        this.authButton = authButton;
        this.send = send;
        this.timerLabel = timerLabel;
    }

    @Override
    public void run() {
        int i = TIME_LIMIT;
        while(!isInterrupted() && i>0){
            timerLabel.setText("осталось: " + i + " сек.");
            i--;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                this.interrupt();
            }
        }
        //обрываем соединение если не успели подключиться (isConnected - volitile)
        if(!serverService.isConnected()) {
            serverService.authExpired();
            serverService.closeConnection();
            authButton.setEnabled(false);
            timerLabel.setText("время вышло");
            send.setEnabled(false);

        }

    }

}
