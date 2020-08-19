package com.tasksOrganizer.gui;

import com.tasksOrganizer.db.DBFonctions;
import com.tasksOrganizer.sample.Task;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private TableView<HomeTableViewModel> table = new TableView<>();

    @FXML
    private TableColumn<HomeTableViewModel, String> col_name;

    @FXML
    private TableColumn<HomeTableViewModel, Integer> col_days;

    @FXML
    private TableColumn<HomeTableViewModel, Button> col_info;

    @FXML
    private TableColumn<HomeTableViewModel, Button> col_modif;

    @FXML
    private TableColumn<HomeTableViewModel, Button> col_del;

    @FXML
    private TableColumn<HomeTableViewModel, CheckBox> col_cb;

    public ObservableList<HomeTableViewModel> list;

    @FXML
    private StackPane parentContainer;

    @FXML
    private AnchorPane container;

    @FXML
    Button addTaskButton, parametersButton;

    @FXML
    Text title;

    private boolean finAnimation = true;

    HomeTableViewModel row;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ImageView addTaskImage = new ImageView(getClass().getResource("/images/addTask.png").toExternalForm());
        addTaskButton.setGraphic(addTaskImage);

        ImageView parametersImage = new ImageView(getClass().getResource("/images/parameters.png").toExternalForm());
        parametersButton.setGraphic(parametersImage);

        list = FXCollections.observableArrayList();

        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_days.setCellValueFactory(new PropertyValueFactory<>("days"));
        col_info.setCellValueFactory(new PropertyValueFactory<>("info"));
        col_modif.setCellValueFactory(new PropertyValueFactory<>("modif"));
        col_del.setCellValueFactory(new PropertyValueFactory<>("delete"));
        col_cb.setCellValueFactory(new PropertyValueFactory<>("checkbox"));

        Task[] tasks = Task.extractTasks();
        String text;

        text = tasks.length != 0 ? "Actuellement, vous avez ceci à faire..." :  "Vous n'avez aucune tâche en attente !";
        title.setText(text);

        Button[] infoButtons = new Button[tasks.length];
        Button[] modifyButtons = new Button[tasks.length];
        Button[] deleteButtons = new Button[tasks.length];
        CheckBox[] checkboxes = new CheckBox[tasks.length];

        for (int i = 0; i < tasks.length; i++){
            infoButtons[i] = new Button();
            modifyButtons[i] = new Button();
            deleteButtons[i] = new Button();
            checkboxes[i] = new CheckBox();
        }

        int[] referenceDel = new int[tasks.length];
        int[] referenceCb = new int[tasks.length];

        for (int i = 0; i < tasks.length; i++) {
            row = new HomeTableViewModel(tasks[i].getNom(), 4, infoButtons[i], modifyButtons[i], deleteButtons[i], checkboxes[i]);
            referenceDel[i] = i;

            infoButtons[i].setId(row.getName());
            row.getInfo().setOnAction(event -> {
                String name = ((Button)(event.getSource())).getId();
                loadInfo(name);
                //System.out.println("info "+name+" clicked");
            });

            modifyButtons[i].setId(row.getName());
            row.getModif().setOnAction(event -> {
                String name = ((Button)(event.getSource())).getId();
                loadModif(name);
                //System.out.println("modif "+name+" clicked");
            });

            deleteButtons[i].setId(row.getName()+i);
            row.getDelete().setOnAction(event -> {
                String name = ((Button)(event.getSource())).getId();
                int index = Integer.parseInt(name.substring(name.length()-1));
                name = name.substring(0, name.length()-1);

                Task.removeTask(name);
                referenceDel[index] = -1;
                //System.out.println("la tache "+ name + " a été supprimée ! index : "+ index);

                int countDel = 0;
                for(int j = 0; j < index; j++)
                    countDel = referenceDel[j] == -1 ? countDel + 1 : countDel;

                //try to animate this
                list.remove(index - countDel);

                table.setItems(list);
            });

            checkboxes[i].setId(row.getName()+i);
            referenceCb[i] = i;
            row.getCheckbox().setOnAction(event -> {
                ((CheckBox)(event.getSource())).setSelected(true);

                /*synchronized (list) {
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        //e.printStackTrace();
                    }
                    list.notifyAll();
                }*/

                String name = ((CheckBox)(event.getSource())).getId();
                int index = Integer.parseInt(name.substring(name.length()-1));
                name = name.substring(0, name.length()-1);

                DBFonctions.taskDone(name);
                referenceCb[index] = -1;

                int countDone = 0;
                for(int j = 0; j < index; j++)
                    countDone = referenceCb[j] == -1 ? countDone + 1 : countDone;
                    //try to animate this
                    list.remove(index - countDone);

                    table.setItems(list);
            });

            list.add(row);
        }

        for (int i = 0; i < tasks.length; i++) {
            infoButtons[i].setGraphic(new ImageView(getClass().getResource("/images/infoTask.png").toExternalForm()));
            modifyButtons[i].setGraphic(new ImageView(getClass().getResource("/images/modifyTask.png").toExternalForm()));
            deleteButtons[i].setGraphic(new ImageView(getClass().getResource("/images/deleteTask.png").toExternalForm()));
        }

        table.setItems(list);
    }

    @FXML
    protected void handleAddTaskButtonAction() throws IOException {
        if(finAnimation){
            finAnimation = false;
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/createTask.fxml"));
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
        //load the parameters ui
    }

    private void loadInfo(String taskName){
        //opens the createTask ui already filled
    }

    private void loadModif(String taskName){
        //opens a small undecorated window witch contains the task's informations
    }

}
