package ca.bcit.comp2522.termproject.lisaandmikkojavagame;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ProgressBar;
import java.util.Optional;

/**
 * Health of the player.
 *
 * @author Mikko Sio
 * @author Lisa Jung
 * @version April 6, 2023
 */
public class PlayerHealth {
    // Amount of health player has.
    private int health;
    // Progress bar for health.
    private final ProgressBar healthBar;
    private boolean restartGame;

    /**
     * Constructor for PlayerHealth class.
     * @param healthBar Progress bar of health.
     */
    public PlayerHealth(ProgressBar healthBar) {
        this.healthBar = healthBar;
        health = 100;
        restartGame = false;
        healthBar.setPrefWidth(200);
    }

    /**
     * Getter for player's health.
     * @return Amount of player health.
     */
    public int getHealth() {
        return health;
    }

    /**
     * Setter for player's health.
     * @param health Amount of health to set to.
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Update the health bar.
     * @param healthValue Amount of health to update to.
     */
    public void updateHealthBar(final int healthValue) {
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

    /**
     * Check if player needs a restart.
     * @return True if player needs reset.
     */
    public boolean needsRestart() {
        return restartGame;
    }
}

