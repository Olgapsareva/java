package Client;

import Server.Server;
import javax.swing.*;

public class AuthTimer extends Thread { //рисует время в UI

    private JButton authButton;
    private JButton send;
    private JLabel timerLabel;


    public AuthTimer(JButton authButton, JButton send, JLabel timerLabel){
        this.authButton = authButton;
        this.send = send;
        this.timerLabel = timerLabel;
    }

    @Override
    public void run() {
        int i = Server.AUTH_TIMEOUT;
        while(!isInterrupted() && i>=0){
            timerLabel.setText("осталось: " + i + " сек.");
            i--;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                this.interrupt();
            }
        }



    }

}
