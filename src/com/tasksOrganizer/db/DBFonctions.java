package com.tasksOrganizer.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import com.tasksOrganizer.sample.Task;

public class DBFonctions {
    public static void saveTask(Task task){

        Connection conn = DBConnect.getInstance().getConn();
        PreparedStatement state;

        try {
                state = conn.prepareStatement("insert into Task(nom, description, importance, difficulte, echeance, tsupp, dateCreation) values (?,?,?,?,?,?,?)", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                state.setString(1, task.getNom());
                state.setString(2, task.getDescription());
                state.setInt(3, task.getImportance());
                state.setInt(4, task.getDifficulte());
                state.setString(5, task.getEcheance().toString());
                state.setString(6, task.getTsupp().toString());
                state.setString(7, task.getDateCreation().toString());

                state.executeUpdate();
                state.close();

        } catch (Exception e) {
            System.out.println("Echec de communication avec la base de donnees");
        }
    }






    public static boolean isTask(String nom){
        boolean exists = false;
        PreparedStatement state;
        ResultSet result;

        Connection conn = DBConnect.getInstance().getConn();

        try {
            state = conn.prepareStatement("select nom from Task where nom = ? and ok = ?",
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            state.setString(1, nom);
            state.setBoolean(2, false);

            result = state.executeQuery();
            exists = result.first();
        } catch (Exception e) {
            System.out.println("Echec de communication avec la base de donnees");
        }

        return exists;
    }






    public static Task[] DBExtractTasks(){
        PreparedStatement state;
        ResultSet result;
        int id;
        Task[] tasks = new Task[0];
        String[] paramList = {"nom", "description", "importance", "difficulte", "echeance", "tsupp", "ok", "dateCreation"};
        Object[] tab = new Object[paramList.length];
        int taille = 0;

        Connection conn = DBConnect.getInstance().getConn();

        try {
            state = conn.prepareStatement("select id from Task where ok = false",
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            result = state.executeQuery();

            while(result.next())
                taille++;

            tasks = new Task[taille];
            result.relative(-1 * taille -1);
            for(int i = 0; i < taille; i++){
                result.next();
                id = Integer.parseInt(result.getObject(1).toString());
                for(int j = 0; j < paramList.length; j++)
                    tab[j] = DBgetParam(paramList[j], "id", id);

                tasks[i] = new Task(tab[0].toString(), tab[1].toString(), Integer.parseInt(tab[2].toString()), Integer.parseInt(tab[3].toString()), LocalDate.parse(tab[4].toString()), LocalDate.parse(tab[5].toString()), (Boolean)tab[6], LocalDate.parse(tab[7].toString()));
            }

            result.close();
            state.close();
        } catch (Exception e) {
            System.out.println("Echec de communication avec la base de donnees");
            e.printStackTrace();
        }

        return tasks;

    }






    public static Object DBgetParam(String nomParam, String nomIdentifiant, int valeurIdentifiant) {
        Object param = null;
        PreparedStatement state;
        ResultSet result;

        Connection conn = DBConnect.getInstance().getConn();

        try {
            state = conn.prepareStatement("select "+nomParam+" from Task where "+nomIdentifiant+" = ?",
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            state.setInt(1, valeurIdentifiant);

            result = state.executeQuery();

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
        PreparedStatement state;

        Connection conn = DBConnect.getInstance().getConn();

        try {
            //state.executeUpdate("Delete from Task where nom = '"+nom.toLowerCase()+"'");
            state = conn.prepareStatement("delete from Task where nom = ?",
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            state.setString(1,  nom);

            state.executeUpdate();

            state.close();
        } catch (Exception e) {
            System.out.println("Echec de communication avec la base de donnees");
            //e.printStackTrace();
        }

    }






    public static void taskDone(String taskName){
        PreparedStatement state;

        Connection conn = DBConnect.getInstance().getConn();

        try {
            state = conn.prepareStatement("update Task set ok = ? where nom = ?",
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            state.setBoolean(1, true);
            state.setString(2, taskName);

            state.executeUpdate();

            state.close();
        } catch (Exception e) {
            System.out.println("Echec de communication avec la base de donnees");
            //e.printStackTrace();
        }

    }






    public static Task DBExtractTask(String taskName){
        Task task = new Task();
        String[] paramList = {"nom", "description", "importance", "difficulte", "echeance", "tsupp", "ok", "dateCreation"};
        Object[] tab = new Object[paramList.length];

        try {
                for(int j = 0; j < paramList.length; j++)
                    tab[j] = DBgetParam2(paramList[j], "nom", taskName);

                task = new Task(tab[0].toString(), tab[1].toString(), Integer.parseInt(tab[2].toString()), Integer.parseInt(tab[3].toString()), LocalDate.parse(tab[4].toString()), LocalDate.parse(tab[5].toString()), (Boolean)tab[6], LocalDate.parse(tab[7].toString()));

        } catch (Exception e) {
            System.out.println("Echec de communication avec la base de donnees");
            //e.printStackTrace();
        }

        return task;

    }






    public static Object DBgetParam2(String nomParam, String nomIdentifiant, String valeurIdentifiant) {
        Object param = null;
        PreparedStatement state;
        ResultSet result;

        Connection conn = DBConnect.getInstance().getConn();

        try {
            state = conn.prepareStatement("select "+nomParam+" from Task where "+nomIdentifiant+" = ?",
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            state.setString(1, valeurIdentifiant);

            result = state.executeQuery();

            if (result.next() && result.getObject(1) != null)
                param = result.getObject(1);

            state.close();
            result.close();
        } catch (Exception e) {
            System.out.println("Echec de communication avec la base de donnees");
        }

        return param;
    }







    public static void modifyTask(String name, Task newtask){
        PreparedStatement state;

        Connection conn = DBConnect.getInstance().getConn();

        try {
            state = conn.prepareStatement(
                    "update Task set nom = ?, description = ?, importance = ?, difficulte = ?, echeance = ?, tsupp = ?, dateCreation = ? where nom = ?",
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            state.setString(1, newtask.getNom());
            state.setString(2, newtask.getDescription());
            state.setInt(3, newtask.getImportance());
            state.setInt(4, newtask.getDifficulte());
            state.setString(5, newtask.getEcheance().toString());
            state.setString(6, newtask.getTsupp().toString());
            state.setString(7, newtask.getDateCreation().toString());
            state.setString(8, name);

            state.executeUpdate();

            state.close();
        } catch (Exception e) {
            System.out.println("Echec de communication avec la base de donnees");
            //e.printStackTrace();
        }
    }

}
