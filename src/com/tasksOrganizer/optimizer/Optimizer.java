package com.tasksOrganizer.optimizer;

import com.tasksOrganizer.sample.Task;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Optimizer {

    public Optimizer(){}


    private Double temporalDifficultyValue(Task task){
        long tsupp = ChronoUnit.DAYS.between(now(), task.getTsupp());
        short difficulte = (short)(task.getDifficulte());
        long echeance = ChronoUnit.DAYS.between(now(), task.getEcheance());
        double marge = Double.parseDouble(String.valueOf(echeance)) / (Double.parseDouble(String.valueOf(tsupp))-1);

        if(marge != 0)
            return (0.2*difficulte + 1) / marge;

        return (0.2*difficulte + 1) / 0.1;
    }







    private Double temporalImportanceValue(Task task){
        long tsupp = ChronoUnit.DAYS.between(now(), task.getTsupp());
        short importance = (short)(task.getImportance());
        long echeance = ChronoUnit.DAYS.between(now(), task.getEcheance());
        double marge = Double.parseDouble(String.valueOf(echeance)) / (Double.parseDouble(String.valueOf(tsupp))-1);

        if(marge != 0)
            return Double.parseDouble(String.valueOf(importance)) / marge;

        return Double.parseDouble(String.valueOf(importance)) / 0.1;
    }






    private LocalDate now(){ return LocalDate.now(); }







    private void naiveOptimizeList(Task[] tasks){
        Double[] s = new Double[tasks.length];

        for (int i = 0; i < tasks.length; i++)
            s[i] = temporalDifficultyValue(tasks[i]) + temporalImportanceValue(tasks[i]);

        rMapBubbleSort(s, tasks);
    }







    public void optimizeList(Task[] tasks){
        naiveOptimizeList(tasks);
    }







    private void rMapBubbleSort(Double[] arr, Task[] tasks)
    {
        //reverse bubble sort
        Double temp;
        Task ech;
        int n = arr.length;
        for (int i = 0; i < n-1; i++)
            for (int j = 0; j < n-i-1; j++)
                if (arr[j] < arr[j+1])
                {
                    temp = arr[j];
                    ech = tasks[j];

                    arr[j] = arr[j+1];
                    tasks[j] = tasks[j+1];

                    arr[j+1] = temp;
                    tasks[j+1] = ech;
                }
    }
}
