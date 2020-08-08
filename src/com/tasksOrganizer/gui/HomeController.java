package com.tasksOrganizer.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    Button addTaskButton, parametersButton;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ImageView addTaskImage = new ImageView(getClass().getResource("/images/addTask.png").toExternalForm());
        addTaskButton.setGraphic(addTaskImage);

        ImageView parametersImage = new ImageView(getClass().getResource("/images/parameters.png").toExternalForm());
        parametersButton.setGraphic(parametersImage);
    }

    @FXML
    protected void handleAddTaskButtonAction(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/fxml/createTask.fxml"));
        Scene scene = new Scene(parent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        parent.getStylesheets().add(getClass().getResource("/css/createTask.css").toString());

        window.setScene(scene);
        window.show();
    }

    @FXML
    protected void handleParametersButtonAction(ActionEvent event) throws IOException{
        System.out.println("parametres");
    }


}
