package ca.bcit.comp2522.termproject.lisaandmikkojavagame;

import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;

public class Monster {
    private ImageView monsterImage;

    private MonsterType type;

    private boolean isShot = false;

    private PlayerHealth health;

    public Monster(ImageView monsterImage) {
        this.monsterImage = monsterImage;
    }

    public Bounds getMonsterBox() {
        return monsterImage.getBoundsInParent();
    }
    public ImageView getMonsterImage() {
        return monsterImage;
    }

    public void doesDamage(PlayerHealth health) {
        switch (type) {
            case MONSTER1 -> {
                health.setHealth(health.getHealth() - 10);
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
