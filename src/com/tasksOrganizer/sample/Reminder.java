package com.tasksOrganizer.sample;

import com.tasksOrganizer.db.DBFonctions;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Reminder {
    private String taskName;
    private LocalTime step;
    private LocalDateTime nextDateTime;
    private boolean active;

    public Reminder() {
    }

    public Reminder(String taskName, LocalDateTime nextDateTime, LocalTime step, boolean active) {
        this.taskName = taskName;
        this.step = step;
        this.nextDateTime = nextDateTime;
        this.active = active;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public LocalTime getStep() {
        return step;
    }

    public void setStep(LocalTime step) {
        this.step = step;
    }

    public LocalDateTime getNextDateTime() {
        return nextDateTime;
    }

    public void setNextDateTime(LocalDateTime nextDateTime) {
        this.nextDateTime = nextDateTime;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public static void save(Reminder reminder, int idTask){
        DBFonctions.saveReminder(reminder, idTask);
    }

    public static void modify(String taskName, Reminder newReminder){
        DBFonctions.modifyReminder(newReminder, taskName);
    }

    public static Reminder extract(String taskName){
        return DBFonctions.DBExtractReminder(taskName);
    }

    public static Reminder[] extractReminders(){
        return DBFonctions.DBExtractReminders();
    }

    public static boolean exists(String taskName){
        return DBFonctions.isReminder(taskName);
    }

    public static void remove(String taskName){
        DBFonctions.DBRemoveReminder(taskName);
    }

    public static void refresh(LocalDateTime next, String taskName){
        DBFonctions.refreshReminder(next, taskName);
    }
}
