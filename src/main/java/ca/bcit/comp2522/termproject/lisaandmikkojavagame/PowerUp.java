package ca.bcit.comp2522.termproject.lisaandmikkojavagame;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class PowerUp {
    private int duration = 10;
    private double sizeIncrease = 2;

    private ImageView powerUpImage;
    private boolean powerUpIsGone = false;

    private boolean poweredUp = false;

    public PowerUp(ImageView powerUpImage) {
        this.powerUpImage = powerUpImage;
    }

    public ImageView getPowerUpImage() {
        return powerUpImage;
    }

    public Bounds getPowerUpBox() {
        if (!powerUpIsGone) {
            return powerUpImage.getBoundsInParent();
        } else {
            return new BoundingBox(0, 0, 0, 0);
        }
    }

    public void removePowerUpFromScene(Pane pane) {
        pane.getChildren().remove(powerUpImage);
        powerUpIsGone = true;
        poweredUp = true;
    }

    public void powerUp(ImageView player) {
        player.setScaleX(player.getScaleX() * 1.2);
        player.setScaleY(player.getScaleY() * 1.2);


        // Start timer to reset power up effect after duration
        AnimationTimer powerUpTimer = new AnimationTimer() {
            private long startTime = System.currentTimeMillis();

            @Override
            public void handle(long l) {
                long elapsedSeconds = (System.currentTimeMillis() - startTime) / 1000;
                if (elapsedSeconds >= duration) {
                    // Reset power up effect
                    player.setScaleX(player.getScaleX() / 1.2);
                    player.setScaleY(player.getScaleY() / 1.2);
                    stop();
                    poweredUp = false;
                }
            }
        };
        powerUpTimer.start();
    }
}
