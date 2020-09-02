package com.tasksOrganizer.gui;

import com.coreoz.wisp.Scheduler;
import com.coreoz.wisp.schedule.Schedules;
import javafx.application.Application;
import javafx.stage.Stage;

import java.time.Duration;


public class MainHidden extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.show();
        primaryStage.close();
        Scheduler scheduler = new Scheduler();

        scheduler.schedule(
                () -> {
                    System.out.println("my first Job");
                    //code à exécuter à chaque minute
                    /*Reminder[] reminders = Reminder.extractReminders();
                    LocalDateTime[] nextDateTimes = new LocalDateTime[reminders.length];

                    for (int i = 0; i < reminders.length; i++){
                        nextDateTimes[i] = reminders[i].getNextDateTime();

                        if(nextDateTimes[i].isBefore(LocalDateTime.now())){
                            //notif ratées
                            try {
                                showMissedNotif(reminders[i]);
                            } catch (IOException | InterruptedException e) {
                                //e.printStackTrace();
                            }
                        }
                    }*/
                }
                ,Schedules.fixedDelaySchedule(Duration.ofSeconds(1)) // the schedule associated to the runnable
        );

    }

    public static void main(String[] args){ launch(args); }

    /*private void showMissedNotif(Reminder reminder) throws IOException, InterruptedException {
        System.out.println("begin");
        Process process = Runtime.getRuntime().exec("touch ~/Bureau/coucou \n");
        process.waitFor();
        System.out.println("end");
    }*/

}
