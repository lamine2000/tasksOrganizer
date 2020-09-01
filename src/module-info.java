module tasksOrganizer {
    requires transitive javafx.controls;
    requires java.sql;
    requires javafx.fxml;
    requires javafx.swt;
    requires transitive javafx.base;
    requires transitive javafx.graphics;
    requires transitive com.gluonhq.charm.glisten;
    requires wisp;
    opens com.tasksOrganizer.gui to javafx.fxml, javafx.base, javafx.graphics;
    opens com.tasksOrganizer.tray.notification to javafx.fxml;
}