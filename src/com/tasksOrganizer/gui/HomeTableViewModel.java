package com.tasksOrganizer.gui;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

public class HomeTableViewModel {
    private final SimpleStringProperty name;
    private final SimpleStringProperty days;
    private final Button info, modif, delete, ok;

    public HomeTableViewModel(String nom, String days, Button info, Button modif, Button delete, Button ok) {

        this.name = new SimpleStringProperty(nom);
        this.days = new SimpleStringProperty(days);
        this.info = info;
        this.modif = modif;
        this.delete = delete;
        this.ok = ok;
    }

    public String getName() {
        return name.get();
    }

    public String getDays() {
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

    public Button getOk() {
        return ok;
    }


}
