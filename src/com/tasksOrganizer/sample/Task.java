package com.tasksOrganizer.sample;

import com.tasksOrganizer.db.DBFonctions;
import com.tasksOrganizer.exceptions.MysqlUnreachableException;

import java.time.LocalDate;

public class Task implements Cloneable {
    private String nom;
    private LocalDate echeance;
    private LocalDate tsupp;
    private int difficulte;
    private int importance;
    private String description = "";
    private boolean ok = false;
    private LocalDate dateCreation;

    @Override
    public Task clone() throws CloneNotSupportedException {
        Task clone = null;
        try {
            clone = (Task) super.clone();
            clone.nom = nom;
            clone.echeance = echeance;
            clone.tsupp = tsupp;
            clone.difficulte = difficulte;
            clone.importance = importance;
            clone.description = description;
            clone.ok = ok;
            clone.dateCreation = dateCreation;

        } catch (CloneNotSupportedException e) {
            System.out.println("Erreur de clonage de la tâche " + nom);
        }

        return clone;
    }

    public Task() {
    }

    public Task(String nom, String description, int importance, int difficulte, LocalDate echeance, LocalDate tsupp, boolean ok, LocalDate dateCreation) {
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

    public static Task[] extractTasks() throws MysqlUnreachableException {
        return DBFonctions.DBExtractTasks();
    }

    public static void remove(String nom) throws MysqlUnreachableException {
        DBFonctions.DBRemoveTask(nom);
    }

    public static void done(String name) throws MysqlUnreachableException {
        DBFonctions.taskDone(name);
    }

    public static Boolean exists(String name) throws MysqlUnreachableException {
        return DBFonctions.isTask(name);
    }

    public static int save(Task task) throws MysqlUnreachableException {
        return DBFonctions.saveTask(task);
    }

    public static Task extract(String name) {
        return DBFonctions.DBExtractTask(name);
    }

    public static void modify(String name, Task newTask) throws MysqlUnreachableException {
        DBFonctions.modifyTask(name, newTask);
    }

    public static int getIdOf(String taskName) throws MysqlUnreachableException {
        if (Task.exists(taskName))
            return Integer.parseInt(DBFonctions.DBgetParam2("id", "Task", "nom", taskName).toString());
        else
            return -1;
    }
}
