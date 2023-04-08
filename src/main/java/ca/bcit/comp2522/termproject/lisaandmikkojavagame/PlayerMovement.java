package ca.bcit.comp2522.termproject.lisaandmikkojavagame;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;

/**
 * Movement script for player.
 *
 * @author Mikko Sio
 * @author Lisa Jung
 * @version April 6, 2023
 */
public class PlayerMovement {
    // Boolean for D key status.
    private boolean isDPressed = false;
    // Boolean for A key status.
    private boolean isAPressed = false;
    // Boolean for Space key status.
    private boolean isSpaceReleased = true;
    // Boolean for player direction.
    private boolean lookingRight = true;
    // Boolean for player's jump status.
    private boolean canJump = true;
    // Speed of the player.
    private int playerSpeed = 3;
    // Jump power of the player.
    private int jumpPower = 45;
    // yVelocity of the player.
    private int yVelocity;
    // Gravity affecting the player.
    private double gravity = 1;
    // Maximum gravity affecting the player.
    private int maxGravity = 15;
    // Scene of the game.
    @FXML
    private AnchorPane scene;
    // Image of player
    @FXML
    private ImageView player;
    // For better collision checking.
    private Rectangle playerBox;
    // List of platforms in level.
    private ArrayList<Node> platforms;
    // List of monsters in level.
    private ArrayList<Monster> monsters;
    // List of power-ups in level.
    private ArrayList<PowerUp> powerUps;
    // Player's health.
    private PlayerHealth health;
    private double monsterStartX = 0;
    private double monsterEndX = 200;
    private boolean monsterMovingRight = true;

    /**
     * Constructor for PlayerMovement class.
     * @param player Image of the player.
     * @param scene Scene of the game.
     * @param playerBox Collision box of the player.
     * @param platforms List of platforms in level.
     * @param monsters List of monsters in level.
     * @param powerUps List of power-ups in level.
     * @param healthBar Health of the player.
     */
    public PlayerMovement(final ImageView player, final AnchorPane scene, final Rectangle playerBox,
                          final ArrayList<Node> platforms, final ArrayList<Monster> monsters,
                          final ArrayList<PowerUp> powerUps, final PlayerHealth healthBar) {
        this.player = player;
        this.playerBox = playerBox;
        this.scene = scene;
        this.platforms = platforms;
        this.monsters = monsters;
        this.powerUps = powerUps;
        this.health = healthBar;
        movementSetup();
        timer.start();
    }

    /**
     * Move player horizontally.
     * @param power Amount to move.
     * @param right If player is moving right.
     */
    public void horizontalMovement(int power, boolean right) {
        // reflect player
        if (player.getScaleX() < 0 && right) {
            lookingRight = true;
            player.setScaleX(player.getScaleX() * -1);
        } else if (player.getScaleX() > 0 && !right) {
            lookingRight = false;
            player.setScaleX(player.getScaleX() * -1);
        }
        // move player
        while (power != 0) {
            // Check platforms for collision.
            for (Node platform : platforms) {
                if (playerBox.getBoundsInParent().intersects(platform.getBoundsInParent())) {
                    if ((right && playerBox.getTranslateX() + playerBox.getWidth() == platform.getTranslateX()
                            || !right && playerBox.getTranslateX() == platform.getTranslateX()
                                + platform.getBoundsInParent().getWidth() - 1)
                            && playerBox.getTranslateY() + playerBox.getHeight() != platform.getTranslateY()) {
                        return;
                    }
                }
            }
            // Check power-ups for collision.
            for (PowerUp powerUp : powerUps) {
                if (playerBox.getBoundsInParent().intersects(powerUp.getPowerUpBox())) {
                    powerUp.removePowerUpFromScene(scene);
                    powerUp.powerUp(player, playerBox);
                }
            }
            // Move player by 1 and decrement amount to move.
            playerBox.setTranslateX(playerBox.getTranslateX() + (right ? 1 : -1));
            power--;
        }
    }

    /**
     * Move player vertically.
     * @param power Amount to move.
     */
    public void verticalMovement(int power) {
        // If player is falling, disable jumping.
        if (power > 0) {
            canJump = false;
        }
        // Move player.
        for (int i = 0; i < Math.abs(power); i++) {
            // reset player, if they fall off map.
            if (playerBox.getTranslateY() + playerBox.getHeight() > 1000) {
                // reset keys
                isAPressed = false;
                isDPressed = false;
                // reset level
                health.updateHealthBar(0);
                // stop movement
                timer.stop();
                return;
            }
            // collision check for platforms.
            for (Node platform : platforms) {
                if (playerBox.getBoundsInParent().intersects(platform.getBoundsInParent())) {
                    // Collision check for jumping.
                    if (playerBox.getTranslateY() == platform.getTranslateY() + platform.getBoundsInParent().getHeight()
                            - 1) {
                        playerBox.setTranslateY(playerBox.getTranslateY() + 1);
                        yVelocity = 0;
                        return;
                    // Collision check for falling.
                    } else if (playerBox.getTranslateY() + playerBox.getHeight() == platform.getTranslateY()
                            && power > 0
                            && playerBox.getTranslateX() != platform.getTranslateX()
                                + platform.getBoundsInParent().getWidth() - 1
                            && playerBox.getTranslateX() + playerBox.getWidth() != platform.getTranslateX()) {
                        canJump = true;
                        return;
                    }
                }
            }
            // Collision check for monsters.
            for (Monster monster : monsters) {
                if (playerBox.getBoundsInParent().intersects(monster.getMonsterBox())) {
                    // If player jumps on monster.
                    if (playerBox.getTranslateY() < monster.getMonsterBox().getMinY()) {
                        // Kill monster.
                        monster.removeFromScene(scene);
                    }
                    return;
                }
            }
            // Collision check for power-ups.
            for (PowerUp powerUp : powerUps) {
                if (playerBox.getBoundsInParent().intersects(powerUp.getPowerUpBox())) {
                    powerUp.removePowerUpFromScene(scene);
                    powerUp.powerUp(player, playerBox);
                }
            }
            if (power > 0) {
                // falling
                playerBox.setTranslateY(playerBox.getTranslateY() + 1);
                power--;
            } else {
                // jumping
                playerBox.setTranslateY(playerBox.getTranslateY() - 1);
                power++;
            }
        }
    }

    /**
     * Update yVelocity according to jump power.
     */
    public void jump() {
        yVelocity -= jumpPower;
        canJump = false;
    }

    // Time to track player's movement.
    private AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long l) {
            // Stop player from automatically moving after restart
            if (health.getHealth() <= 0) {
                stop();
                isDPressed = false;
                isAPressed = false;
                isSpaceReleased = true;
                return;
            }

            // monster movement back and forth
            for (Monster monster : monsters) {
                double x = monster.getMonsterImage().getTranslateX();
                double y = monster.getMonsterImage().getTranslateY();
                if (monsterMovingRight && x >= monsterEndX) {
                    monsterMovingRight = false;
                } else if (!monsterMovingRight && x <= monsterStartX) {
                    monsterMovingRight = true;
                }

                // Move monster
                if (monsterMovingRight) {
                    x += monster.getMonsterSpeed();
                } else {
                    x -= monster.getMonsterSpeed();
                }
                monster.getMonsterImage().setTranslateX(x);
                monster.getMonsterImage().setTranslateY(y);
                // powered up player intersects with monster
                if (PowerUp.poweredUp) {
                    if (playerBox.getBoundsInParent().intersects(monster.getMonsterBox())) {
                        monster.removeFromScene(scene);
                    }
                } else {
                    // player collision with monster
                    if (playerBox.getBoundsInParent().intersects(monster.getMonsterBox())) {
                        boolean playerIsRightOfMonster = player.getBoundsInParent().getCenterX() > monster.getMonsterImage().getBoundsInParent().getCenterX();
                        int direction = !playerIsRightOfMonster ? -1 : 1;
                        playerBox.setTranslateX(playerBox.getTranslateX() + (direction * 50));
                        player.setTranslateX(playerBox.getTranslateX());
                        monster.doesDamage(health);
                    }
                }
            }
            // Player gravity.
            if (yVelocity < maxGravity) {
                yVelocity += gravity;
            }
            // Move player vertically each timer loop.
            verticalMovement(yVelocity);
            // Check if keys are pressed.
            if (isDPressed) {
                // Move right.
                horizontalMovement(playerSpeed, true);
            }
            if (isAPressed) {
                // Move left.
                horizontalMovement(playerSpeed, false);
            }
            // Updating playerImage position to stay on playerBox.
            player.setTranslateY(playerBox.getTranslateY() + 1);
            if (lookingRight) {
                player.setTranslateX(playerBox.getTranslateX() - 6);
            } else {
                player.setTranslateX(playerBox.getTranslateX() - 18);
            }
        }
    };

    // Setup for key listener.
    private void movementSetup() {
        scene.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.D) {
                isDPressed = true;
            }
            if (keyEvent.getCode() == KeyCode.A) {
                isAPressed = true;
            }
            if (keyEvent.getCode() == KeyCode.SPACE && canJump && isSpaceReleased) {
                jump();
                isSpaceReleased = false;
            }
        });

        scene.setOnKeyReleased(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.D) {
                isDPressed = false;
            }
            if (keyEvent.getCode() == KeyCode.A) {
                isAPressed = false;
            }
            if (keyEvent.getCode() == KeyCode.SPACE) {
                isSpaceReleased = true;
            }
        });
    }
}
