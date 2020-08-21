package com.tasksOrganizer.gui;

import com.gluonhq.charm.glisten.control.ProgressBar;
import com.tasksOrganizer.db.DBFonctions;
import com.tasksOrganizer.sample.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.net.URL;
import java.time.LocalDate;
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
    private Button closeButton;

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


    String taskName = MotherController.taskName;
    private static ImageView d1Image0, d2Image0, d3Image0, d4Image0, d5Image0;
    private static ImageView d1Image1, d2Image1, d3Image1, d4Image1, d5Image1;

    private static ImageView i1Image0, i2Image0, i3Image0, i4Image0, i5Image0;
    private static ImageView i1Image1, i2Image1, i3Image1, i4Image1, i5Image1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Task task = DBFonctions.DBExtractTask(taskName);

        Text descText = !task.getDescription().trim().equals("") ? new Text("Description :\n"+ task.getDescription()) : new Text();
        descText.setStyle("-fx-fill: orange");

        title.setText(taskName.toUpperCase());

        description.getChildren().add(descText);

        LocalDate date = task.getDateCreation();
        createdAt.setText("Creation : "+date.getDayOfWeek()+" "+date.getDayOfMonth()+" "+date.getMonth()+" "+date.getYear());

        long interval1 = ChronoUnit.DAYS.between(date, task.getEcheance());
        long interval2 = ChronoUnit.DAYS.between(date, LocalDate.now());
        double p = (double)(interval2/interval1);

        progress1.setProgress(p);
        legendpg1.setText(p*100+"% du temps disponible ont été consumé(s) ! \n Nombre restant de jours : "+(interval1-interval2));

        interval1 = ChronoUnit.DAYS.between(date, task.getTsupp());
        p = (double)(interval2/interval1);

        progress2.setProgress(p);
        if(!LocalDate.now().isAfter(task.getTsupp()))
            legendpg2.setText(p*100+"% du temps estimé ont été consumé(s) ! \n Nombre restant de jours : "+(interval1-interval2));
        else
            legendpg2.setText(p*100+"% du temps estimé ont été consumé(s) ! \n Suppositions dépassées de : "+(interval2-interval1)+"j");


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

    @FXML
    void handleCloseButton() {
        Stage stage =(Stage)(d1.getScene().getWindow());
        stage.close();
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
}
