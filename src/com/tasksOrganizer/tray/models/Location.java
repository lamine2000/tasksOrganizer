package com.tasksOrganizer.tray.models;

public class Location {
    private final double x;
    private final double y;

    public Location(double xLoc, double yLoc) {
        this.x = xLoc;
        this.y = yLoc;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }
}