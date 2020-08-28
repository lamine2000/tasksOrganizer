package com.tasksOrganizer.tray.models;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CustomStage extends Stage {
    private final Location bottomRight;
    private final SimpleDoubleProperty xLocationProperty = new SimpleDoubleProperty() {
        public void set(double newValue) {
            com.tasksOrganizer.tray.models.CustomStage.this.setX(newValue);
        }

        public double get() {
            return com.tasksOrganizer.tray.models.CustomStage.this.getX();
        }
    };
    private final SimpleDoubleProperty yLocationProperty = new SimpleDoubleProperty() {
        public void set(double newValue) {
            com.tasksOrganizer.tray.models.CustomStage.this.setY(newValue);
        }

        public double get() {
            return com.tasksOrganizer.tray.models.CustomStage.this.getY();
        }
    };

    public CustomStage(AnchorPane ap, StageStyle style) {
        this.initStyle(style);
        this.setSize(ap.getPrefWidth(), ap.getPrefHeight());
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double x = screenBounds.getMinX() + screenBounds.getWidth() - ap.getPrefWidth() - 2.0D;
        double y = screenBounds.getMinY() + screenBounds.getHeight() - ap.getPrefHeight() - 2.0D;
        this.bottomRight = new Location(x, y);
    }

    public Location getBottomRight() {
        return this.bottomRight;
    }

    public void setSize(double width, double height) {
        this.setWidth(width);
        this.setHeight(height);
    }

    public Location getOffScreenBounds() {
        Location loc = this.getBottomRight();
        return new Location(loc.getX() + this.getWidth(), loc.getY());
    }

    public void setLocation(Location loc) {
        this.setX(loc.getX());
        this.setY(loc.getY());
    }

    public SimpleDoubleProperty xLocationProperty() {
        return this.xLocationProperty;
    }

    public SimpleDoubleProperty yLocationProperty() {
        return this.yLocationProperty;
    }
}
