package com.tasksOrganizer.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AProposController implements Initializable {

    @FXML
    Label clipboardMsg;

    @FXML
    Hyperlink hp;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clipboardMsg.setVisible(false);
        hp.setTooltip(new Tooltip("Copier le lien dans le presse papier..."));
    }

    @FXML
    public void handleHyperLinkAction(){
        //copie le lien github du projet dans le presse papiers
        String link = "https://www.github.com/lamine2000/tasksOrganizer";
        copyToClipboardText(link);
        clipboardMsg.setVisible(true);

    }

    public void copyToClipboardText(String s) {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();

        content.putString(s);
        clipboard.setContent(content);

    }

    @FXML
    public void handleMouseExcitedAction(){
        //fermer la fenetre
        Stage stage = (Stage) (clipboardMsg.getScene().getWindow());
        stage.close();
    }
}
