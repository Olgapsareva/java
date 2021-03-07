package org.example.Client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;
import org.example.Server.*;
import java.io.IOException;
import java.net.URL;

public class App extends Application {

    private static Scene scene;
    private static FXMLLoader fxmlLoader;
    public static SocketServerService serverService;

    @Override
    public void init() throws Exception {
        serverService = new SocketServerService();
        serverService.openConnection();
    }

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("authWindow"), 480, 500);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        System.out.println("это тред в App.java: " + Thread.currentThread().getName());
   }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        URL url  = App.class.getResource(fxml + ".fxml");
        fxmlLoader = new FXMLLoader(url);
        return fxmlLoader.load();
    }


    public static void main(String[] args) {
        launch();
    }

}