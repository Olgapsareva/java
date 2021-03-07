package org.example.Client;

import javafx.concurrent.Task;
import org.example.Server.Server;


public class AuthTimer extends Task<Integer> {

    //рисует время в UI

    @Override
    protected Integer call() throws Exception {
        int i = Server.AUTH_TIMEOUT;
        while(!isCancelled() && i>0) {
            updateMessage("осталось: " + i + " сек.");
            i--;
            Thread.sleep(1000);
        }
        if (isCancelled()){
            return 0;
        }
        updateMessage("время вышло");
        return -1;
    }



}
