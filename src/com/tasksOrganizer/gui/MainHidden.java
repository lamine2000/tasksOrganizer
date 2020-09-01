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
                () -> System.out.println("My first job"),           // the runnable to be scheduled
                Schedules.fixedDelaySchedule(Duration.ofSeconds(1)) // the schedule associated to the runnable
        );

    }

    public static void main(String[] args){ launch(args); }
}
