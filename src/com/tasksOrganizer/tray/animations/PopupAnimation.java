package com.tasksOrganizer.tray.animations;

import com.tasksOrganizer.tray.models.CustomStage;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class PopupAnimation implements TrayAnimation {
    private final Timeline showAnimation;
    private final Timeline dismissAnimation;
    private final SequentialTransition sq;
    private final CustomStage stage;
    private boolean trayIsShowing;

    public PopupAnimation(CustomStage s) {
        this.stage = s;
        this.showAnimation = this.setupShowAnimation();
        this.dismissAnimation = this.setupDismissAnimation();
        this.sq = new SequentialTransition(this.setupShowAnimation(), this.setupDismissAnimation());
    }

    private Timeline setupDismissAnimation() {
        Timeline tl = new Timeline();
        KeyValue kv1 = new KeyValue(this.stage.yLocationProperty(), this.stage.getY() + this.stage.getWidth());
        KeyFrame kf1 = new KeyFrame(Duration.millis(2000.0D), kv1);
        KeyValue kv2 = new KeyValue(this.stage.opacityProperty(), 0.0D);
        KeyFrame kf2 = new KeyFrame(Duration.millis(2000.0D), kv2);
        tl.getKeyFrames().addAll(kf1, kf2);
        tl.setOnFinished((e) -> {
            this.trayIsShowing = false;
            this.stage.close();
            this.stage.setLocation(this.stage.getBottomRight());
        });
        return tl;
    }

    private Timeline setupShowAnimation() {
        Timeline tl = new Timeline();
        KeyValue kv1 = new KeyValue(this.stage.yLocationProperty(), this.stage.getBottomRight().getY() + this.stage.getWidth());
        KeyFrame kf1 = new KeyFrame(Duration.ZERO, kv1);
        KeyValue kv2 = new KeyValue(this.stage.yLocationProperty(), this.stage.getBottomRight().getY());
        KeyFrame kf2 = new KeyFrame(Duration.millis(1000.0D), kv2);
        KeyValue kv3 = new KeyValue(this.stage.opacityProperty(), 0.0D);
        KeyFrame kf3 = new KeyFrame(Duration.ZERO, kv3);
        KeyValue kv4 = new KeyValue(this.stage.opacityProperty(), 1.0D);
        KeyFrame kf4 = new KeyFrame(Duration.millis(2000.0D), kv4);
        tl.getKeyFrames().addAll(kf1, kf2, kf3, kf4);
        tl.setOnFinished((e) -> this.trayIsShowing = true);
        return tl;
    }

    public AnimationType getAnimationType() {
        return AnimationType.POPUP;
    }

    public void playSequential(Duration dismissDelay) {
        this.sq.getChildren().get(1).setDelay(dismissDelay);
        this.sq.play();
    }

    public void playShowAnimation() {
        this.showAnimation.play();
    }

    public void playDismissAnimation() {
        this.dismissAnimation.play();
    }

    public boolean isShowing() {
        return this.trayIsShowing;
    }
}


