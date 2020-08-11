package com.tasksOrganizer.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;

public class DBFonctions {
    public static void saveTask(String nom, String description, int difficulte, int importance, LocalDate echeance, LocalDate vtsuppose){
        Statement state = null;

        Connection conn = DBConnect.getInstance().getConn();

        try {
            state = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        } catch (Exception e) {
            System.out.println("Erreur de creation du Statement");
        }

        try {
            state.executeUpdate("insert into Task(nom, description, importance, difficulte, echeance, tsupp) values ('" + nom + "','" + description + "'," + importance + "," + difficulte + ",'" + echeance + "','" + vtsuppose + "')");
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

}
