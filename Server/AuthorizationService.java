package Server;

public interface AuthorizationService {
    void start();
    void stop();
    String nickByLoginAndPass(String login, String password);
}