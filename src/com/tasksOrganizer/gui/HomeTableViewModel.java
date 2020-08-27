package com.tasksOrganizer.gui;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;

public class HomeTableViewModel {
    private final SimpleStringProperty name;
    private final SimpleStringProperty days;
    private final SimpleObjectProperty<Button> info, modif, delete, ok;

    public HomeTableViewModel(String nom, String days, Button info, Button modif, Button delete, Button ok) {

        this.name = new SimpleStringProperty(nom);
        this.days = new SimpleStringProperty(days);
        this.info = new SimpleObjectProperty<Button>(info);
        this.modif = new SimpleObjectProperty<Button>(modif);
        this.delete = new SimpleObjectProperty<Button>(delete);
        this.ok = new SimpleObjectProperty<Button>(ok);
    }

    public String getName() {
        return name.get();
    }

    public String getDays() {
        return days.get();
    }

    public Button getInfo() {
        return info.get();
    }

    public Button getModif() {
        return modif.get();
    }

    public Button getDelete() {
        return delete.get();
    }

    public Button getOk() {
        return ok.get();
    }
}
