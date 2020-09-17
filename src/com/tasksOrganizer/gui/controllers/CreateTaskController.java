package com.tasksOrganizer.gui.controllers;

import com.gluonhq.charm.glisten.control.TextField;
import com.tasksOrganizer.gui.AlertHelper;
import com.tasksOrganizer.gui.TimeSpinner;
import com.tasksOrganizer.exceptions.MysqlUnreachableException;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class CreateTaskController implements Initializable {
    private static ImageView d1Image0, d2Image0, d3Image0, d4Image0, d5Image0;
    private static ImageView d1Image1, d2Image1, d3Image1, d4Image1, d5Image1;

    private static ImageView i1Image0, i2Image0, i3Image0, i4Image0, i5Image0;
    private static ImageView i1Image1, i2Image1, i3Image1, i4Image1, i5Image1;

    private static boolean starDClicked = false;
    private static boolean starIClicked = false;

    private static int difficulte, importance;
    private boolean finAnimation = true;

    @FXML
    private AnchorPane container;

    @FXML
    private Button backButton;

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
    private VBox vbox, vboxStep;

    @FXML
    private DatePicker reminderFirstDate;

    @FXML
    private Text text1, text2, text3;

    @FXML
    private CheckBox reminderOn;

    TimeSpinner tsFirst, tsStep;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        reminderOn.setTooltip(new Tooltip("Activer les rappels"));

        reminderFirstDate.setTooltip(new Tooltip("Date de la première notification"));
        reminderFirstDate.setEditable(false);

        tsFirst = new TimeSpinner();
        tsFirst.setTooltip(new Tooltip("Heure de la première notification"));
        tsFirst.setId("firsTimeSpinner");
        vbox.getChildren().add(tsFirst);

        tsStep = new TimeSpinner();
        tsStep.setTooltip(new Tooltip("Fréquence des notifications"));
        tsStep.setId("StepTimeSpinner");
        vboxStep.getChildren().add(tsStep);

        reminderOn.setSelected(false);
        setReminderStuffVisility(false);

        ImageView backImage = new ImageView(getClass().getResource("/images/back.png").toExternalForm());
        backButton.setGraphic(backImage);
        backButton.setTooltip(new Tooltip("Revenir à la page d'accueil sans enregistrer"));

        ImageView okImage = new ImageView(getClass().getResource("/images/ok1.png").toExternalForm());
        okButton.setGraphic(okImage);
        okButton.setTooltip(new Tooltip("Enregistrer la nouvelle tâche"));

        ImageView clearImage = new ImageView(getClass().getResource("/images/clear.png").toExternalForm());
        clearButton.setGraphic(clearImage);
        clearButton.setTooltip(new Tooltip("Vider les champs"));

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

        setStars();
    }

    @FXML
    protected void difficultyStarHoverAction(MouseEvent event){
        Object source = event.getSource();
        int nb;

        if(source.equals(d1))
            nb = 1;

        else if(source.equals(d2))
            nb = 2;

        else if(source.equals(d3))
            nb = 3;

        else if(source.equals(d4))
            nb = 4;

        else /*if(source.equals(d5))*/
            nb = 5;

        setGraphics('d', nb);
    }

    @FXML
    protected void difficultyStarsExitedAction(){
        if(!starDClicked)
            setGraphics('d', 0);
        else
            setGraphics('d', difficulte);
    }

    @FXML
    protected void importanceStarHoverAction(MouseEvent event) {
        Object source = event.getSource();
        int nb;

        if(source.equals(i1))
            nb = 1;

        else if(source.equals(i2))
            nb = 2;

        else if(source.equals(i3))
            nb = 3;

        else if(source.equals(i4))
            nb = 4;

        else /*if(source.equals(i5))*/
            nb = 5;

        setGraphics('i', nb);
    }

    @FXML
    protected void importanceStarsExitedAction() {
        if(!starIClicked)
            setGraphics('i', 0);
        else
            setGraphics('i', importance);
    }

    private void setGraphics(char c, int i){
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

        for(int count = 1; count <= 5; count++){
            if(count <= i)
                tab[count-1].setGraphic(images1[count-1]);

            else
                tab[count-1].setGraphic(images0[count-1]);
        }

        }

    @FXML
    protected void handleEffacerButtonAction() {
        emptyAll();
    }

    @FXML
    protected void handleValiderButtonAction() throws MysqlUnreachableException {
        Window owner = nameField.getScene().getWindow();

        if(nameField.getText().isEmpty()){
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Erreur!", "Veuillez entrer le nom de la nouvelle tâche !");
            return;
        }

        if(eDatePicker.getValue() == null){
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Erreur!", "Veuillez préciser la date d'échéance de la nouvelle tâche !");
            return;
        }

        if(!eDatePicker.getValue().isAfter(today())){
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Erreur!", "L'échéance doit etre ultérieure à la date du jour ("+today().getDayOfMonth()+"/"+today().getMonthValue()+"/"+today().getYear()+") !");
            return;
        }

        if(tsDatePicker.getValue() == null){
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Erreur!", "Veuillez estimez la date de fin de la tâche !");
            return;
        }

        if(!tsDatePicker.getValue().isAfter(today())){
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Erreur!", "la date supposée de fin de tâche doit etre ultérieure à la date du jour ("+today().getDayOfMonth()+"/"+today().getMonthValue()+"/"+today().getYear()+") !");
            return;
        }

        if(tsDatePicker.getValue().isAfter(eDatePicker.getValue())){
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Erreur!", "La date de fin de tâche estimée ne doit pas être au delà de l'échéance!");
            return;
        }

        if(!starDClicked){
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Erreur!", "Veuillez indiquer le niveau de difficulté de la tâche (1 étoile pour \"très facile\", ..., 5 étoiles pour \"très difficile\")");
            return;
        }

        if(!starIClicked){
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Erreur!", "Veuillez indiquer le niveau d'importance de la tâche (1 étoile pour \"très peu importante\", ..., 5 étoiles pour \"très importante\")");
            return;
        }

        String nom = nameField.getText().toLowerCase();

        if(Task.exists(nom)){
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Erreur!", "La tâche \"" + nom + "\" existe déjà dans la base de données !\nVeuillez renommer la nouvelle tâche.");
            nameField.setText("");
            return;
        }

        String descripiton = descriptionArea.getText();
        LocalDate echeance = eDatePicker.getValue();
        LocalDate tsuppose = tsDatePicker.getValue();

        Task task = new Task(nom, descripiton, importance, difficulte, echeance, tsuppose, false, today());
        LocalDateTime fdt;
        boolean allOk = false;
        int idTask;
        Reminder reminder = null;

        if(reminderOn.isSelected()){

            if(reminderFirstDate.getValue() == null){
                AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Erreur!", "Veuillez entrer la date de la première notification");
                return;
            }

            if(!reminderFirstDate.getValue().isBefore(echeance)){
                AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Erreur!", "La date du premier rappel doit être antérieure à l'échéance");
                return;
            }

            fdt = LocalDateTime.of(reminderFirstDate.getValue(), tsFirst.getValue());
            if(!fdt.isAfter(LocalDateTime.now())){
                AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Erreur!", "La date du rappel doit être ultérieure à : "+LocalDateTime.now().toString().split("\\.")[0].replace("T", "   ")+" (càd maintenant)");
                return;
            }

            allOk = true;

            LocalDateTime firstDateTime = fdt;
            LocalTime step = tsStep.getValue();

            reminder = new Reminder(nom, firstDateTime, step, true);
            //a la creation d une tache, la prochaine notificationn de cette tache est aussi la premiere
        }

        if(allOk){ //task with reminder
            idTask = Task.save(task);
            Reminder.save(reminder, idTask);
        }
        else //task without reminder
            Task.save(task);


        emptyAll();

        TrayNotification tray = new TrayNotification();
        tray.setTitle("Création réussie");
        tray.setMessage("La tâche '"+task.getNom()+"' a été créée avec succès.");
        tray.setRectangleFill(Paint.valueOf("#6D8F00"));
        tray.setAnimationType(AnimationType.SLIDE);
        tray.setNotificationType(NotificationType.SUCCESS);
        tray.showAndDismiss(Duration.seconds(5));

    }

    @FXML
    protected void handleStarClickedAction(ActionEvent event){
        Button[] tabD = {d1, d2, d3, d4, d5};
        boolean trouveD = false;
        Object source = event.getSource();

        for(Button elt : tabD){
            if(source.equals(elt)) {
                trouveD = true;
                break;
            }
        }

        if(!trouveD) {
            starIClicked = true;

            if(source.equals(i1))
                importance = 1;

            else if(source.equals(i2))
                importance = 2;

            else if(source.equals(i3))
                importance = 3;

            else if(source.equals(i4))
                importance = 4;

            else /*if(source.equals(i5))*/
                importance = 5;
        }

        else{
            starDClicked = true;

            if(source.equals(d1))
                difficulte = 1;

            else if(source.equals(d2))
                difficulte = 2;

            else if(source.equals(d3))
                difficulte = 3;

            else if(source.equals(d4))
                difficulte = 4;

            else /*if(source.equals(d5))*/
                difficulte = 5;
        }
    }

    @FXML
    void handleBackButtonAction() throws IOException {

        if(finAnimation){
            finAnimation = false;
            Parent root = FXMLLoader.load(getClass().getResource("/views/home.fxml"));
            Scene scene = backButton.getScene();
            root.getStylesheets().add(getClass().getResource("/stylesheets/home.css").toString());

            root.translateXProperty().set(scene.getWidth());
            StackPane parentContainer = (StackPane)scene.getRoot();
            parentContainer.getChildren().add(root);

            Timeline timeLine = new Timeline();
            KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
            KeyFrame kf = new KeyFrame(Duration.seconds(0.6), kv);
            timeLine.getKeyFrames().add(kf);

            timeLine.setOnFinished(event1 -> {
                parentContainer.getChildren().remove(container);
                finAnimation = true;
            });

            timeLine.play();
        }

    }

    private LocalDate today(){
        return LocalDate.now();
    }

    private void emptyAll(){
        nameField.setText("");
        descriptionArea.clear();
        starDClicked = false;
        starIClicked = false;
        setGraphics('d', 0);
        setGraphics('i', 0);
        eDatePicker.setValue(null);
        tsDatePicker.setValue(null);
        setReminderStuffVisility(false);
        reminderOn.setSelected(false);
    }

    private void setStars(){
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

        setGraphics('d', 0);

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

        setGraphics('i', 0);
    }

    @FXML
    void handleReminderCheckBoxAction() {
        boolean visible = reminderOn.isSelected();
        setReminderStuffVisility(visible);
    }

    void setReminderStuffVisility(boolean visible){
        text1.setVisible(visible);
        text2.setVisible(visible);
        text3.setVisible(visible);
        reminderFirstDate.setVisible(visible);
        vboxStep.setVisible(visible);
        vbox.setVisible(visible);
    }
}
