module tasksOrganizer {
    requires transitive javafx.controls;
    requires java.sql;
    requires javafx.fxml;
    requires transitive javafx.base;
    requires transitive javafx.graphics;
    requires com.gluonhq.charm.glisten;
    requires TrayNotification;
    opens com.tasksOrganizer.gui to javafx.fxml, javafx.base, javafx.graphics;
}