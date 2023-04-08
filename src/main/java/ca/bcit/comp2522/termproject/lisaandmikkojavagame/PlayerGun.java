package ca.bcit.comp2522.termproject.lisaandmikkojavagame;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;

/**
 * Gun of the player.
 *
 * @author Mikko Sio
 * @author Lisa Jung
 * @version April 6, 2023
 */
public class PlayerGun {
    // Image of player.
    @FXML
    private final ImageView playerImage;
    // Anchor Pane scene.
    @FXML
    private final AnchorPane scene;
    // All platforms in current level.
    private final ArrayList<Node> platforms;
    // All monsters in current level.
    private final ArrayList<Monster> monsters;
    // Bullets to be used in list.
    private final ArrayList<Node> bullets;
    // Width of level
    private final int levelWidth;
    // Speed of bullet.
    private int bulletSpeed;
    // Cooldown to shoot.
    private int cooldown;
    // Max cooldown.
    private int maxCooldown;

    /**
     * Constructor for PlayerGun class.
     * @param scene Scene of the game.
     * @param playerImage Image of the player.
     * @param level Current level.
     */
    public PlayerGun(final AnchorPane scene, final ImageView playerImage, final Level level) {
        this.playerImage = playerImage;
        this.scene = scene;
        this.platforms = level.getPlatforms();
        this.monsters = level.getMonsters();
        this.levelWidth = level.getLevelWidth();
        bulletSpeed = 7;
        cooldown = 0;
        maxCooldown = 50;
        bullets = new ArrayList<>();
        // Initialize all bullets to arraylist and add to scene.
        makeBullets();
        // Listen for mouse clicks.
        gunSetup();
        // Start animation to move bullets.
        shootAnimation.start();
        // Start cooldown timer.
        cooldownTimer.start();
    }

    /**
     * Setter for gun's cooldown.
     * @param cooldown Shooting cooldown.
     */
    public void setCooldown(final int cooldown) {
        maxCooldown = cooldown;
    }

    /**
     * Make the bullets of the gun.
     */
    private void makeBullets() {
        for (int i = 0; i < 10; i++) {
            Rectangle newBullet = new Rectangle(13, 7, Color.RED);
            bullets.add(newBullet);
        }
    }

    /**
     * Setup for listener for clicks.
     */
    private void gunSetup() {
        scene.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY && cooldown == 0) {
                cooldown = maxCooldown;
                shootGun();
            }
        });
    }

    /**
     * Shoot the gun.
     */
    public void shootGun() {
        // Check all bullets.
        for (Node bullet : bullets) {
            // If bullet is not in scene, means bullet is ready to be used.
            if (!scene.getChildren().contains(bullet)) {
                // Player is facing right.
                if (playerImage.getScaleX() > 0) {
                    // Move bullet in front of X Coord of player.
                    bullet.setTranslateX(playerImage.getTranslateX() + playerImage.getFitWidth());
                    // Update bullet scale to know where to shoot.
                    bullet.setScaleX(1);
                // Player is facing left.
                } else {
                    bullet.setTranslateX(playerImage.getTranslateX() - 15);
                    bullet.setScaleX(-1);
                }
                // Move bullet to player gun height.
                bullet.setTranslateY(playerImage.getTranslateY() + 29);
                // Add bullet to scene after editing coords.
                scene.getChildren().add(bullet);
                // return so we only do this for one bullet.
                return;
            }
        }
    }

    /**
     * Move the bullets in scene.
     * @param bullet Bullet to be moved.
     * @param movingRight If bullet is moving right.
     */
    public void moveBullet(Node bullet, boolean movingRight) {
        // Check for collision for one power at a time.
        for (int i = 0; i < bulletSpeed; i++) {
            // Check all platforms.
            for (Node platform : platforms) {
                // If bullet intersects with any platform.
                if (bullet.getBoundsInParent().intersects(platform.getBoundsInParent())) {
                    // Remove bullet from scene.
                    scene.getChildren().remove(bullet);
                    return;
                }
            }
            // Check all monsters
            for (Monster monster : monsters) {
                // If bullet intersects with any monster.
                if (bullet.getBoundsInParent().intersects(monster.getMonsterBox())) {
                    // Remove monster from scene.
                    monster.removeFromScene(scene);
                    // Remove bullet from scene.
                    scene.getChildren().remove(bullet);
                    return;
                }
            }
            // Move bullet one power at a time.
            bullet.setTranslateX(bullet.getTranslateX() + (movingRight ? 1 : -1));
            // If bullet outside of screen, remove bullet.
            if (bullet.getTranslateX() < 0 || bullet.getTranslateX() > levelWidth) {
                scene.getChildren().remove(bullet);
            }
        }
    }

    // Timer to update shooting cooldown.
    AnimationTimer cooldownTimer = new AnimationTimer() {
        @Override
        public void handle(long l) {
            if (cooldown > 0) {
                cooldown -= 1;
            }
        }
    };

    // Timer to check if bullets need to be moved in scene.
    AnimationTimer shootAnimation = new AnimationTimer() {
        @Override
        public void handle(long l) {
            // Check all bullets.
            for (Node bullet : bullets) {
                // If bullet is in scene, move according to scale.
                if (scene.getChildren().contains(bullet)) {
                    if (bullet.getScaleX() > 0) {
                        moveBullet(bullet, true);
                    } else {
                        moveBullet(bullet, false);
                    }
                }
            }
        }
    };
}
