package com.tasksOrganizer.gui;

import com.tasksOrganizer.optimizer.Optimizer;
import com.tasksOrganizer.sample.Task;
import com.tasksOrganizer.tray.animations.AnimationType;
import com.tasksOrganizer.tray.notification.NotificationType;
import com.tasksOrganizer.tray.notification.TrayNotification;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
    private TableColumn<HomeTableViewModel, Button> col_ok;

    public ObservableList<HomeTableViewModel> list;

    @FXML
    private StackPane parentContainer;

    @FXML
    private AnchorPane container;

    @FXML
    Button addTaskButton, parametersButton;

    @FXML
    Text title;

    Task[] tasks;

    private boolean finAnimation = true;

    HomeTableViewModel row;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ImageView addTaskImage = new ImageView(getClass().getResource("/images/addTask.png").toExternalForm());
        addTaskButton.setGraphic(addTaskImage);
        addTaskButton.setTooltip(new Tooltip("Créer une nouvelle tâche"));

        ImageView parametersImage = new ImageView(getClass().getResource("/images/parameters.png").toExternalForm());
        parametersButton.setGraphic(parametersImage);
        parametersButton.setTooltip(new Tooltip("Paramètres"));

        list = FXCollections.observableArrayList();

        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_days.setCellValueFactory(new PropertyValueFactory<>("days"));
        col_info.setCellValueFactory(new PropertyValueFactory<>("info"));
        col_modif.setCellValueFactory(new PropertyValueFactory<>("modif"));
        col_del.setCellValueFactory(new PropertyValueFactory<>("delete"));
        col_ok.setCellValueFactory(new PropertyValueFactory<>("ok"));

        updateTable();

        MotherController.list = list;
        MotherController.table = table;

    }

    @FXML
    protected void handleAddTaskButtonAction() throws IOException {
        if (finAnimation) {
            finAnimation = false;
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/createTask.fxml"));
            root.getStylesheets().add(getClass().getResource("/css/createTask.css").toString());

            root.translateYProperty().set(650);
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
    protected void handleParametersButtonAction() {
        //load the parameters ui
    }

    private void loadInfo(String taskName) throws IOException {
        //opens the createTask ui already filled

        if (!MotherController.infoOpened) {
            MotherController.infoOpened = true;
            MotherController.taskName = taskName;

            Parent root = FXMLLoader.load(getClass().getResource("/fxml/infoTask.fxml"));
            root.getStylesheets().add(getClass().getResource("/css/info.css").toExternalForm());
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));
            stage.setAlwaysOnTop(true);
            stage.show();
        }

    }

    private void loadModif(String taskName) throws IOException {
        //opens a small undecorated window witch contains the task's informations
        if(!MotherController.modifOpened){

            MotherController.modifOpened = true;
            MotherController.taskName = taskName;

            Parent root = FXMLLoader.load(getClass().getResource("/fxml/modifTask.fxml"));
            root.getStylesheets().add(getClass().getResource("/css/modif.css").toExternalForm());
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));
            stage.setAlwaysOnTop(true);
            stage.show();
        }
    }

    private int refreshText(int nb, ActionEvent event) {
        String singPlur, text;
        if (event != null && (event.getSource().getClass()).toString().equals("class javafx.scene.control.Button"))
            nb--;

        singPlur = nb == 1 ? "tâche" : "tâches";
        text = nb != 0 ? "Actuellement, vous avez " + nb + " " + singPlur + " en attente..." : "Vous n'avez aucune tâche en attente !";
        title.setText(text);
        if (nb == 0)
            title.setStyle("-fx-fill: green");

        MotherController.homeTitle = title;

        return nb;
    }
    
    public void updateTable() {
        tasks = Task.extractTasks();
        
        Optimizer op = new Optimizer();
        op.optimizeList(tasks);

        final int[] nb = {tasks.length};

        nb[0] = refreshText(nb[0], null);

        Button[] infoButtons = new Button[tasks.length];
        Button[] modifyButtons = new Button[tasks.length];
        Button[] deleteButtons = new Button[tasks.length];
        Button[] oks = new Button[tasks.length];

        Tooltip tooltipInfo = new Tooltip("Informations");
        Tooltip tooltipModif = new Tooltip("Modifier");
        Tooltip tooltipDelete = new Tooltip("Supprimer");
        Tooltip tooltipOk = new Tooltip("Marquer comme terminée");

        for (int i = 0; i < tasks.length; i++) {
            infoButtons[i] = new Button();
            modifyButtons[i] = new Button();
            deleteButtons[i] = new Button();
            oks[i] = new Button();
            oks[i].setText("Done ?");

            infoButtons[i].setTooltip(tooltipInfo);
            modifyButtons[i].setTooltip(tooltipModif);
            deleteButtons[i].setTooltip(tooltipDelete);
            oks[i].setTooltip(tooltipOk);
        }

        int[] referenceDel = new int[tasks.length];
        int[] referenceDone = new int[tasks.length];

        for (int i = 0; i < tasks.length; i++) {
            long interval = ChronoUnit.DAYS.between(LocalDate.now(), tasks[i].getEcheance());
            String unite = interval == 1 ? " jour" : " jours";

            row = new HomeTableViewModel(tasks[i].getNom(), interval + unite, infoButtons[i], modifyButtons[i], deleteButtons[i], oks[i]);

            infoButtons[i].setId(row.getName());
            row.getInfo().setOnAction(event -> {

                String name = ((Button) (event.getSource())).getId();
                try {
                    loadInfo(name);
                } catch (IOException e) {
                    //e.printStackTrace();
                }
            });

            modifyButtons[i].setId(row.getName());
            row.getModif().setOnAction(event -> {
                String name = ((Button) (event.getSource())).getId();
                try {
                    loadModif(name);
                } catch (IOException e) {
                    //e.printStackTrace();
                }
            });

            deleteButtons[i].setId((i + "").length() + row.getName() + i);
            referenceDel[i] = i;
            row.getDelete().setOnAction(event -> {

                String data = ((Button) (event.getSource())).getId();
                int tailleIndex = Integer.parseInt(data.substring(0, 1));
                int index = Integer.parseInt(data.substring(data.length() - tailleIndex));
                String name = data.substring(1/*tailleIndex.length*/, data.length() - tailleIndex);

                Task.remove(name);
                referenceDel[index] = -1;
                //System.out.println("la tache "+ name + " a été supprimée ! index : "+ index);

                int countDel = 0;
                for (int j = 0; j < index; j++)
                    countDel = referenceDel[j] == -1 ? countDel + 1 : countDel;

                // TODO: Animate deletion of tableView  elements
                list.remove(index - countDel);

                table.setItems(list);

                table.refresh();

                nb[0] = refreshText(nb[0], event);

                TrayNotification tray = new TrayNotification();
                tray.setTitle("Suppression réussie");
                tray.setMessage("La tâche '" + name + "' a été supprimée avec succès.");
                tray.setAnimationType(AnimationType.SLIDE);
                tray.setNotificationType(NotificationType.SUCCESS);
                tray.showAndDismiss(Duration.seconds(5));

            });

            oks[i].setId((i + "").length() + row.getName() + i);
            oks[i].getStyleClass().add("doneButton");
            referenceDone[i] = i;
            row.getOk().setOnAction(event -> {

                String data = ((Button) (event.getSource())).getId();
                int tailleIndex = Integer.parseInt(data.substring(0, 1));
                int index = Integer.parseInt(data.substring(data.length() - tailleIndex));
                String name = data.substring(1/*tailleIndex.length*/, data.length() - tailleIndex);

                Task.done(name);
                referenceDone[index] = -1;

                int countDone = 0;
                for (int j = 0; j < index; j++)
                    countDone = referenceDone[j] == -1 ? countDone + 1 : countDone;
                //try to animate this
                list.remove(index - countDone);

                table.setItems(list);

                nb[0] = refreshText(nb[0], event);
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
}