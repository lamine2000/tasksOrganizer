package com.tasksOrganizer.sample;

import com.tasksOrganizer.db.DBFonctions;

public class Task {
    private String nom;
    private int echeance;
    private int tsupp;
    private short difficulte;
    private short importance;
    private String description = "";
    private boolean ok = false;
    private short rang;

    public Task() {}

    public Task(String nom, int echeance, int tsupp, short difficulte, short importance, String description, boolean ok, short rang) {
        this.nom = nom;
        this.echeance = echeance;
        this.tsupp = tsupp;
        this.difficulte = difficulte;
        this.importance = importance;
        this.description = description;
        this.ok = ok;
        this.rang = rang;
    }

    /*public static Task[] extractTasks(){
        return DBExtractTasks();
    }

    public static void removeTask(String nom){
        DBRemoveTask(nom);
    }

    public static void modifyTask(int nom, int echeance, int tsupp, int difficulte, int importance, String description){
        DBModifyTask(nom, echeance, tsupp, difficulte, importance, description);
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getEcheance() {
        return echeance;
    }

    public void setEcheance(int echeance) {
        this.echeance = echeance;
    }

    public int getTsupp() {
        return tsupp;
    }

    public void setTsupp(int tsupp) {
        this.tsupp = tsupp;
    }

    public short getDifficulte() {
        return difficulte;
    }

    public void setDifficulte(short difficulte) {
        this.difficulte = difficulte;
    }

    public short getImportance() {
        return importance;
    }

    public void setImportance(short importance) {
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

    public short getRang() {
        return rang;
    }

    public void setRang(short rang) {
        this.rang = rang;
    }*/
}
