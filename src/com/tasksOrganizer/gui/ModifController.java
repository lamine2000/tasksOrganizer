package com.tasksOrganizer.gui;


import com.gluonhq.charm.glisten.control.TextField;
import com.tasksOrganizer.sample.Task;
import com.tasksOrganizer.tray.animations.AnimationType;
import com.tasksOrganizer.tray.notification.NotificationType;
import com.tasksOrganizer.tray.notification.TrayNotification;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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


    Task task = new Task();

    HomeTableViewModel row;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

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

        setStars(task);

        nameField.setText(task.getNom());

        descriptionArea.setText(task.getDescription());

        eDatePicker.setValue(task.getEcheance());

        tsDatePicker.setValue(task.getTsupp());

        importance = task.getImportance();

        difficulte = task.getDifficulte();

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
    protected void handleValiderButtonAction() {
        Window owner = nameField.getScene().getWindow();

        if (nameField.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Erreur!", "Veuillez entrer le nom de la nouvelle tâche !");
            return;
        }

        if (eDatePicker.getValue() == null) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Erreur!", "Veuillez préciser la date d'échéance de la nouvelle tâche !");
            return;
        }

        if (eDatePicker.getValue().isBefore(today()) || eDatePicker.getValue().isEqual(today())) {
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

        String nom = nameField.getText().toLowerCase();

        String descripiton = descriptionArea.getText();
        LocalDate echeance = eDatePicker.getValue();
        LocalDate tsuppose = tsDatePicker.getValue();

        Task task2 = new Task(nom, descripiton, importance, difficulte, echeance, tsuppose, false, task.getDateCreation());
        Task.modify(task.getNom(), task2);

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

    private void updateTable() {
        list.clear();

        Task[] tasks = Task.extractTasks();
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

                Task.remove(name);
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

            oks[i].setId(row.getName() + i);
            oks[i].getStyleClass().add("doneButton");
            referenceDone[i] = i;
            row.getOk().setOnAction(event -> {

                String name = ((Button) (event.getSource())).getId();
                int index = Integer.parseInt(name.substring(name.length() - 1));
                name = name.substring(0, name.length() - 1);

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

        table.getItems().remove(list);
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

            Parent root = FXMLLoader.load(getClass().getResource("/fxml/infoTask.fxml"));
            root.getStylesheets().add(getClass().getResource("/css/info.css").toExternalForm());
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

            Parent root = FXMLLoader.load(getClass().getResource("/fxml/modifTask.fxml"));
            root.getStylesheets().add(getClass().getResource("/css/modif.css").toExternalForm());
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
    }
}