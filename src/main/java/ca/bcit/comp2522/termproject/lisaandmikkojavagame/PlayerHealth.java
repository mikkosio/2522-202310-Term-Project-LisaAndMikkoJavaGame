package ca.bcit.comp2522.termproject.lisaandmikkojavagame;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;

import java.util.Optional;

public class PlayerHealth {

    private int health = 100;
    private ProgressBar healthBar;
    private boolean restartGame = false;

    // Player health bar
    public PlayerHealth(ProgressBar healthBar) {
        this.healthBar = healthBar;
        healthBar.setPrefWidth(200);
    }

    public int getHealth() {
        return health;
    }
    public void setHealth(int health) {
        this.health = health;
    }

    // Update health bar
    public void updateHealthBar(int healthValue) {
        if (healthValue > 0) {
            double progress = (double) healthValue / 100.0;
            System.out.println("Updating health bar to: " + progress);
            healthBar.setProgress(progress);
        } else {
            healthBar.setProgress(0.0);
            showGameOverPopup();
        }
    }

    // Show game-over screen
    private void showGameOverPopup() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Game Over");
            alert.setHeaderText("Your player died :(");
            alert.setContentText("Do you want to restart the game?");
            // Press restart game
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                restartGame = true;
            } else {
                // Close game
                System.exit(0);
            }
        });
    }
    public boolean needsRestart() {
        return restartGame;
    }
}

