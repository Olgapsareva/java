package org.example.Server;

public interface AuthorizationService {
    void start();
    void stop();
    String nickByLoginAndPass(String login, String password);
    void changeNickname(String oldNick, String newNick);
}
