package com.tasksOrganizer.gui;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;

public class MotherController {
    protected static String taskName;
    protected static boolean infoOpened = false;
    protected static boolean modifOpened = false;
    protected static ObservableList<HomeTableViewModel> list;
    protected static TableView<HomeTableViewModel> table;
    protected static Text homeTitle;
}
