package com.tasksOrganizer.gui;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

public class HomeTableViewModel {
    private final SimpleStringProperty name;
    private final SimpleIntegerProperty days;
    private final Button info, modif, delete;
    private final CheckBox  checkbox;

    public HomeTableViewModel(String nom, int days, Button info, Button modif, Button delete, CheckBox checkbox) {

        this.name = new SimpleStringProperty(nom);
        this.days = new SimpleIntegerProperty(days);
        this.info = info;
        this.modif = modif;
        this.delete = delete;
        this.checkbox = checkbox;
    }

    public String getName() {
        return name.get();
    }

    public int getDays() {
        return days.get();
    }

    public Button getInfo() {
        return info;
    }

    public Button getModif() {
        return modif;
    }

    public Button getDelete() {
        return delete;
    }

    public CheckBox getCheckbox() {
        return checkbox;
    }
}
