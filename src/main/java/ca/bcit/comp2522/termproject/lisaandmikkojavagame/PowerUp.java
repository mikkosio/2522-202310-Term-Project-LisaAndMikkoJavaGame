package ca.bcit.comp2522.termproject.lisaandmikkojavagame;

import javafx.animation.AnimationTimer;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

/**
 * PowerUp.
 *
 * @author Mikko Sio
 * @author Lisa Jung
 * @version April 6, 2023
 */
public class PowerUp {
    // If player is powered up.
    static boolean poweredUp = false;
    // Duration of power-up.
    private int duration = 5;
    // Image of power-up.
    private ImageView powerUpImage;
    // If player got the power-up.
    private boolean powerUpIsGone = false;

    /**
     * Constructor for PowerUp class.
     * @param powerUpImage Image of power-up.
     */
    public PowerUp(final ImageView powerUpImage) {
        this.powerUpImage = powerUpImage;
    }

    /**
     * Getter for power-up's image.
     * @return Power-up's image.
     */
    public ImageView getPowerUpImage() {
        return powerUpImage;
    }

    /**
     * Getter for power-up's bounds.
     * @return Power-up's bounds.
     */
    public Bounds getPowerUpBox() {
        if (!powerUpIsGone) {
            return powerUpImage.getBoundsInParent();
        } else {
            return new BoundingBox(0, 0, 0, 0);
        }
    }

    /**
     * Remove power-up from scene.
     * @param pane Scene of the game.
     */
    public void removePowerUpFromScene(final Pane pane) {
        pane.getChildren().remove(powerUpImage);
        powerUpIsGone = true;
        poweredUp = true;
    }

    /**
     * Power up the player.
     * @param player Image of the player.
     * @param playerBox Collision box of the player.
     */
    public void powerUp(final ImageView player, final Rectangle playerBox) {
        playerBox.setTranslateY(playerBox.getTranslateY() - 20);
        player.setScaleX(player.getScaleX() * 1.2);
        player.setScaleY(player.getScaleY() * 1.2);
        playerBox.setWidth(Controller.PLAYER_WIDTH*1.1);
        playerBox.setHeight(Controller.PLAYER_HEIGHT*1.1);

        poweredUp = true;
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
                    playerBox.setWidth(Controller.PLAYER_WIDTH);
                    playerBox.setHeight(Controller.PLAYER_HEIGHT);
                    poweredUp = false;

                    stop();
                }
            }
        };
        powerUpTimer.start();
    }
}
