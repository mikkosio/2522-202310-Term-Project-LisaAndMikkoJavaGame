package ca.bcit.comp2522.termproject.lisaandmikkojavagame;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * Monster.
 *
 * @author Mikko Sio
 * @author Lisa Jung
 * @version April 6, 2023
 */
public class Monster {
    // Image of the monster.
    private final ImageView monsterImage;
    // Type of the monster.
    private final MonsterType type;
    // Speed of the monster.
    private double monsterSpeed;
    // Boolean for monster's death status.
    private boolean isDead;

    /**
     * Constructor for Monster class.
     * @param monsterImage Image of the monster.
     * @param type Type of the monster.
     * @param monsterSpeed Speed of the monster.
     */
    public Monster(final ImageView monsterImage, final MonsterType type, final double monsterSpeed) {
        this.monsterImage = monsterImage;
        this.type = type;
        this.monsterSpeed = monsterSpeed;
        this.isDead = false;
    }

    /**
     * Get the bounds of the monster.
     * @return Monster's bounds.
     */
    public Bounds getMonsterBox() {
        if (!isDead) {
            return monsterImage.getBoundsInParent();
        } else {
            return new BoundingBox(0, 0, 0, 0);
        }
    }

    /**
     * Getter for monster's image.
     * @return Monster's image.
     */
    public ImageView getMonsterImage() {
        return monsterImage;
    }

    /**
     * Getter for monster's speed.
     * @return Monster's speed.
     */
    public double getMonsterSpeed() {
        return monsterSpeed;
    }

    /**
     * Remove monster from the scene.
     * @param pane Scene of the game.
     */
    public void removeFromScene(final Pane pane) {
        pane.getChildren().remove(monsterImage);
        isDead = true;
    }

    /**
     * Damage the player's health according to monster type.
     * @param health Health of the player.
     */
    public void doesDamage(final PlayerHealth health) {
        switch (type) {
            case MONSTER1 -> {
                health.setHealth(health.getHealth() - 10);
                health.updateHealthBar(health.getHealth());
            }
            case MONSTER2 -> {
                health.setHealth(health.getHealth() - 25);
                health.updateHealthBar(health.getHealth());
            }
            case MONSTER3 -> {
                health.setHealth(health.getHealth() - 50);
                health.updateHealthBar(health.getHealth());
            }
        }
    }

}
