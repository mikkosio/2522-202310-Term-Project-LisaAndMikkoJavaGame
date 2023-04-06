package ca.bcit.comp2522.termproject.lisaandmikkojavagame;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class Camera {
    @FXML
    private AnchorPane scene;
    @FXML
    private VBox healthBarVBox;
    private Rectangle playerBox;
    private double cameraWidth;
    private double cameraHeight;
    private int levelWidth;
    private int levelHeight;

    public Camera(AnchorPane scene, Rectangle playerBox, int levelWidth, int levelHeight, VBox healthBarVBox) {
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

    public void resetCamera() {
        scene.setLayoutX(0);
        scene.setLayoutY(0);
        healthBarVBox.setTranslateX(0);
        healthBarVBox.setTranslateY(0);
    }
}
