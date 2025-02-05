package com.project.booksphere.util;

import com.project.booksphere.controller.SendMailController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class NavigationPage {
    public void newWindowPopUpEmail(String path,String email) throws IOException {
//        Parent load = FXMLLoader.load(getClass().getResource(path));
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        Parent load = loader.load();
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/logo.jpg")));
        stage.setTitle("Book Sphere");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);

        SendMailController Controller = loader.getController();
        Controller.setEmail(email);
        stage.showAndWait();
    }
    public static void newWindowPopUp(String path) throws IOException {
        Parent load = FXMLLoader.load(NavigationPage.class.getResource(path));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.getIcons().add(new Image(NavigationPage.class.getResourceAsStream("/image/logo.jpg")));
        stage.setTitle("Payment");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.showAndWait();
    }

}
