package com.tasksOrganizer.gui.main;

public class Main {
    public static void main(String[] args) {
        if(args.length != 0 && args[0].equals("hide"))
            MainHidden.main(args);
        else
            MainShown.main(args);
    }
}
