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

public class PlayerGun {
    // Image of player.
    @FXML
    private ImageView playerImage;
    // Anchor Pane scene.
    @FXML
    private AnchorPane scene;
    // All platforms in current level.
    private ArrayList<Node> platforms;
    // All monsters in current level.
    private ArrayList<Monster> monsters;
    // Bullets to be used in list.
    private ArrayList<Node> bullets;
    // Speed of bullet.
    private int bulletSpeed;
    // Cooldown to shoot.
    private int cooldown;
    // Max cooldown.
    private int maxCooldown;
    // Multiplier to make countdown into seconds in animation timers.

    public void makeGun(AnchorPane scene, ImageView playerImage, ArrayList<Node> platforms, ArrayList<Monster> monsters) {
        this.playerImage = playerImage;
        this.scene = scene;
        this.platforms = platforms;
        this.monsters = monsters;
        this.bulletSpeed = 7;
        cooldown = 0;
        maxCooldown = 50;
        bullets = new ArrayList<>();
        // Initialize all bullets to arraylist and add to scene.
        makeBullets();
        // Listen for mouseclicks
        gunSetup();
        // Start animation to move bullets.
        shootAnimation.start();
        // Start cooldown timer.
        cooldownTimer.start();
    }

    public void setCooldown(int cooldown) {
        maxCooldown = cooldown;
    }

    private void makeBullets() {
        for (int i = 0; i < 10; i++) {
            Rectangle newBullet = new Rectangle(12, 5, Color.RED);
            newBullet.setTranslateX(10000);
            newBullet.setTranslateY(10000);
            bullets.add(newBullet);
            scene.getChildren().add(newBullet);
        }
    }

    private void gunSetup() {
        scene.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY && cooldown == 0) {
                cooldown = maxCooldown;
                shootGun();
            }
        });
    }

    public void shootGun() {
        // Check all bullets.
        for (Node bullet : bullets) {
            // If coords in 10000, means bullet is ready to be used.
            if (bullet.getTranslateX() == 10000 && bullet.getTranslateY() == 10000) {
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
                // return so we only do this for one bullet.
                return;
            }
        }
    }

    public void moveBullet(Node bullet, boolean movingRight) {
        // Check for collision for one power at a time.
        for (int i = 0; i < bulletSpeed; i++) {
            // Check all platforms.
            for (Node platform : platforms) {
                // If bullet intersects with any platform, reset and return.
                if (bullet.getBoundsInParent().intersects(platform.getBoundsInParent())) {
                    System.out.println("boom");
                    // Reset coords of bullet.
                    resetBullet(bullet);
                    return;
                }
            }
            // Check all monsters
            for (Monster monster : monsters) {
                // If bullet intersects with any monster.
                if (bullet.getBoundsInParent().intersects(monster.getMonsterBox())) {
                    // Remove monster from scene.
                    monster.removeFromScene(scene);
                    // Reset bullet coords.
                    resetBullet(bullet);
                    return;
                }
            }
            // Move bullet one power at a time.
            bullet.setTranslateX(bullet.getTranslateX() + (movingRight ? 1 : -1));

        }
    }

    private void resetBullet(Node bullet) {
        // Reset to default coords (outside of screen).
        bullet.setTranslateX(10000);
        bullet.setTranslateY(10000);
    }

    AnimationTimer cooldownTimer = new AnimationTimer() {
        @Override
        public void handle(long l) {
            if (cooldown > 0) {
                cooldown -= 1;
            }
        }
    };

    AnimationTimer shootAnimation = new AnimationTimer() {
        @Override
        public void handle(long l) {
            // Check all bullets.
            for (Node bullet : bullets) {
                // If bullet is not in default Y and bullet is outside of screen, reset it.
                if (bullet.getTranslateY() != 10000 && 0 > bullet.getTranslateX() || bullet.getTranslateX() > 1600) {
                    resetBullet(bullet);
                }
                // If bullet is not in default coords, move according to scale.
                if (bullet.getTranslateX() != 10000) {
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
