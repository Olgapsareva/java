package Server;

import java.util.ArrayList;
import java.util.List;

public class BaseAuthorizationService implements AuthorizationService {
    private class Entry{
        private String login;
        private String password;
        private String nickname;

        public Entry(String login, String password, String nickname) {
            this.login = login;
            this.password = password;
            this.nickname = nickname;
        }
    }
    List<Entry> entries;

    public BaseAuthorizationService() {
        entries = new ArrayList<>();
        entries.add(new Entry("Ivan", "password", "Ivan"));
        entries.add(new Entry("Leo", "password123", "Leo"));
        entries.add(new Entry("Maria", "password098", "MariaM"));
    }

    @Override
    public void start() {
        System.out.println("сервис авторизации запущен");

    }

    @Override
    public void stop() {
        System.out.println("сервис авторизации остановлен");
    }

    @Override
    public String nickByLoginAndPass(String login, String password) {
        String nickFound = null;
        for (Entry entry : entries) {
            if(login.equals(entry.login) && password.equals(entry.password)){
                nickFound = entry.nickname;
            }

        }
        return nickFound;

    }

}
