package com.project.booksphere;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AppInitializer  extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent load = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
        Scene scene = new Scene(load);
        stage.setScene(scene);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/logo.jpg")));
        stage.setTitle("BookSphere");
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
