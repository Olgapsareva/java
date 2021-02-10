package Server;

import java.sql.*;

public class BaseAuthorizationService implements AuthorizationService {

    private final String URL = "jdbc:sqlite://*ABSOLUTE PATH TO*\\users.db";
    public BaseAuthorizationService(){
        try (Connection conn = DriverManager.getConnection(URL)){
            Statement statement = conn.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS 'users'(\n" +
                    "\t 'ID' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                    "\t 'Login' VARCHAR NOT NULL,\n" +
                    "\t 'Password' VARCHAR NOT NULL,\n" +
                    "\t 'Nickname' VARCHAR NOT NULL\n)");
            statement.executeUpdate("INSERT INTO users (Login, Password, Nickname) VALUES ('Ivan', 'password', 'Ivan');");
            statement.executeUpdate("INSERT INTO users (Login, Password, Nickname) VALUES ('Leo', 'password123', 'Leo');");
            statement.executeUpdate("INSERT INTO users (Login, Password, Nickname) VALUES ('Maria', 'password098', 'MariaM');");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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
        try(Connection connection = DriverManager.getConnection(URL)) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE Login = ? AND Password = ?");
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println(resultSet.getString("Nickname"));
            nickFound = resultSet.getString(4);
            System.out.println(nickFound);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return nickFound;
    }

    @Override
    public void changeNickname(String oldNick, String newNick) {
        try(Connection connection = DriverManager.getConnection(URL)) {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE users SET Nickname = ? WHERE Nickname = ?");
            preparedStatement.setString(1, newNick);
            preparedStatement.setString(2, oldNick);
            preparedStatement.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
