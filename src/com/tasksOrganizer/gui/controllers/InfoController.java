package com.tasksOrganizer.gui.controllers;

import com.tasksOrganizer.exceptions.MysqlUnreachableException;
import com.tasksOrganizer.sample.Reminder;
import com.tasksOrganizer.sample.Task;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

public class InfoController extends MotherController implements Initializable {

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
    private Text title;

    @FXML
    private ProgressBar progress1;

    @FXML
    private Text legendpg1;

    @FXML
    private Text createdAt;

    @FXML
    private ProgressBar progress2;

    @FXML
    private Text legendpg2;

    @FXML
    private TextFlow description;

    @FXML
    private Text textRmdr;

    String taskName = MotherController.taskName;
    private static ImageView d1Image0, d2Image0, d3Image0, d4Image0, d5Image0;
    private static ImageView d1Image1, d2Image1, d3Image1, d4Image1, d5Image1;

    private static ImageView i1Image0, i2Image0, i3Image0, i4Image0, i5Image0;
    private static ImageView i1Image1, i2Image1, i3Image1, i4Image1, i5Image1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Task task = Task.extract(taskName);
        LocalDateTime nextDateTime;
        try {
            if(Reminder.exists(taskName)){
                nextDateTime = Reminder.extract(taskName).getNextDateTime();
                textRmdr.setText("Prochaine notification prévue pour le "+localDateTimeTranslator(nextDateTime));
            }
            else
                textRmdr.setText("Notifications désactivées pour cette tâche");
        } catch (MysqlUnreachableException e) {
            //e.printStackTrace();
        }


        Text descText = !task.getDescription().isBlank() ? new Text("Description :\n"+ task.getDescription()) : new Text();
        descText.setStyle("-fx-fill: orange");

        title.setText(taskName);

        description.getChildren().add(descText);

        LocalDate dateCreation = task.getDateCreation();
        createdAt.setText("Création : "+localDateTranslator(dateCreation));

        long interval1 = ChronoUnit.DAYS.between(dateCreation, task.getEcheance());
        long interval2 = ChronoUnit.DAYS.between(dateCreation, LocalDate.now());
        double p = (double)(interval2)/(double)(interval1);

        progress1.setProgress(p);
        double pDisplay = (p*100+"").length() < 4 ? p*100 : Double.parseDouble((p*100+"").substring(0, 4));

        setProgressBarColor(progress1);
        if(!LocalDate.now().isAfter(task.getEcheance()))
            legendpg1.setText(pDisplay+"% du temps disponible ont été consumé(s) ! \n Nombre restant de jours : "+(interval1-interval2));
        else
            legendpg1.setText(pDisplay+"% du temps disponible ont été consumé(s) ! \n Retard de "+(interval2-interval1)+"j accusé sur l'échéance");

        if(p != 0){
            final Timeline timeline1 = new Timeline(
                    new KeyFrame(Duration.millis(0),    new KeyValue(progress1.progressProperty(), 0)),
                    new KeyFrame(Duration.millis(1500), new KeyValue(progress1.progressProperty(), p))
            );timeline1.play();
        }

        interval1 = ChronoUnit.DAYS.between(dateCreation, task.getTsupp());
        p = (double)(interval2)/(double)(interval1);

        progress2.setProgress(p);

        if((p*100+"").length() < 4)
            pDisplay = p*100;
        else
            pDisplay = Double.parseDouble((p*100+"").substring(0, 4));

        setProgressBarColor(progress2);
        if(!LocalDate.now().isAfter(task.getTsupp()))
            legendpg2.setText(pDisplay+"% du temps estimé ont été consumé(s) ! \n Nombre restant de jours : "+(interval1-interval2));
        else
            legendpg2.setText(pDisplay+"% du temps estimé ont été consumé(s) ! \n Suppositions dépassées de : "+(interval2-interval1)+"j");

        if(p != 0){
            final Timeline timeline2 = new Timeline(
                    new KeyFrame(Duration.millis(0),    new KeyValue(progress2.progressProperty(), 0)),
                    new KeyFrame(Duration.millis(1500), new KeyValue(progress2.progressProperty(), p))
            );timeline2.play();
        }

        setStars(task);
    }

    @FXML
    void handleCloseButton() {
        Stage stage =(Stage)(d1.getScene().getWindow());
        stage.close();
        MotherController.infoOpened = false;
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
    void handleDropFocus() {
        handleCloseButton();
    }

    private void setProgressBarColor(ProgressBar p){
        double v = p.getProgress();
        String cssProperty = "-fx-accent : ";

        if(v <= 0.2)
            cssProperty+="green";
        else if(v <= 0.4)
            cssProperty+="#6D8F00";
        else if(v <= 0.6)
            cssProperty+="#C5C500";
        else if(v <= 0.8)
            cssProperty+="orange";
        else
            cssProperty+="red";

        p.setStyle(cssProperty);
    }

    private void setStars(Task task){
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

    private String localDateTimeTranslator(LocalDateTime local){
        int month, day, year, hour, minute, dweek;
        String strMonth, strDweek;

        month = local.getMonthValue();
        day = local.getDayOfMonth();
        year = local.getYear();
        hour = local.getHour();
        minute = local.getMinute();
        dweek = local.getDayOfWeek().getValue();


        switch (month) {
            case 1 -> strMonth = "Janvier";
            case 2 -> strMonth = "Février";
            case 3 -> strMonth = "Mars";
            case 4 -> strMonth = "Avril";
            case 5 -> strMonth = "Mai";
            case 6 -> strMonth = "Juin";
            case 7 -> strMonth = "Juillet";
            case 8 -> strMonth = "Août";
            case 9 -> strMonth = "Septembre";
            case 10 -> strMonth = "Octobre";
            case 11 -> strMonth = "Novembre";
            case 12 -> strMonth = "Décembre";
            default -> throw new IllegalStateException("Valeur inattendue de mois: " + month);
        }

        switch (dweek){
            case 1 -> strDweek = "Lundi";
            case 2 -> strDweek = "Mardi";
            case 3 -> strDweek = "Mercredi";
            case 4 -> strDweek = "Jeudi";
            case 5 -> strDweek = "Vendredi";
            case 6 -> strDweek = "Samedi";
            case 7 -> strDweek = "Dimanche";
            default -> throw new IllegalStateException("Valeur inattendue de jour de la semaine: " + dweek);
        }

        return strDweek+", "+day+" "+ strMonth+" "+year+" à "+hour+"h"+minute+"min";
    }

    private String localDateTranslator(LocalDate local){
        int month, day, year, dweek;
        String strMonth, strDweek;

        month = local.getMonthValue();
        day = local.getDayOfMonth();
        year = local.getYear();
        dweek = local.getDayOfWeek().getValue();

        switch (month) {
            case 1 -> strMonth = "Janvier";
            case 2 -> strMonth = "Février";
            case 3 -> strMonth = "Mars";
            case 4 -> strMonth = "Avril";
            case 5 -> strMonth = "Mai";
            case 6 -> strMonth = "Juin";
            case 7 -> strMonth = "Juillet";
            case 8 -> strMonth = "Août";
            case 9 -> strMonth = "Septembre";
            case 10 -> strMonth = "Octobre";
            case 11 -> strMonth = "Novembre";
            case 12 -> strMonth = "Décembre";
            default -> throw new IllegalStateException("Valeur inattendue de mois: " + month);
        }

        switch (dweek){
            case 1 -> strDweek = "Lundi";
            case 2 -> strDweek = "Mardi";
            case 3 -> strDweek = "Mercredi";
            case 4 -> strDweek = "Jeudi";
            case 5 -> strDweek = "Vendredi";
            case 6 -> strDweek = "Samedi";
            case 7 -> strDweek = "Dimanche";
            default -> throw new IllegalStateException("Valeur inattendue de jour de la semaine: " + dweek);
        }

        return strDweek+", "+day+" "+ strMonth+" "+year;
    }
}
