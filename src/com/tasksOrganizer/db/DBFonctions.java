package com.tasksOrganizer.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import com.tasksOrganizer.sample.Task;

public class DBFonctions {
    public static void saveTask(Task task){
        Statement state = null;

        Connection conn = DBConnect.getInstance().getConn();

        try {
            state = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        } catch (Exception e) {
            System.out.println("Erreur de creation du Statement");
        }

        try {
            if(!isTask(task.getNom()))
                state.executeUpdate("insert into Task(nom, description, importance, difficulte, echeance, tsupp) values ('" + task.getNom() + "','" + task.getDescription() + "'," + task.getImportance() + "," + task.getDifficulte() + ",'" + task.getEcheance() + "','" + task.getTsupp() + "')");

            else
                state.executeUpdate("update Task set nom = '"+ task.getNom() +"', description = '"+ task.getDescription() +"' , importance = "+ task.getImportance() + ", difficulte = " + task.getDifficulte() + ", echeance = '" + task.getEcheance() + "', tsupp = '" + task.getTsupp() + "' where nom = '"+ task.getNom() +"'");

            state.close();
        } catch (Exception e) {
            System.out.println("Echec de communication avec la base de donnees");
        }
    }






    public static boolean isTask(String nom){
        boolean exists = false;
        Statement state = null;
        ResultSet result = null;

        Connection conn = DBConnect.getInstance().getConn();

        try {
            state = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        } catch (Exception e) {
            System.out.println("Erreur de creation du Statement");
        }

        try {
            result = state.executeQuery("select nom from Task where nom = '" + nom + "' and ok = false");
            exists = result.first();

            result.close();
            state.close();
        } catch (Exception e) {
            System.out.println("Echec de communication avec la base de donnees");
        }

        return exists;
    }






    public static Task[] DBExtractTasks(){
        Statement state = null;
        ResultSet result = null;
        int id;
        Task[] tasks = new Task[0];
        String[] paramList = {"nom", "description", "importance", "difficulte", "echeance", "tsupp", "ok"};
        Object[] tab = new Object[paramList.length];
        int taille = 0;

        Connection conn = DBConnect.getInstance().getConn();

        try {
            state = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (Exception e) {
            System.out.println("Erreur de creation du Statement");
        }

        try {
            result = state.executeQuery("select id from Task");

            while(result.next())
                taille++;

            tasks = new Task[taille];
            result.relative(-1 * taille - 1);
            for(int i = 0; i < taille; i++){
                result.next();
                id = Integer.parseInt(result.getObject(1).toString());
                for(int j = 0; j < paramList.length; j++){
                    tab[j] = DBgetParam(paramList[j], "id", id);
                }

                tasks[i] = new Task(tab[0].toString(), tab[1].toString(), Integer.parseInt(tab[2].toString()), Integer.parseInt(tab[3].toString()), LocalDate.parse(tab[4].toString()), LocalDate.parse(tab[5].toString()), (Boolean)tab[6]);
            }

            result.close();
            state.close();
        } catch (Exception e) {
            System.out.println("Echec de communication avec la base de donnees");
            //e.printStackTrace();
        }

        return tasks;

    }






    public static Object DBgetParam(String nomParam, String nomIdentifiant, int valeurIdentifiant) {
        Object param = null;
        Statement state = null;
        ResultSet result = null;
        String nomTable = "Task";

        Connection conn = DBConnect.getInstance().getConn();

        try {
            state = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (Exception e) {
            System.out.println("Erreur de creation du Statement");
        }

        try {
            result = state.executeQuery("SELECT " + nomParam + " FROM " + nomTable + " where " + nomIdentifiant + "="
                    + valeurIdentifiant);

            if (result.next() && result.getObject(1) != null)
                param = result.getObject(1);

            state.close();
            result.close();
        } catch (Exception e) {
            System.out.println("Echec de communication avec la base de donnees");
        }

        return param;
    }






    public static void DBRemoveTask(String nom){
        Statement state = null;

        Connection conn = DBConnect.getInstance().getConn();

        try {
            state = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        } catch (Exception e) {
            System.out.println("Erreur de creation du Statement");
        }

        try {
            state.executeUpdate("Delete from Task where nom = '"+nom+"'");

            state.close();
        } catch (Exception e) {
            System.out.println("Echec de communication avec la base de donnees");
            //e.printStackTrace();
        }

    }

}
