package Server;

public class Message {
    private String nickname;
    private String message;

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
        return  "nickname='" + nickname + '\'' +
                ", message='" + message + '\'';
    }
}
