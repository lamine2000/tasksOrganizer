package com.tasksOrganizer.tray.animations;

import com.tasksOrganizer.tray.models.CustomStage;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class FadeAnimation implements TrayAnimation {
    private final Timeline showAnimation;
    private final Timeline dismissAnimation;
    private final SequentialTransition sq;
    private final CustomStage stage;
    private boolean trayIsShowing;

    public FadeAnimation(CustomStage customStage) {
        this.stage = customStage;
        this.showAnimation = this.setupShowAnimation();
        this.dismissAnimation = this.setupDismissAnimation();
        this.sq = new SequentialTransition(this.setupShowAnimation(), this.setupDismissAnimation());
    }

    private Timeline setupShowAnimation() {
        Timeline tl = new Timeline();
        KeyValue kvOpacity = new KeyValue(this.stage.opacityProperty(), 0.0D);
        KeyFrame frame1 = new KeyFrame(Duration.ZERO, kvOpacity);
        KeyValue kvOpacity2 = new KeyValue(this.stage.opacityProperty(), 1.0D);
        KeyFrame frame2 = new KeyFrame(Duration.millis(3000.0D), kvOpacity2);
        tl.getKeyFrames().addAll(frame1, frame2);
        tl.setOnFinished((e) -> {
            this.trayIsShowing = true;
        });
        return tl;
    }

    private Timeline setupDismissAnimation() {
        Timeline tl = new Timeline();
        KeyValue kv1 = new KeyValue(this.stage.opacityProperty(), 0.0D);
        KeyFrame kf1 = new KeyFrame(Duration.millis(2000.0D), kv1);
        tl.getKeyFrames().addAll(kf1);
        tl.setOnFinished((e) -> {
            this.trayIsShowing = false;
            this.stage.close();
            this.stage.setLocation(this.stage.getBottomRight());
        });
        return tl;
    }

    public AnimationType getAnimationType() {
        return AnimationType.FADE;
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
