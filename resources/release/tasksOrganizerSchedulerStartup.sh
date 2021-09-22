#!/bin/bash

desktopFile="$HOME"/.config/autostart/tasksOrganizerScheduler.desktop

jarPath=/opt/tasksOrganizer/tasksOrganizer.jar

touch "$desktopFile"

desktop-file-edit --set-key=Type --set-value=Application --set-key=Exec --set-value="java16 -jar $jarPath hide" --set-key=Hidden --set-value=false --set-key=NoDisplay --set-value=false --set-key=Name --set-value="tasksOrganizer.jar scheduler startUp" --set-key=X-GNOME-Autostart-enabled --set-value=true "$desktopFile"
