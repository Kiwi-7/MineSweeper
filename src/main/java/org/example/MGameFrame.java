package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class MGameFrame extends Application{
    @Override
    public void start(Stage stage) throws Exception {
        Button button = new Button("Click me");
        Scene scene = new Scene(new StackPane(button), 300, 200);

        stage.setTitle("Hello");
        stage.setScene(scene);
        stage.show();
    }
}
