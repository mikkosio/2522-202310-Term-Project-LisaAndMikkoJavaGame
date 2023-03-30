package ca.bcit.comp2522.termproject.lisaandmikkojavagame;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;

public class PlayerHealth {

    private int health = 100;

    ProgressBar healthBar;


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


    public Node getHealthBar() {
        VBox container = new VBox();
        container.getChildren().add(healthBar);
        return container;
    }

    public void updateHealthBar(int healthValue) {
        double progress = (double) healthValue / 100.0;
        System.out.println("Updating health bar to: " + progress);
        healthBar.setProgress(progress);
    }

}
