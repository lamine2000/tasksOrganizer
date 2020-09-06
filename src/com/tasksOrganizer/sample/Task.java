package com.tasksOrganizer.sample;

import com.tasksOrganizer.db.DBFonctions;

import java.time.LocalDate;

public class Task {
    private String nom;
    private LocalDate echeance;
    private LocalDate tsupp;
    private int difficulte;
    private int importance;
    private String description = "";
    private boolean ok = false;
    private LocalDate dateCreation;

    public Task() {}

    public Task(String nom, String description, int importance,  int difficulte, LocalDate echeance, LocalDate tsupp, boolean ok, LocalDate dateCreation) {
        this.nom = nom;
        this.echeance = echeance;
        this.tsupp = tsupp;
        this.difficulte = difficulte;
        this.importance = importance;
        this.description = description;
        this.ok = ok;
        this.dateCreation = dateCreation;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public LocalDate getEcheance() {
        return echeance;
    }

    public void setEcheance(LocalDate echeance) {
        this.echeance = echeance;
    }

    public LocalDate getTsupp() {
        return tsupp;
    }

    public void setTsupp(LocalDate tsupp) {
        this.tsupp = tsupp;
    }

    public int getDifficulte() {
        return difficulte;
    }

    public void setDifficulte(int difficulte) {
        this.difficulte = difficulte;
    }

    public int getImportance() {
        return importance;
    }

    public void setImportance(int importance) {
        this.importance = importance;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public static Task[] extractTasks(){
        return DBFonctions.DBExtractTasks();
    }

    public static void remove(String nom){
        DBFonctions.DBRemoveTask(nom);
    }

    public static void done(String name){
        DBFonctions.taskDone(name);
    }

    public static Boolean exists(String name){
        return DBFonctions.isTask(name);
    }

    public static int save(Task task){
       return DBFonctions.saveTask(task);
    }

    public static Task extract(String name){
        return DBFonctions.DBExtractTask(name);
    }

    public static void modify(String name, Task newTask){
        DBFonctions.modifyTask(name, newTask);
    }

    public static int getIdOf(String taskName){
        if(Task.exists(taskName))
            return Integer.parseInt(DBFonctions.DBgetParam2("id", "Task", "nom", taskName).toString());
        else
            return -1;
    }
}
