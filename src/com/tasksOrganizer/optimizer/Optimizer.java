package com.tasksOrganizer.optimizer;

import com.tasksOrganizer.sample.Task;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Optimizer {

    public Optimizer(){}


    private Double temporalDifficultyValue(Task task){
        long tsupp = ChronoUnit.DAYS.between(now(), task.getTsupp());
        short difficulte = (short)(task.getDifficulte());
        long echeance = ChronoUnit.DAYS.between(now(), task.getEcheance());
        double marge = Double.parseDouble(String.valueOf(echeance)) / (Double.parseDouble(String.valueOf(tsupp)))-1;

        marge = marge!=0 ? marge : 0.1d;

        return (0.2*difficulte + 1) / marge;
    }


    private Double temporalImportanceValue(Task task){
        long tsupp = ChronoUnit.DAYS.between(now(), task.getTsupp());
        short importance = (short)(task.getImportance());
        long echeance = ChronoUnit.DAYS.between(now(), task.getEcheance());
        double marge = Double.parseDouble(String.valueOf(echeance)) / (Double.parseDouble(String.valueOf(tsupp)))-1;

        marge = marge!=0 ? marge : 0.1d;

        return Double.parseDouble(String.valueOf(importance)) / marge;
    }


    private Double temporalImportanceDifficultyCompromiseValue(Task task){
        return temporalDifficultyValue(task)+temporalImportanceValue(task);
    }


    private LocalDate now(){ return LocalDate.now(); }


    private ArrayList<Task> insert(ArrayList<Task> tasks, Task newOne, int index) throws CloneNotSupportedException {

        ArrayList<Task> copy = new ArrayList<>(tasks);

        if(tasks.size() != 0) {
            for (int i = 0; i < tasks.size(); i++)
                copy.set(i, tasks.get(i).clone());

            if (index < copy.size())
                copy.add(index, newOne);
        }
        return copy;
    }


    private Double sigmaUnrealisableTasksValue(ArrayList<Task> tasks){
        //renvoie la somme de Ci des taches qui ne pourront pas etre

        int daysBefore = 0, size;
        StringBuilder strIndexes = new StringBuilder();
        Task[] unrealisableTasks;
        String[] indexes;
        double sum = 0d;

        for (int i = 0; i < tasks.size(); i++) {
            //extraire les indices des taches non-faisables

            for (int j = 0; j < i; j++)
                if (now().isBefore(tasks.get(i).getTsupp()))
                    daysBefore += ChronoUnit.DAYS.between(now(), tasks.get(j).getTsupp());

            if (daysBefore > ChronoUnit.DAYS.between(now(), tasks.get(i).getEcheance()))
                strIndexes.append(i).append("/");
        }
        //calcul du nombre de taches non faisables
        size = strIndexes.toString().length() / 2;

        unrealisableTasks = new Task[size];
        indexes = strIndexes.toString().split("/");

        for (int i = 0; i < unrealisableTasks.length; i++)
            unrealisableTasks[i] = tasks.get(Integer.parseInt(indexes[i]));

        for(Task elt : unrealisableTasks)
            sum += temporalImportanceDifficultyCompromiseValue(elt);

        return Double.parseDouble(String.valueOf(sum));
    }


    private void naiveOptimizeList(Task[] tasks){
        Double[] s = new Double[tasks.length];

        for (int i = 0; i < tasks.length; i++)
            s[i] = temporalDifficultyValue(tasks[i]) + temporalImportanceValue(tasks[i]);

        rMapBubbleSort(s, tasks);
    }


    private void rMapBubbleSort(Double[] arr, Task[] tasks)
    {
        //reverse bubble sort -> and mapping changes to the 'tasks' array
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


    public void optimize(Task[] tasks) throws CloneNotSupportedException {
        naiveOptimizeList(tasks);
        if(tasks.length > 1) {
            ArrayList<Task> subTable = new ArrayList<>();
            ArrayList<Task> set;
            double sigma, minSigma = 1000000d;

            subTable.add(tasks[0]);

            for (int i = 0; i < tasks.length; i++) {
                for (int j = 0; j <= i+1; j++) {
                    if (i + 1 < tasks.length) {
                        set = insert(subTable, tasks[i + 1], j);
                        sigma = sigmaUnrealisableTasksValue(set);

                        if (sigma < minSigma) {
                            subTable = set;
                            minSigma = sigma;
                        }
                    }
                }
                minSigma = 1000000d;
            }

            for (int i = 0; i < tasks.length; i++)
                tasks[i] = subTable.get(i);
        }
    }
}
