package ca.bcit.comp2522.termproject.lisaandmikkojavagame;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

public class Camera {
    @FXML
    private AnchorPane scene;
    private Rectangle playerBox;
    private double cameraWidth;
    private double cameraHeight;
    private int levelWidth;
    private int levelHeight;

    public Camera(AnchorPane scene, Rectangle playerBox, int levelWidth, int levelHeight) {
        this.scene = scene;
        this.playerBox = playerBox;
        this.cameraWidth = scene.getPrefWidth();
        this.cameraHeight = -scene.getPrefHeight();
        this.levelWidth = levelWidth;
        this.levelHeight = levelHeight;
        setupCamera();
    }

    private void setupCamera() {
        // Move camera horizontally.
        playerBox.translateXProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() > cameraWidth / 2 && newValue.intValue() < levelWidth - cameraWidth / 2) {
                scene.setLayoutX(-(playerBox.getTranslateX() - cameraWidth / 2));
            }
        });

        // Move camera vertically.
        playerBox.translateYProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() > cameraHeight / 2 && newValue.intValue() < levelHeight - cameraHeight / 2) {
                scene.setLayoutY(-(playerBox.getTranslateY() + cameraHeight / 2));
            }
        });
    }
}