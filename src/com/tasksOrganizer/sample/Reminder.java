package com.tasksOrganizer.sample;

import com.tasksOrganizer.db.DBFonctions;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Reminder {
    private String taskName;
    private LocalDateTime firstDateTime;
    private LocalTime step;
    private LocalDateTime nextDateTime;
    private int iteration;
    private boolean active;

    public Reminder() {
    }

    public Reminder(String taskName, LocalDateTime firstDateTime, LocalTime step, LocalDateTime nextDateTime, int iteration, boolean active) {
        this.taskName = taskName;
        this.firstDateTime = firstDateTime;
        this.step = step;
        this.nextDateTime = nextDateTime;
        this.iteration = iteration;
        this.active = active;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public LocalDateTime getFirstDateTime() {
        return firstDateTime;
    }

    public void setFirstDateTime(LocalDateTime firstDateTime) {
        this.firstDateTime = firstDateTime;
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

    public int getIteration() {
        return iteration;
    }

    public void setIteration(int iteration) {
        this.iteration = iteration;
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

    public static void refresh(int iteration, LocalDateTime next, String taskName){
        DBFonctions.refreshReminder(iteration, next, taskName);
    }
}
