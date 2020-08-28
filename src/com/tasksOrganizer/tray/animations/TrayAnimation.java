package com.tasksOrganizer.tray.animations;

import javafx.util.Duration;

public interface TrayAnimation {
    AnimationType getAnimationType();

    void playSequential(Duration var1);

    void playShowAnimation();

    void playDismissAnimation();

    boolean isShowing();
}
