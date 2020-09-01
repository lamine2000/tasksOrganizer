package com.tasksOrganizer.db;

import com.tasksOrganizer.sample.Reminder;
import com.tasksOrganizer.sample.Task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DBFonctions {
    public static int saveTask(Task task) {

        Connection conn = DBConnect.getInstance().getConn();
        PreparedStatement state;
        int id = -1;

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

            id = Integer.parseInt((DBgetParam2("id", "Task","nom", task.getNom())).toString());

            state.close();

        } catch (Exception e) {
            System.out.println("Echec de communication avec la base de donnees");
        }

        return id;
    }


    public static boolean isTask(String nom) {
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


    public static Task[] DBExtractTasks() {
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

            while (result.next())
                taille++;

            tasks = new Task[taille];
            result.relative(-1 * taille - 1);
            for (int i = 0; i < taille; i++) {
                result.next();
                id = Integer.parseInt(result.getObject(1).toString());
                for (int j = 0; j < paramList.length; j++)
                    tab[j] = DBgetParam(paramList[j], "id", id);

                tasks[i] = new Task(tab[0].toString(), tab[1].toString(), Integer.parseInt(tab[2].toString()), Integer.parseInt(tab[3].toString()), LocalDate.parse(tab[4].toString()), LocalDate.parse(tab[5].toString()), (Boolean) tab[6], LocalDate.parse(tab[7].toString()));
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
            state = conn.prepareStatement("select " + nomParam + " from Task where " + nomIdentifiant + " = ?",
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


    public static void DBRemoveTask(String nom) {
        PreparedStatement state;

        Connection conn = DBConnect.getInstance().getConn();

        try {
            //state.executeUpdate("Delete from Task where nom = '"+nom.toLowerCase()+"'");
            state = conn.prepareStatement("delete from Task where nom = ?",
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            state.setString(1, nom);

            state.executeUpdate();
            state.close();

            DBRemoveReminder(nom);
        } catch (Exception e) {
            System.out.println("Echec de communication avec la base de donnees");
            //e.printStackTrace();
        }

    }


    public static void taskDone(String taskName) {
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


    public static Task DBExtractTask(String taskName) {
        Task task = new Task();
        String[] paramList = {"nom", "description", "importance", "difficulte", "echeance", "tsupp", "ok", "dateCreation"};
        Object[] tab = new Object[paramList.length];

        try {
            for (int j = 0; j < paramList.length; j++)
                tab[j] = DBgetParam2(paramList[j], "Task","nom", taskName);

            task = new Task(tab[0].toString(),
                    tab[1].toString(),
                    Integer.parseInt(tab[2].toString()),
                    Integer.parseInt(tab[3].toString()),
                    LocalDate.parse(tab[4].toString()),
                    LocalDate.parse(tab[5].toString()),
                    (Boolean) tab[6],
                    LocalDate.parse(tab[7].toString())
            );

        } catch (Exception e) {
            System.out.println("Echec de communication avec la base de donnees");
            //e.printStackTrace();
        }

        return task;
    }


    public static Object DBgetParam2(String nomParam,String nomTable ,String nomIdentifiant, String valeurIdentifiant) {
        Object param = null;
        PreparedStatement state;
        ResultSet result;

        Connection conn = DBConnect.getInstance().getConn();

        try {
            state = conn.prepareStatement("select " + nomParam + " from " + nomTable + " where " + nomIdentifiant + " = ?",
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
            //e.printStackTrace();
        }

        return param;
    }


    public static void modifyTask(String name, Task newtask) {
        PreparedStatement state;

        Connection conn = DBConnect.getInstance().getConn();

        try {
            state = conn.prepareStatement(
                    "update Task set nom = ?, description = ?, importance = ?, difficulte = ?, echeance = ?, tsupp = ?, dateCreation = ? where nom = ?",
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

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






    public static void saveReminder(Reminder reminder, int idTask){
        Connection conn = DBConnect.getInstance().getConn();
        PreparedStatement state;

        try {
            state = conn.prepareStatement("insert into Reminder (id, taskName, firstDateTime, step, nextDateTime, iteration, active) values (?,?,?,?,?,?,?)", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            String fdt = reminder.getFirstDateTime().toString().replace("T"," ");
            String ndt = reminder.getNextDateTime().toString().replace("T", " ");

            state.setInt(1, idTask);
            state.setString(2, reminder.getTaskName());
            state.setString(3, fdt);
            state.setString(4, reminder.getStep().toString());
            state.setString(5, ndt);
            state.setInt(6, reminder.getIteration());
            state.setBoolean(7, reminder.isActive());

            state.executeUpdate();
            state.close();

        } catch (Exception e) {
            System.out.println("Echec de communication avec la base de donnees");
            //e.printStackTrace();
        }
    }






    public static void modifyReminder(Reminder newReminder, String taskName){
        PreparedStatement state;

        Connection conn = DBConnect.getInstance().getConn();

        try {
            state = conn.prepareStatement(
                    "update Reminder set taskName = ?, firstDateTime = ?, step = ?, nextDateTime = ?, iteration = ?, active = ? where taskName = ?",
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            state.setString(1, newReminder.getTaskName());
            state.setString(2, newReminder.getFirstDateTime().toString());
            state.setString(3, newReminder.getStep().toString());
            state.setString(4, newReminder.getNextDateTime().toString());
            state.setInt(5, newReminder.getIteration());
            state.setBoolean(6, newReminder.isActive());
            state.setString(7, taskName);

            state.executeUpdate();

            state.close();
        } catch (Exception e) {
            System.out.println("Echec de communication avec la base de donnees");
            //e.printStackTrace();
        }
    }






    public static Reminder DBExtractReminder(String taskName){
        Reminder reminder = new Reminder();
        String[] paramList = {"taskName", "firstDateTime", "step", "nextDateTime", "iteration", "active"};
        Object[] tab = new Object[paramList.length];

        try {
            for (int j = 0; j < paramList.length; j++)
                tab[j] = DBgetParam2(paramList[j], "Reminder","taskName", taskName);

            reminder = new Reminder(
                    tab[0].toString(),
                    LocalDateTime.parse(tab[1].toString().replace(" ", "T")),
                    LocalTime.parse(tab[2].toString()),
                    LocalDateTime.parse(tab[3].toString().replace(" ", "T")),
                    Integer.parseInt(tab[4].toString()),
                    (Boolean) tab[5]
            );
        } catch (Exception e) {
            System.out.println("Echec de communication avec la base de donnees");
            //e.printStackTrace();
        }

        return reminder;
    }






    public static Reminder[] DBExtractReminders(){return new Reminder[0];}






    public static void DBRemoveReminder(String taskName){
        if(isReminder(taskName)){
            PreparedStatement state;

            Connection conn = DBConnect.getInstance().getConn();

            try {
                state = conn.prepareStatement("delete from Reminder where taskName = ?",
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_UPDATABLE);
                state.setString(1, taskName);

                state.executeUpdate();

                state.close();
            } catch (Exception e) {
                System.out.println("Echec de communication avec la base de donnees");
                //e.printStackTrace();
            }
        }
    }






    public static boolean isReminder(String taskName) {
        boolean exists = false;
        PreparedStatement state;
        ResultSet result;

        Connection conn = DBConnect.getInstance().getConn();

        try {
            state = conn.prepareStatement("select taskName from Reminder where taskName = ? and active = true",
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            state.setString(1, taskName);

            result = state.executeQuery();
            exists = result.first();
        } catch (Exception e) {
            System.out.println("Echec de communication avec la base de donnees");
        }

        return exists;
    }
}
