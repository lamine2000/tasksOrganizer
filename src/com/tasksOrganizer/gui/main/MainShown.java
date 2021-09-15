package com.tasksOrganizer.gui.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class MainShown extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/home.fxml")));
        root.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/stylesheets/home.css")).toString());
        primaryStage.setTitle("tasksOrganizer");
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("/images/appIcone.png")).toExternalForm(), false));
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
