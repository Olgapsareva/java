package Server;

public class AuthMessage {
    String login;
    String password;
    String nickname;
    private boolean authenticated = false;
    private boolean timeExpired = false;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public boolean isTimeExpired() {
        return timeExpired;
    }

    public void setTimeExpired(boolean timeExpired) {
        this.timeExpired = timeExpired;
    }

    @Override
    public String toString() {
        return "AuthMessage{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", authenticated=" + authenticated +
                '}';
    }
}
