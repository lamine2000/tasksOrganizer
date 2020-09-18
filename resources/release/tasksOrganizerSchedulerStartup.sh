#!/bin/bash

desktopFile=tasksOrganizerScheduler.desktop

#jarPath doit contenir l'emplacement de tasksOrganizer.jar
jarPath=$HOME/IdeaProjects/tasksOrganizer/out/artifacts/tasksOrganizer.jar

touch "$HOME"/.config/autostart/$desktopFile

desktop-file-edit --set-key=Type --set-value=Application --set-key=Exec --set-value="java -jar $jarPath hide" --set-key=Hidden --set-value=false --set-key=NoDisplay --set-value=false --set-key=Name --set-value="tasksOrganizer.jar scheduler startUp" --set-key=X-GNOME-Autostart-enabled --set-value=true $desktopFile
