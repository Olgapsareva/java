module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires org.apache.logging.log4j;

    opens org.example to javafx.fxml;
    opens org.example.Client to javafx.fxml, com.google.gson;
    opens org.example.Client.controllers to javafx.fxml, com.google.gson;
    opens org.example.Server to javafx.fxml, com.google.gson;

    exports org.example.Client;
    exports org.example.Server;
    exports org.example.Client.controllers;


}