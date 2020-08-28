package com.tasksOrganizer.tray.animations;

import com.tasksOrganizer.tray.models.CustomStage;
import javafx.animation.*;
import javafx.util.Duration;

public class SlideAnimation implements TrayAnimation {
    private final Timeline showAnimation;
    private final Timeline dismissAnimation;
    private final SequentialTransition sq;
    private final CustomStage stage;
    private boolean trayIsShowing;

    public SlideAnimation(CustomStage customStage) {
        this.stage = customStage;
        this.showAnimation = this.setupShowAnimation();
        this.dismissAnimation = this.setupDismissAnimation();
        this.sq = new SequentialTransition(this.setupShowAnimation(), this.setupDismissAnimation());
    }

    private Timeline setupShowAnimation() {
        Timeline tl = new Timeline();
        double offScreenX = this.stage.getOffScreenBounds().getX();
        KeyValue kvX = new KeyValue(this.stage.xLocationProperty(), offScreenX);
        KeyFrame frame1 = new KeyFrame(Duration.ZERO, kvX);
        Interpolator interpolator = Interpolator.TANGENT(Duration.millis(300.0D), 50.0D);
        KeyValue kvInter = new KeyValue(this.stage.xLocationProperty(), this.stage.getBottomRight().getX(), interpolator);
        KeyFrame frame2 = new KeyFrame(Duration.millis(1300.0D), kvInter);
        KeyValue kvOpacity = new KeyValue(this.stage.opacityProperty(), 0.0D);
        KeyFrame frame3 = new KeyFrame(Duration.ZERO, kvOpacity);
        KeyValue kvOpacity2 = new KeyValue(this.stage.opacityProperty(), 1.0D);
        KeyFrame frame4 = new KeyFrame(Duration.millis(1000.0D), kvOpacity2);
        tl.getKeyFrames().addAll(frame1, frame2, frame3, frame4);
        tl.setOnFinished((e) -> this.trayIsShowing = true);
        return tl;
    }

    private Timeline setupDismissAnimation() {
        Timeline tl = new Timeline();
        double offScreenX = this.stage.getOffScreenBounds().getX();
        Interpolator interpolator = Interpolator.TANGENT(Duration.millis(300.0D), 50.0D);
        double trayPadding = 3.0D;
        KeyValue kvX = new KeyValue(this.stage.xLocationProperty(), offScreenX + trayPadding, interpolator);
        KeyFrame frame1 = new KeyFrame(Duration.millis(1400.0D), kvX);
        KeyValue kvOpacity = new KeyValue(this.stage.opacityProperty(), 0.4D);
        KeyFrame frame2 = new KeyFrame(Duration.millis(2000.0D), kvOpacity);
        tl.getKeyFrames().addAll(frame1, frame2);
        tl.setOnFinished((e) -> {
            this.trayIsShowing = false;
            this.stage.close();
            this.stage.setLocation(this.stage.getBottomRight());
        });
        return tl;
    }

    public AnimationType getAnimationType() {
        return AnimationType.SLIDE;
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
