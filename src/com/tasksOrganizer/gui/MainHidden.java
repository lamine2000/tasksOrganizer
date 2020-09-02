package com.tasksOrganizer.gui;

import com.coreoz.wisp.Scheduler;
import com.coreoz.wisp.schedule.Schedules;
import com.tasksOrganizer.sample.Reminder;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


public class MainHidden extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scheduler scheduler = new Scheduler();

        scheduler.schedule(
                () -> {
                    //code à exécuter à chaque minute
                    System.out.println("tic");

                    Reminder[] reminders = Reminder.extractReminders();
                    LocalDateTime[] nextDateTimes = new LocalDateTime[reminders.length];

                    for (int i = 0; i < reminders.length; i++){
                        nextDateTimes[i] = reminders[i].getNextDateTime();

                        if(nextDateTimes[i].isBefore(LocalDateTime.now().minusSeconds(5))){
                            //notif ratées
                            try {
                                showMissedNotif(reminders[i]);
                            } catch (IOException | InterruptedException e) {
                                //e.printStackTrace();
                            }
                        }

                        else if(nextDateTimes[i].isBefore(LocalDateTime.now().plusSeconds(5)) && nextDateTimes[i].isAfter(LocalDateTime.now().minusSeconds(5))){
                            try {
                                showNotif(reminders[i]);
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                //e.printStackTrace();
                            }
                        }
                    }
                }
                ,Schedules.fixedDelaySchedule(Duration.ofSeconds(1)) // the schedule associated to the runnable
        );

    }


    private void showMissedNotif(Reminder reminder) throws IOException, InterruptedException {
        LocalDateTime next = reminder.getNextDateTime();
        LocalTime step = reminder.getStep();
        LocalDate date = LocalDate.parse(next.toString().split("T")[0]);
        LocalTime time = LocalTime.parse(next.toString().split("T")[1]);
        String name = reminder.getTaskName();
        int iteration = reminder.getIteration();


        while(next.isBefore(LocalDateTime.now())) {
            Process process = Runtime.getRuntime().exec("notify-send Tâche : " + reminder.getTaskName() + "\nRappel manqué le " + date.toString() + " à " + time.getHour() + "h" + time.getMinute() + "min");
            process.waitFor();

            next = next.plusHours(step.getHour()).plusMinutes(step.getMinute());
            iteration++;
            Reminder.refresh(iteration, next , name);
        }
    }

    private void showNotif(Reminder reminder) throws IOException, InterruptedException {
        LocalDateTime next = reminder.getNextDateTime();
        LocalTime step = reminder.getStep();
        LocalDate date = LocalDate.parse(next.toString().split("T")[0]);
        LocalTime time = LocalTime.parse(next.toString().split("T")[1]);
        String name = reminder.getTaskName();
        int iteration = reminder.getIteration();

        Process process = Runtime.getRuntime().exec("notify-send Tâche : " + reminder.getTaskName() + "\nRappel le " + date.toString() + " à " + time.getHour() + "h" + time.getMinute() + "min");
        process.waitFor();

        next = next.plusHours(step.getHour()).plusMinutes(step.getMinute());
        iteration++;
        Reminder.refresh(iteration, next , name);
    }

    public static void main(String[] args){ launch(args); }

}
