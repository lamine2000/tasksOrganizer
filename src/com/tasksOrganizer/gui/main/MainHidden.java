package com.tasksOrganizer.gui.main;

import com.coreoz.wisp.Scheduler;
import com.coreoz.wisp.schedule.Schedules;
import com.tasksOrganizer.exceptions.MysqlUnreachableException;
import com.tasksOrganizer.sample.Reminder;
import com.tasksOrganizer.sample.Task;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;


public class MainHidden extends Application {
    private LocalDateTime next;
    private LocalTime step;
    private String name, message;
    private Process process;

    @Override
    public void start(Stage primaryStage){

        Scheduler scheduler = new Scheduler();

        scheduler.schedule(
                () -> {
                    //code à exécuter à chaque 2 secondes
                    System.out.println("tic");

                    Reminder[] reminders = new Reminder[0];
                    try {
                        reminders = Reminder.extractReminders();
                    } catch (MysqlUnreachableException e) {
                        //e.printStackTrace();
                    }
                    LocalDateTime[] nextDateTimes = new LocalDateTime[reminders.length];

                    for (int i = 0; i < reminders.length; i++){
                        nextDateTimes[i] = reminders[i].getNextDateTime();

                        if(nextDateTimes[i].isBefore(LocalDateTime.now().minusSeconds(5))){
                            //notif ratées
                            try {
                                showMissedNotif(reminders[i]);
                            } catch (IOException | InterruptedException | MysqlUnreachableException e) {
                                //e.printStackTrace();
                            }
                        }

                        else if(nextDateTimes[i].isBefore(LocalDateTime.now().plusSeconds(5)) && nextDateTimes[i].isAfter(LocalDateTime.now().minusSeconds(5))){
                            try {
                                showNotif(reminders[i]);
                            } catch (IOException | InterruptedException | MysqlUnreachableException e ) {
                                //e.printStackTrace();
                            }
                        }
                    }
                }
                ,Schedules.fixedDelaySchedule(Duration.ofSeconds(2)) // the schedule associated to the runnable
        );

    }


    private void showMissedNotif(Reminder reminder) throws IOException, InterruptedException, MysqlUnreachableException {
        step = reminder.getStep();
        LocalDate date;
        LocalTime time;
        name = "Tâche : "+reminder.getTaskName();
        LocalDateTime now = LocalDateTime.now();
        next = reminder.getNextDateTime();

        while(next.isBefore(now)) {
            date = LocalDate.parse(next.toString().split("T")[0]);
            time = LocalTime.parse(next.toString().split("T")[1]);
            message = "Rappel__Manqué_:__Le__" + date.toString() + "__à__" + time.getHour() + "h" + time.getMinute() + "min";
            //process = Runtime.getRuntime().exec(String.format("notify-send %s %s", name, message));

            process = Runtime.getRuntime().exec(new String[]{"notify-send", name, message}, null);
            process.waitFor();

            next = next.plusHours(step.getHour()).plusMinutes(step.getMinute());
            Reminder.refresh(next , name);
        }
    }

    private void showNotif(Reminder reminder) throws IOException, InterruptedException, MysqlUnreachableException {
        next = reminder.getNextDateTime();
        step = reminder.getStep();
        name = "Tâche : "+reminder.getTaskName();
        Task task = Task.extract(name);
        long interval = ChronoUnit.DAYS.between(LocalDate.now(), task.getEcheance());
        message = "Rappel_:__Il__vous__reste__encore__"+ interval +"j";

        process = Runtime.getRuntime().exec(new String[]{"notify-send", name, message}, null);
        process.waitFor();

        next = next.plusHours(step.getHour()).plusMinutes(step.getMinute());
        Reminder.refresh(next, name);
    }

    public static void main(String[] args){ launch(args); }

}
