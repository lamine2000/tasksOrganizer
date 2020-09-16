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
        double marge = Double.parseDouble(String.valueOf(echeance)) / (Double.parseDouble(String.valueOf(tsupp))) - 1;

        marge = marge!=0 ? marge : 0.1d;

        return tsupp * (0.2*difficulte + 1) / marge;
    }


    private Double temporalImportanceValue(Task task){
        long tsupp = ChronoUnit.DAYS.between(now(), task.getTsupp());
        short importance = (short)(task.getImportance());
        long echeance = ChronoUnit.DAYS.between(now(), task.getEcheance());
        double marge = Double.parseDouble(String.valueOf(echeance)) / (Double.parseDouble(String.valueOf(tsupp))) - 1;

        marge = marge!=0 ? marge : 0.1d;

        return Double.parseDouble(String.valueOf(importance)) / marge;
    }


    private Double temporalImportanceDifficultyCompromiseValue(Task task){
        return temporalDifficultyValue(task)+temporalImportanceValue(task);
    }


    private LocalDate now(){ return LocalDate.now(); }


    private ArrayList<Task> insert(ArrayList<Task> subTable, Task newOne, int index) throws CloneNotSupportedException {

        ArrayList<Task> copy = new ArrayList<>(subTable);

        if(subTable.size() != 0) {
            for (int i = 0; i < subTable.size(); i++)
                copy.set(i, subTable.get(i).clone());

            if (index < copy.size())
                copy.add(index, newOne);
        }
        return copy;
    }


    private Double sigmaUnrealisableTasksValue(ArrayList<Task> set){
        //renvoie la somme de Ci des taches qui ne pourront pas etre

        long daysBefore = 0;
        int size;
        StringBuilder strIndexes = new StringBuilder();
        Task[] unrealisableTasks;
        String[] indexes;
        double sum = 0d;
        boolean unDoable;

        for (int i = 1; i < set.size(); i++) {
            /*
            parcourir la liste de tâches et identifier celles non faisables
            (en faisant la somme des temps supposés des tâches qui la précedent)
            */
            for (int j = 0; j < i; j++)
                if (now().isBefore(set.get(i).getTsupp()))
                    daysBefore += ChronoUnit.DAYS.between(now(), set.get(j).getTsupp());


            unDoable = daysBefore >
                    ChronoUnit.DAYS.between(now(), set.get(i).getEcheance())
                    -
                    ChronoUnit.DAYS.between(now(), set.get(i).getTsupp());

            //extraire les indices des taches non-faisables
            if (unDoable)
                strIndexes.append(i).append("/");
        }
        //calcul du nombre de taches non faisables
        size = strIndexes.toString().length() / 2;

        unrealisableTasks = new Task[size];
        indexes = strIndexes.toString().split("/");

        for (int i = 0; i < unrealisableTasks.length; i++)
            unrealisableTasks[i] = set.get(Integer.parseInt(indexes[i]));

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
        if(tasks.length > 1) {
            naiveOptimizeList(tasks);
            ArrayList<Task> subTable = new ArrayList<>(), set, bestSet = new ArrayList<>();
            double sigma, minSigma = 1000000d;

            subTable.add(tasks[0]);

            for (int i = 1; i < tasks.length; i++) {
                for (int j = i; j >= 0 ; j--) {

                    if(i != j)
                        set = insert(subTable, tasks[i], j);
                    else{
                        subTable.add(tasks[i]);
                        set = subTable;
                    }

                    sigma = sigmaUnrealisableTasksValue(set);

                    if (sigma < minSigma) {
                        bestSet = set;
                        minSigma = sigma;
                    }
                }
                subTable = bestSet;
                minSigma = 1000000d;
            }

            for (int i = 0; i < tasks.length; i++)
                tasks[i] = subTable.get(i);
        }
    }
}