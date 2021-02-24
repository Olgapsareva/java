package Server;

public class Message {
    private String nickname;
    private String message;

    public Message(String nickname, String message){
        this.nickname = nickname;
        this.message = message;
    }
    public Message(){
        this(null, null);
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return  nickname + ": " + message;
    }
}
