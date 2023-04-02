package ca.bcit.comp2522.termproject.lisaandmikkojavagame;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Monster {
    private ImageView monsterImage;

    private MonsterType type;

    private boolean isShot = false;

    private double monsterSpeed;

    private boolean isDead;

    public Monster(ImageView monsterImage, MonsterType type, double monsterSpeed) {
        this.monsterImage = monsterImage;
        this.type = type;
        this.monsterSpeed = monsterSpeed;
        this.isDead = false;
    }

    public Bounds getMonsterBox() {
        if (!isDead) {
            return monsterImage.getBoundsInParent();
        } else {
            return new BoundingBox(0, 0, 0, 0);
        }
    }
    public ImageView getMonsterImage() {
        return monsterImage;
    }

    public double getMonsterSpeed() {
        return monsterSpeed;
    }


    public void removeFromScene(Pane pane) {
        pane.getChildren().remove(monsterImage);
        isDead = true;
    }

    public void doesDamage(PlayerHealth health) {
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
