package com.tasksOrganizer.gui;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import com.gluonhq.charm.glisten.control.TextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.tasksOrganizer.db.DBFonctions;

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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ImageView backImage = new ImageView(getClass().getResource("/images/back.png").toExternalForm());
        backButton.setGraphic(backImage);

        ImageView okImage = new ImageView(getClass().getResource("/images/ok1.png").toExternalForm());
        okButton.setGraphic(okImage);

        ImageView clearImage = new ImageView(getClass().getResource("/images/clear.png").toExternalForm());
        clearButton.setGraphic(clearImage);


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
    protected void difficultyStarsExitedAction(MouseEvent event){
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
    protected void importanceStarsExitedAction(MouseEvent event) {
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
    protected void handleEffacerButtonAction(ActionEvent event) {
        emptyAll();
    }

    @FXML
    protected void handleValiderButtonAction(ActionEvent event) {
        Window owner = nameField.getScene().getWindow();

        if(nameField.getText().isEmpty()){
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Erreur!", "Veuillez entrer le nom de la nouvelle tâche !");
            return;
        }

        if(eDatePicker.getValue() == null){
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Erreur!", "Veuillez préciser la date d'échéance de la nouvelle tâche !");
            return;
        }

        if(eDatePicker.getValue().isBefore(today()) || eDatePicker.getValue().isEqual(today())){
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Erreur!", "L'échéance doit etre ultérieure à la date du jour ("+today().getDayOfMonth()+"/"+today().getMonthValue()+"/"+today().getYear()+") !");
            return;
        }

        if(tsDatePicker.getValue() == null){
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Erreur!", "Veuillez estimez la date de fin de la tâche !");
            return;
        }

        if(tsDatePicker.getValue().isBefore(today()) || tsDatePicker.getValue().isEqual(today())){
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

        if(DBFonctions.isTask(nom)){
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Erreur!", "La tâche \"" + nom + "\" existe déjà dans la base de données !\nVeuillez renommer la nouvelle tâche.");
            nameField.setText("");
            return;
        }

        String descripiton = descriptionArea.getText();
        LocalDate echeance = eDatePicker.getValue();
        LocalDate tsuppose = tsDatePicker.getValue();



        DBFonctions.saveTask(nom, descripiton, difficulte, importance, echeance, tsuppose);
            AlertHelper.showAlert(Alert.AlertType.INFORMATION, owner, "Enregistrement réussi !", "La tâche \"" + nom + "\" a été enregistrée avec succès !");

        emptyAll();

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
    void handleBackButtonAction(ActionEvent event) throws IOException {

        if(finAnimation){
            finAnimation = false;
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/home.fxml"));
            Scene scene = backButton.getScene();
            root.getStylesheets().add(getClass().getResource("/css/home.css").toString());

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
    }

    }
