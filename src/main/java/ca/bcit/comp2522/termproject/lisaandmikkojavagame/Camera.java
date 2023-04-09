package ca.bcit.comp2522.termproject.lisaandmikkojavagame;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

/**
 * Updating camera view of the player.
 *
 * @author Mikko Sio
 * @author Lisa Jung
 * @version April 6, 2023
 */
public class Camera {
    // Scene for game.
    @FXML
    private AnchorPane scene;
    // Health bar container.
    @FXML
    private VBox healthBarVBox;
    // Player's collision box.
    private Rectangle playerBox;
    // How wide the player can see.
    private double cameraWidth;
    // How high the player can see.
    private double cameraHeight;
    // How wide the level is.
    private int levelWidth;
    // How high the level is.
    private int levelHeight;

    /**
     * Constructor for the camera.
     * @param scene Game scene.
     * @param playerBox Player's bounding box.
     * @param levelWidth Width of the game level.
     * @param levelHeight Height of the game level.
     * @param healthBarVBox Player's health bar.
     */
    public Camera(final AnchorPane scene, final Rectangle playerBox, final int levelWidth, final int levelHeight,
                  final VBox healthBarVBox) {
        this.scene = scene;
        this.playerBox = playerBox;
        this.cameraWidth = scene.getPrefWidth();
        this.cameraHeight = scene.getPrefHeight();
        this.levelWidth = levelWidth;
        // offset of 800
        this.levelHeight = -levelHeight + 800;
        this.healthBarVBox = healthBarVBox;
        setupCamera();
    }

    /**
     * Updates camera according to player's movement.
     */
    private void setupCamera() {
        // Move camera horizontally.
        playerBox.translateXProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() > cameraWidth / 2 && newValue.intValue() < levelWidth - cameraWidth / 2) {
                scene.setLayoutX(-(playerBox.getTranslateX() - cameraWidth / 2));
                healthBarVBox.setTranslateX((playerBox.getTranslateX() - cameraWidth / 2));
            }
        });

        // Move camera vertically.
        playerBox.translateYProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() < cameraHeight / 2 && newValue.intValue() > levelHeight + cameraHeight / 2) {
                scene.setLayoutY(-(playerBox.getTranslateY() - cameraHeight / 2));
                healthBarVBox.setTranslateY(playerBox.getTranslateY() - cameraHeight / 2);
            }
        });
    }
}
