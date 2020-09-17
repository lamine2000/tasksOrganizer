module tasksOrganizer {
    requires transitive javafx.controls;
    requires java.sql;
    requires javafx.fxml;
    requires javafx.swt;
    requires transitive javafx.base;
    requires transitive javafx.graphics;
    requires transitive com.gluonhq.charm.glisten;
    requires wisp;

    opens com.tasksOrganizer.gui.controllers to javafx.fxml;
    opens com.tasksOrganizer.tray.notification to javafx.fxml;
    opens com.tasksOrganizer.gui.models to javafx.base;

    exports com.tasksOrganizer.gui.main to javafx.graphics;

}