package com.tasksOrganizer.gui.controllers;


import com.gluonhq.charm.glisten.control.TextField;
import com.tasksOrganizer.exceptions.MysqlUnreachableException;
import com.tasksOrganizer.gui.AlertHelper;
import com.tasksOrganizer.gui.TimeSpinner;
import com.tasksOrganizer.gui.models.HomeTableViewModel;
import com.tasksOrganizer.optimizer.Optimizer;
import com.tasksOrganizer.sample.Reminder;
import com.tasksOrganizer.sample.Task;
import com.tasksOrganizer.tray.animations.AnimationType;
import com.tasksOrganizer.tray.notification.NotificationType;
import com.tasksOrganizer.tray.notification.TrayNotification;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;


public class ModifController extends MotherController implements Initializable {

    private static ImageView d1Image0, d2Image0, d3Image0, d4Image0, d5Image0;
    private static ImageView d1Image1, d2Image1, d3Image1, d4Image1, d5Image1;

    private static ImageView i1Image0, i2Image0, i3Image0, i4Image0, i5Image0;
    private static ImageView i1Image1, i2Image1, i3Image1, i4Image1, i5Image1;

    private static boolean starDClicked = true;
    private static boolean starIClicked = true;

    private static int difficulte, importance;

    @FXML
    private Button d1;

    @FXML
    private Button d2;

    @FXML
    private Button d3;

    @FXML
    private Button d4;

    @FXML
    private Button d5;

    @FXML
    private Button i1;

    @FXML
    private Button i2;

    @FXML
    private Button i3;

    @FXML
    private Button i4;

    @FXML
    private Button i5;

    @FXML
    private TextField nameField;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private Button clearButton;

    @FXML
    private Button okButton;

    @FXML
    private DatePicker eDatePicker;

    @FXML
    private DatePicker tsDatePicker;

    @FXML
    private Button fermerButton;

    @FXML
    private Button restaurerButton;

    @FXML
    private VBox vbox, vboxStep;

    @FXML
    private DatePicker reminderNextDate;

    @FXML
    private Text text1, text2, text3;

    @FXML
    private CheckBox reminderOn;

    TimeSpinner tsFirst, tsStep;


    Task task = new Task();
    Reminder reminder = new Reminder();

    HomeTableViewModel row;

    boolean rmdrAssociated;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            rmdrAssociated = Reminder.exists(MotherController.taskName);
        } catch (MysqlUnreachableException e) {
            //e.printStackTrace();
        }

        reminderOn.setTooltip(new Tooltip("Activer les rappels"));

        reminderNextDate.setTooltip(new Tooltip("Date de la prochaine notification"));
        reminderNextDate.setEditable(false);

        tsFirst = new TimeSpinner();
        tsFirst.setTooltip(new Tooltip("Heure de la prochaine notification"));
        tsFirst.setId("firsTimeSpinner");
        vbox.getChildren().add(tsFirst);

        tsStep = new TimeSpinner();
        tsStep.setTooltip(new Tooltip("Fréquence des notifications (hh:mm)"));
        tsStep.setId("StepTimeSpinner");
        vboxStep.getChildren().add(tsStep);

        reminderOn.setSelected(rmdrAssociated);

        text1.setVisible(rmdrAssociated);
        text2.setVisible(rmdrAssociated);
        text3.setVisible(rmdrAssociated);
        reminderNextDate.setVisible(rmdrAssociated);
        vboxStep.setVisible(rmdrAssociated);
        vbox.setVisible(rmdrAssociated);

        ImageView okImage = new ImageView(getClass().getResource("/images/ok1.png").toExternalForm());
        okButton.setGraphic(okImage);
        okButton.setTooltip(new Tooltip("Valider les changements"));

        ImageView clearImage = new ImageView(getClass().getResource("/images/clear.png").toExternalForm());
        clearButton.setGraphic(clearImage);
        clearButton.setTooltip(new Tooltip("Vider les champs"));

        ImageView restoreImage = new ImageView(getClass().getResource("/images/restore.png").toExternalForm());
        restaurerButton.setGraphic(restoreImage);
        restaurerButton.setTooltip(new Tooltip("Annuler les changements"));

        fermerButton.setTooltip(new Tooltip("Fermer"));

        d1.setTooltip(new Tooltip("Très facile"));
        d2.setTooltip(new Tooltip("Facile"));
        d3.setTooltip(new Tooltip("Moyenne"));
        d4.setTooltip(new Tooltip("Difficile"));
        d5.setTooltip(new Tooltip("Très difficile"));

        i1.setTooltip(new Tooltip("Pas importante"));
        i2.setTooltip(new Tooltip("Peu importante"));
        i3.setTooltip(new Tooltip("Moyenne"));
        i4.setTooltip(new Tooltip("Importante"));
        i5.setTooltip(new Tooltip("Très importante"));

        eDatePicker.setTooltip(new Tooltip("Date limite d'exécution de la tâche"));
        tsDatePicker.setTooltip(new Tooltip("Supposition de date de fin de tâche"));

        task = Task.extract(MotherController.taskName);
        reminder = rmdrAssociated ? Reminder.extract(task.getNom()) : null;

        setStars(task);

        nameField.setText(task.getNom());

        descriptionArea.setText(task.getDescription());

        eDatePicker.setValue(task.getEcheance());

        tsDatePicker.setValue(task.getTsupp());

        importance = task.getImportance();

        difficulte = task.getDifficulte();

        if(rmdrAssociated){
            reminderNextDate.setValue(reminder.getNextDateTime().toLocalDate());
            tsFirst.getEditor().setText(reminder.getNextDateTime().getHour()+":"+reminder.getNextDateTime().getMinute());
            tsStep.getEditor().setText(reminder.getStep().getHour()+":"+reminder.getStep().getMinute());
        }

    }

    private void setStars(Task task) {
        d1Image0 = new ImageView(getClass().getResource("/images/star0.png").toExternalForm());
        d2Image0 = new ImageView(getClass().getResource("/images/star0.png").toExternalForm());
        d3Image0 = new ImageView(getClass().getResource("/images/star0.png").toExternalForm());
        d4Image0 = new ImageView(getClass().getResource("/images/star0.png").toExternalForm());
        d5Image0 = new ImageView(getClass().getResource("/images/star0.png").toExternalForm());

        d1Image1 = new ImageView(getClass().getResource("/images/star1.png").toExternalForm());
        d2Image1 = new ImageView(getClass().getResource("/images/star1.png").toExternalForm());
        d3Image1 = new ImageView(getClass().getResource("/images/star1.png").toExternalForm());
        d4Image1 = new ImageView(getClass().getResource("/images/star1.png").toExternalForm());
        d5Image1 = new ImageView(getClass().getResource("/images/star1.png").toExternalForm());

        setGraphics('d', task.getDifficulte());

        i1Image0 = new ImageView(getClass().getResource("/images/star0.png").toExternalForm());
        i2Image0 = new ImageView(getClass().getResource("/images/star0.png").toExternalForm());
        i3Image0 = new ImageView(getClass().getResource("/images/star0.png").toExternalForm());
        i4Image0 = new ImageView(getClass().getResource("/images/star0.png").toExternalForm());
        i5Image0 = new ImageView(getClass().getResource("/images/star0.png").toExternalForm());

        i1Image1 = new ImageView(getClass().getResource("/images/star1.png").toExternalForm());
        i2Image1 = new ImageView(getClass().getResource("/images/star1.png").toExternalForm());
        i3Image1 = new ImageView(getClass().getResource("/images/star1.png").toExternalForm());
        i4Image1 = new ImageView(getClass().getResource("/images/star1.png").toExternalForm());
        i5Image1 = new ImageView(getClass().getResource("/images/star1.png").toExternalForm());

        setGraphics('i', task.getImportance());
    }

    private void setGraphics(char c, int i) {
        Button[] tabD = {d1, d2, d3, d4, d5};
        Button[] tabI = {i1, i2, i3, i4, i5};
        Button[] tab = new Button[5];

        ImageView[] dImages0 = {d1Image0, d2Image0, d3Image0, d4Image0, d5Image0};
        ImageView[] dImages1 = {d1Image1, d2Image1, d3Image1, d4Image1, d5Image1};
        ImageView[] iImages0 = {i1Image0, i2Image0, i3Image0, i4Image0, i5Image0};
        ImageView[] iImages1 = {i1Image1, i2Image1, i3Image1, i4Image1, i5Image1};
        ImageView[] images0 = new ImageView[5];
        ImageView[] images1 = new ImageView[5];

        switch (c) {
            case 'd' -> {
                tab = tabD;
                images0 = dImages0;
                images1 = dImages1;
            }
            case 'i' -> {
                tab = tabI;
                images0 = iImages0;
                images1 = iImages1;
            }
        }

        for (int count = 1; count <= 5; count++) {
            if (count <= i)
                tab[count - 1].setGraphic(images1[count - 1]);

            else
                tab[count - 1].setGraphic(images0[count - 1]);
        }
    }

    @FXML
    protected void handleEffacerButtonAction() {
        emptyAll();
    }

    private void emptyAll() {
        nameField.setText("");
        descriptionArea.clear();
        starDClicked = false;
        starIClicked = false;
        setGraphics('d', 0);
        setGraphics('i', 0);
        eDatePicker.setValue(null);
        tsDatePicker.setValue(null);
        reminderOn.setSelected(false);
        setReminderStuffVisility(false);
    }

    @FXML
    protected void difficultyStarHoverAction(MouseEvent event) {
        Object source = event.getSource();
        int nb;

        if (source.equals(d1))
            nb = 1;

        else if (source.equals(d2))
            nb = 2;

        else if (source.equals(d3))
            nb = 3;

        else if (source.equals(d4))
            nb = 4;

        else /*if(source.equals(d5))*/
            nb = 5;

        setGraphics('d', nb);
    }

    @FXML
    protected void difficultyStarsExitedAction() {
        if (!starDClicked)
            setGraphics('d', 0);
        else
            setGraphics('d', difficulte);
    }

    @FXML
    protected void importanceStarHoverAction(MouseEvent event) {
        Object source = event.getSource();
        int nb;

        if (source.equals(i1))
            nb = 1;

        else if (source.equals(i2))
            nb = 2;

        else if (source.equals(i3))
            nb = 3;

        else if (source.equals(i4))
            nb = 4;

        else /*if(source.equals(i5))*/
            nb = 5;

        setGraphics('i', nb);
    }

    @FXML
    protected void importanceStarsExitedAction() {
        if (!starIClicked)
            setGraphics('i', 0);
        else
            setGraphics('i', importance);
    }

    @FXML
    protected void handleValiderButtonAction() throws CloneNotSupportedException, MysqlUnreachableException {
        Window owner = nameField.getScene().getWindow();
        LocalDateTime fdt;
        LocalTime hm;
        StringBuilder now = new StringBuilder();

        if (nameField.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Erreur!", "Veuillez entrer le nom de la nouvelle tâche !");
            return;
        }

        if (eDatePicker.getValue() == null) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Erreur!", "Veuillez préciser la date d'échéance de la nouvelle tâche !");
            return;
        }

        if (!eDatePicker.getValue().isAfter(today())) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Erreur!", "L'échéance doit etre ultérieure à la date du jour (" + today().getDayOfMonth() + "/" + today().getMonthValue() + "/" + today().getYear() + ") !");
            return;
        }

        if (tsDatePicker.getValue() == null) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Erreur!", "Veuillez estimez la date de fin de la tâche !");
            return;
        }

        if (tsDatePicker.getValue().isBefore(task.getDateCreation())) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Erreur!", "la date supposée de fin de tâche doit etre ultérieure à sa date de création (" + task.getDateCreation().getDayOfMonth() + "/" + task.getDateCreation().getMonthValue() + "/" + task.getDateCreation().getYear() + ") !");
            return;
        }

        if (tsDatePicker.getValue().isAfter(eDatePicker.getValue())) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Erreur!", "La date de fin de tâche estimée ne doit pas être au delà de l'échéance!");
            return;
        }

        if (!starDClicked) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Erreur!", "Veuillez indiquer le niveau de difficulté de la tâche (1 étoile pour \"très facile\", ..., 5 étoiles pour \"très difficile\")");
            return;
        }

        if (!starIClicked) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Erreur!", "Veuillez indiquer le niveau d'importance de la tâche (1 étoile pour \"très peu importante\", ..., 5 étoiles pour \"très importante\")");
            return;
        }

        if(rmdrAssociated){

            if(reminderNextDate.getValue() == null){
                AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Erreur!", "Veuillez entrer la date de la première notification");
                return;
            }

            if(!reminderNextDate.getValue().isBefore(eDatePicker.getValue())){
                AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Erreur!", "La date du rappel doit être antérieure à l'échéance");
                return;
            }

            hm = LocalTime.of(
                    Integer.parseInt(tsFirst.getEditor().getText().split(":")[0]),
                    Integer.parseInt(tsFirst.getEditor().getText().split(":")[1].split("\\.")[0])
            );
            fdt = LocalDateTime.of(reminderNextDate.getValue(), hm);
            if(!fdt.isAfter(LocalDateTime.now())){
                now.append(LocalDateTime.now().toString().split("\\.")[0].replace("T", "   "));
                now.delete(now.length()-3, now.length());

                AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Erreur!", "La date du premier rappel doit être ultérieure à : "+now.toString()+" (càd maintenant)");
                return;
            }
        }

        String nom = nameField.getText().toLowerCase();

        String descripiton = descriptionArea.getText();
        LocalDate echeance = eDatePicker.getValue();
        LocalDate tsuppose = tsDatePicker.getValue();

        String[] strFirst;
        LocalDateTime nextDateTime;
        String[] strStep;
        LocalTime step;
        Reminder newR;

        if(rmdrAssociated){
            strFirst = tsFirst.getEditor().getText().split(":");
            nextDateTime = LocalDateTime.of(
                    reminderNextDate.getValue(),
                    LocalTime.of(Integer.parseInt(strFirst[0]), Integer.parseInt(strFirst[1]))
            );
            strStep = tsStep.getEditor().getText().split(":");
            step = LocalTime.of(Integer.parseInt(strStep[0]), Integer.parseInt(strStep[1]));

            newR = new Reminder(nom, nextDateTime, step, true);
            if(Reminder.exists(taskName))
                Reminder.modify(taskName, newR);
            else
                Reminder.save(newR, Task.getIdOf(taskName));
        }

        else if(Reminder.exists(taskName))
            Reminder.remove(taskName);

        Task.modify(task.getNom(), new Task(nom, descripiton, importance, difficulte, echeance, tsuppose, false, task.getDateCreation()));
        handleFermerButtonAction();
        MotherController.modifOpened = false;


        TrayNotification tray = new TrayNotification();
        tray.setTitle("Enregistrement réussi");
        tray.setMessage("La tâche '"+taskName+"' a été modifiée avec succès.");
        tray.setAnimationType(AnimationType.SLIDE);
        tray.setNotificationType(NotificationType.SUCCESS);
        tray.showAndDismiss(Duration.seconds(5));

        updateTable();
    }

    private LocalDate today() {
        return LocalDate.now();
    }

    @FXML
    protected void handleStarClickedAction(ActionEvent event) {
        Button[] tabD = {d1, d2, d3, d4, d5};
        boolean trouveD = false;
        Object source = event.getSource();

        for (Button elt : tabD) {
            if (source.equals(elt)) {
                trouveD = true;
                break;
            }
        }

        if (!trouveD) {
            starIClicked = true;

            if (source.equals(i1))
                importance = 1;

            else if (source.equals(i2))
                importance = 2;

            else if (source.equals(i3))
                importance = 3;

            else if (source.equals(i4))
                importance = 4;

            else /*if(source.equals(i5))*/
                importance = 5;
        } else {
            starDClicked = true;

            if (source.equals(d1))
                difficulte = 1;

            else if (source.equals(d2))
                difficulte = 2;

            else if (source.equals(d3))
                difficulte = 3;

            else if (source.equals(d4))
                difficulte = 4;

            else /*if(source.equals(d5))*/
                difficulte = 5;
        }
    }

    private void updateTable() throws CloneNotSupportedException, MysqlUnreachableException {
        list.clear();

        Task[] tasks = Task.extractTasks();
        Optimizer op = new Optimizer();
        op.optimize(tasks);

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
            oks[i] = new Button("Done ?");

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
                //System.out.println("info "+name+" clicked");
            });

            modifyButtons[i].setId(row.getName());
            row.getModif().setOnAction(event -> {
                String name = ((Button) (event.getSource())).getId();
                try {
                    loadModif(name);
                } catch (IOException e) {
                    //e.printStackTrace();
                }
                //System.out.println("modif "+name+" clicked");
            });

            deleteButtons[i].setId((i + "").length() + row.getName() + i);
            referenceDel[i] = i;
            row.getDelete().setOnAction(event -> {
                String data = ((Button) (event.getSource())).getId();
                int tailleIndex = Integer.parseInt(data.substring(0, 1));
                int index = Integer.parseInt(data.substring(data.length() - tailleIndex));
                String name = data.substring(1/*tailleIndex.length*/, data.length() - tailleIndex);

                try {
                    Task.remove(name);
                } catch (MysqlUnreachableException e) {
                    ///e.printStackTrace();
                }
                referenceDel[index] = -1;
                //System.out.println("la tache "+ name + " a été supprimée ! index : "+ index);

                int countDel = 0;
                for (int j = 0; j < index; j++)
                    countDel = referenceDel[j] == -1 ? countDel + 1 : countDel;

                //try to animate this
                list.remove(index - countDel);

                table.setItems(list);

                table.refresh();

                nb[0] = refreshText(nb[0], event);

            });

            oks[i].setId((i+"").length()+row.getName() + i);
            oks[i].getStyleClass().add("doneButton");
            referenceDone[i] = i;
            row.getOk().setOnAction(event -> {

                String data = ((Button) (event.getSource())).getId();
                int tailleIndex = Integer.parseInt(data.substring(0,1));
                int index = Integer.parseInt(data.substring(data.length() - tailleIndex));
                String name = data.substring(1/*tailleIndex.length*/, data.length() - tailleIndex);

                try {
                    Task.done(name);
                } catch (MysqlUnreachableException e) {
                    //e.printStackTrace();
                }
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

    private int refreshText(int nb, ActionEvent event) {
        String singPlur, text;
        if (event != null && (event.getSource().getClass()).toString().equals("class javafx.scene.control.Button"))
            nb--;

        singPlur = nb == 1 ? "tâche" : "tâches";
        text = nb != 0 ? "Actuellement, vous avez " + nb + " " + singPlur + " en attente..." : "Vous n'avez aucune tâche en attente !";
        MotherController.homeTitle.setText(text);
        if (nb == 0)
            MotherController.homeTitle.setStyle("-fx-fill: green");

        return nb;
    }

    private void loadInfo(String taskName) throws IOException {
        //opens the createTask ui already filled

        if (!MotherController.infoOpened) {
            MotherController.infoOpened = true;
            MotherController.taskName = taskName;

            Parent root = FXMLLoader.load(getClass().getResource("/views/infoTask.fxml"));
            root.getStylesheets().add(getClass().getResource("/stylesheets/info.css").toExternalForm());
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));
            stage.show();
        }

    }

    private void loadModif(String taskName) throws IOException {
        //opens a small undecorated window witch contains the task's informations
        if (!MotherController.modifOpened) {

            MotherController.modifOpened = true;
            MotherController.taskName = taskName;

            Parent root = FXMLLoader.load(getClass().getResource("/views/modifTask.fxml"));
            root.getStylesheets().add(getClass().getResource("/stylesheets/modif.css").toExternalForm());
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    @FXML
    void handleFermerButtonAction() {
        Stage stage = (Stage) (d1.getScene().getWindow());
        stage.close();

        MotherController.modifOpened = false;
    }

    @FXML
    void handleRestaurerButtonAction() {

        Timeline timeLine = new Timeline();
        KeyValue kv = new KeyValue(restaurerButton.getGraphic().rotateProperty(), -720, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(1.2), kv);
        timeLine.getKeyFrames().add(kf);

        timeLine.play();
        timeLine.setOnFinished((ActionEvent event) -> restaurerButton.getGraphic().setRotate(0));

        nameField.setText(task.getNom());

        descriptionArea.setText(task.getDescription());

        eDatePicker.setValue(task.getEcheance());

        tsDatePicker.setValue(task.getTsupp());

        importance = task.getImportance();

        difficulte = task.getDifficulte();

        setGraphics('i', importance);
        setGraphics('d', difficulte);

        starDClicked = true;
        starIClicked = true;

        reminderOn.setSelected(rmdrAssociated);
        setReminderStuffVisility(rmdrAssociated);

        if(rmdrAssociated){
            reminderNextDate.setValue(reminder.getNextDateTime().toLocalDate());
            tsFirst.getEditor().setText(reminder.getNextDateTime().getHour()+":"+reminder.getNextDateTime().getMinute());
            tsStep.getEditor().setText(reminder.getStep().getHour()+":"+reminder.getStep().getMinute());
        }
    }

    @FXML
    void handleReminderCheckBoxAction() {
        boolean visible = reminderOn.isSelected();
        setReminderStuffVisility(visible);
        rmdrAssociated = visible;
    }

    void setReminderStuffVisility(boolean visible){
        double duration = 0.3;//seconds
        if(visible){
            text1.setVisible(visible);
            text2.setVisible(visible);
            text3.setVisible(visible);
            reminderNextDate.setVisible(visible);
            vboxStep.setVisible(visible);
            vbox.setVisible(visible);
        }

        text1.setOpacity(visible ? 0 : 1);
        text2.setOpacity(visible ? 0 : 1);
        text3.setOpacity(visible ? 0 : 1);
        reminderNextDate.setOpacity(visible ? 0 : 1);
        vboxStep.setOpacity(visible ? 0 : 1);
        vbox.setOpacity(visible ? 0 : 1);

        Timeline timeline = new Timeline();

        KeyValue kv1 = new KeyValue(text1.opacityProperty(), visible ? 1 : 0, Interpolator.EASE_IN);
        KeyValue kv2 = new KeyValue(text2.opacityProperty(), visible ? 1 : 0, Interpolator.EASE_IN);
        KeyValue kv3 = new KeyValue(text3.opacityProperty(), visible ? 1 : 0, Interpolator.EASE_IN);
        KeyValue kv4 = new KeyValue(reminderNextDate.opacityProperty(), visible ? 1 : 0, Interpolator.EASE_IN);
        KeyValue kv5 = new KeyValue(vboxStep.opacityProperty(), visible ? 1 : 0, Interpolator.EASE_IN);
        KeyValue kv6 = new KeyValue(vbox.opacityProperty(), visible ? 1 : 0, Interpolator.EASE_IN);

        KeyFrame kf1 = new KeyFrame(Duration.seconds(duration), kv1);
        KeyFrame kf2 = new KeyFrame(Duration.seconds(duration), kv2);
        KeyFrame kf3 = new KeyFrame(Duration.seconds(duration), kv3);
        KeyFrame kf4 = new KeyFrame(Duration.seconds(duration), kv4);
        KeyFrame kf5 = new KeyFrame(Duration.seconds(duration), kv5);
        KeyFrame kf6= new KeyFrame(Duration.seconds(duration), kv6);

        timeline.getKeyFrames().addAll(kf1, kf2, kf3, kf4, kf5, kf6);
        timeline.play();
        timeline.setOnFinished(event -> {
            text1.setVisible(visible);
            text2.setVisible(visible);
            text3.setVisible(visible);
            reminderNextDate.setVisible(visible);
            vboxStep.setVisible(visible);
            vbox.setVisible(visible);
        });
    }
}