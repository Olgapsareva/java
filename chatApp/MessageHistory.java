package chatApplication;

import java.util.*;

public class MessageHistory {

    private final Map<Long, String> MESSAGE_HISTORY;
    private Calendar calendar = Calendar.getInstance();
    private StringBuilder stringBuilder = new StringBuilder();

    public MessageHistory(){
        MESSAGE_HISTORY = new LinkedHashMap<>();
    }

    public void add(Long time, String message){
        MESSAGE_HISTORY.put(time, String.format("%s%n", message));
    }

    public String getMessage(Long time){
        calendar.setTimeInMillis(time);
        if(!stringBuilder.isEmpty()){
            stringBuilder.delete(0, stringBuilder.length());
        }
        stringBuilder.append(String.format("%tr ", calendar.getTime()));
        stringBuilder.append("\n");
        stringBuilder.append(MESSAGE_HISTORY.get(time));
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }

}
