package com.tasksOrganizer.gui;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private StackPane parentContainer;

    @FXML
    private AnchorPane container;

    @FXML
    Button addTaskButton, parametersButton;

    private boolean finAnimation = true;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ImageView addTaskImage = new ImageView(getClass().getResource("/images/addTask.png").toExternalForm());
        addTaskButton.setGraphic(addTaskImage);

        ImageView parametersImage = new ImageView(getClass().getResource("/images/parameters.png").toExternalForm());
        parametersButton.setGraphic(parametersImage);
    }

    @FXML
    protected void handleAddTaskButtonAction() throws IOException {
        if(finAnimation){
            finAnimation = false;
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/createTask.fxml"));
            //Scene scene = new Scene(root);
            root.getStylesheets().add(getClass().getResource("/css/createTask.css").toString());

            root.translateYProperty().set(600);
            parentContainer.getChildren().add(root);

            Timeline timeLine = new Timeline();
            KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
            KeyFrame kf = new KeyFrame(Duration.seconds(0.6), kv);
            timeLine.getKeyFrames().add(kf);

            timeLine.setOnFinished(event1 -> {
                parentContainer.getChildren().remove(container);
                finAnimation = true;
            });

            timeLine.play();
        }

    }

    @FXML
    protected void handleParametersButtonAction(){
        System.out.println("parametres");
    }


}
