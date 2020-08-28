package com.tasksOrganizer.tray.notification;

import com.tasksOrganizer.tray.animations.*;
import com.tasksOrganizer.tray.models.CustomStage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;

public final class TrayNotification {
    @FXML
    private Label lblTitle;
    @FXML
    private Label lblMessage;
    @FXML
    private Label lblClose;
    @FXML
    private ImageView imageIcon;
    @FXML
    private Rectangle rectangleColor;
    @FXML
    private AnchorPane rootNode;
    private CustomStage stage;
    private NotificationType notificationType;
    private AnimationType animationType;
    private EventHandler<ActionEvent> onDismissedCallBack;
    private EventHandler<ActionEvent> onShownCallback;
    private TrayAnimation animator;
    private AnimationProvider animationProvider;

    public TrayNotification(String title, String body, Image img, Paint rectangleFill) {
        this.initTrayNotification(title, body, NotificationType.CUSTOM);
        this.setImage(img);
        this.setRectangleFill(rectangleFill);
    }

    public TrayNotification(String title, String body, NotificationType notificationType) {
        this.initTrayNotification(title, body, notificationType);
    }

    public TrayNotification() {
        this.initTrayNotification("", "", NotificationType.CUSTOM);
    }

    private void initTrayNotification(String title, String message, NotificationType type) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/TrayNotification.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.load();
            this.initStage();
            this.initAnimations();
            this.setTray(title, message, type);
        } catch (IOException var5) {
            var5.printStackTrace();
        }

    }

    private void initAnimations() {
        this.animationProvider = new AnimationProvider(new FadeAnimation(this.stage), new SlideAnimation(this.stage), new PopupAnimation(this.stage));
        this.setAnimationType(AnimationType.SLIDE);
    }

    private void initStage() {
        this.stage = new CustomStage(this.rootNode, StageStyle.UNDECORATED);
        this.stage.setScene(new Scene(this.rootNode));
        this.stage.setAlwaysOnTop(true);
        this.stage.setLocation(this.stage.getBottomRight());
        this.lblClose.setOnMouseClicked((e) -> this.dismiss());
    }

    public NotificationType getNotificationType() {
        return this.notificationType;
    }

    public void setNotificationType(NotificationType nType) {
        this.notificationType = nType;
        URL imageLocation = null;
        String paintHex = null;
        switch (nType) {
            case INFORMATION:
                imageLocation = this.getClass().getResource("/info.png");
                paintHex = "#2C54AB";
                break;
            case NOTICE:
                imageLocation = this.getClass().getResource("/notice.png");
                paintHex = "#8D9695";
                break;
            case SUCCESS:
                imageLocation = this.getClass().getResource("/success.png");
                paintHex = "#009961";
                break;
            case WARNING:
                imageLocation = this.getClass().getResource("/warning.png");
                paintHex = "#E23E0A";
                break;
            case ERROR:
                imageLocation = this.getClass().getResource("/error.png");
                paintHex = "#CC0033";
                break;
            case CUSTOM:
                return;
        }

        this.setRectangleFill(Paint.valueOf(paintHex));
        this.setImage(new Image(imageLocation.toString()));
        this.setTrayIcon(this.imageIcon.getImage());
    }

    public void setTray(String title, String message, NotificationType type) {
        this.setTitle(title);
        this.setMessage(message);
        this.setNotificationType(type);
    }

    public void setTray(String title, String message, Image img, Paint rectangleFill, AnimationType animType) {
        this.setTitle(title);
        this.setMessage(message);
        this.setImage(img);
        this.setRectangleFill(rectangleFill);
        this.setAnimationType(animType);
    }

    public boolean isTrayShowing() {
        return this.animator.isShowing();
    }

    public void showAndDismiss(Duration dismissDelay) {
        if (this.isTrayShowing()) {
            this.dismiss();
        } else {
            this.stage.show();
            this.onShown();
            this.animator.playSequential(dismissDelay);
        }

        this.onDismissed();
    }

    public void showAndWait() {
        if (!this.isTrayShowing()) {
            this.stage.show();
            this.animator.playShowAnimation();
            this.onShown();
        }

    }

    public void dismiss() {
        if (this.isTrayShowing()) {
            this.animator.playDismissAnimation();
            this.onDismissed();
        }

    }

    private void onShown() {
        if (this.onShownCallback != null) {
            this.onShownCallback.handle(new ActionEvent());
        }

    }

    private void onDismissed() {
        if (this.onDismissedCallBack != null) {
            this.onDismissedCallBack.handle(new ActionEvent());
        }

    }

    public void setOnDismiss(EventHandler<ActionEvent> event) {
        this.onDismissedCallBack = event;
    }

    public void setOnShown(EventHandler<ActionEvent> event) {
        this.onShownCallback = event;
    }

    public Image getTrayIcon() {
        return this.stage.getIcons().get(0);
    }

    public void setTrayIcon(Image img) {
        this.stage.getIcons().clear();
        this.stage.getIcons().add(img);
    }

    public String getTitle() {
        return this.lblTitle.getText();
    }

    public void setTitle(String txt) {
        this.lblTitle.setText(txt);
    }

    public String getMessage() {
        return this.lblMessage.getText();
    }

    public void setMessage(String txt) {
        this.lblMessage.setText(txt);
    }

    public Image getImage() {
        return this.imageIcon.getImage();
    }

    public void setImage(Image img) {
        this.imageIcon.setImage(img);
        this.setTrayIcon(img);
    }

    public Paint getRectangleFill() {
        return this.rectangleColor.getFill();
    }

    public void setRectangleFill(Paint value) {
        this.rectangleColor.setFill(value);
    }

    public AnimationType getAnimationType() {
        return this.animationType;
    }

    public void setAnimationType(AnimationType type) {
        this.animator = this.animationProvider.findFirstWhere((a) -> a.getAnimationType() == type);
        this.animationType = type;
    }
}
