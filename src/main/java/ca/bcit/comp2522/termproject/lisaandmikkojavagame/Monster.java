package ca.bcit.comp2522.termproject.lisaandmikkojavagame;

import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;

public class Monster {
    private ImageView monsterImage;

    private MonsterType type;

    private boolean isShot = false;

    private double monsterSpeed;

    public Monster(ImageView monsterImage, MonsterType type, double monsterSpeed) {
        this.monsterImage = monsterImage;
        this.type = type;
        this.monsterSpeed = monsterSpeed;
    }

    public Bounds getMonsterBox() {
        return monsterImage.getBoundsInParent();
    }
    public ImageView getMonsterImage() {
        return monsterImage;
    }

    public double getMonsterSpeed() {
        return monsterSpeed;
    }

    public void doesDamage(PlayerHealth health) {
        switch (type) {
            case MONSTER1 -> {
                health.setHealth(health.getHealth() - 10);
                health.updateHealthBar(health.getHealth());


            }
            case MONSTER2 -> {
                health.setHealth(health.getHealth() - 25);
            }
            case MONSTER3 -> {
                health.setHealth(health.getHealth() - 50);
            }
        }
    }

}
