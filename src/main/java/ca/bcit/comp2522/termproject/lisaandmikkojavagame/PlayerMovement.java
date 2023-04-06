package ca.bcit.comp2522.termproject.lisaandmikkojavagame;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;

import java.util.ArrayList;

public class PlayerMovement {
    private boolean isDPressed = false;
    private boolean isAPressed = false;
    private boolean isSpaceReleased = true;
    private boolean lookingRight = true;
    private boolean canJump = true;
    private int playerSpeed = 3;
    private int jumpPower = 55;
    private int yVelocity;
    private double gravity = 1;
    private int maxGravity = 15;
    // Image of player
    @FXML
    private ImageView player;
    // For better collision checking
    private Rectangle playerBox;
    @FXML
    private AnchorPane scene;
    private ArrayList<Node> platforms;
    private ArrayList<Monster> monsters;
    private int startX;
    private int startY;
    private Camera camera;
    private PlayerHealth health;

    private double monsterStartX = 0;
    private double monsterEndX = 200;
    private boolean monsterMovingRight = true;
    private ArrayList<PowerUp> powerUps;

//    public void makeMovable(ImageView player, AnchorPane scene, Rectangle playerBox, ArrayList<Node> platforms, ArrayList<Monster> monsters, ArrayList<PowerUp> powerUps, PlayerHealth healthBar) {
    public void makeMovable(ImageView player, AnchorPane scene, Rectangle playerBox, ArrayList<Node> platforms,
                            ArrayList<Monster> monsters, ArrayList<PowerUp> powerUps, PlayerHealth healthBar,
                            int startX, int startY, Camera camera) {
        this.player = player;
        this.playerBox = playerBox;
        this.scene = scene;
        this.platforms = platforms;
        this.monsters = monsters;
        this.powerUps = powerUps;
        this.health = healthBar;
//        this.health = new PlayerHealth(healthBar);
        this.startX = startX;
        this.startY = startY;
        this.camera = camera;
        movementSetup();
        timer.start();
    }

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
            // intersection between player and powered up
            for (PowerUp powerUp : powerUps) {
                if (playerBox.getBoundsInParent().intersects(powerUp.getPowerUpBox())) {
                    powerUp.removePowerUpFromScene(scene);
                    powerUp.powerUp(player, playerBox);
                }
            }
            playerBox.setTranslateX(playerBox.getTranslateX() + (right ? 1 : -1));
            power--;
        }
    }


    public void verticalMovement(int power) {
        // If player is falling, disable jumping.
        if (power > 0) {
            canJump = false;
        }
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
                    if (playerBox.getTranslateY() == platform.getTranslateY()
                            + platform.getBoundsInParent().getHeight() - 1) {
                        playerBox.setTranslateY(playerBox.getTranslateY() + 1);
                        yVelocity = 0;
                        return;
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
            for (Monster monster : monsters) {
                if (playerBox.getBoundsInParent().intersects(monster.getMonsterBox())) {
                    //player jump on top of monster
                    if (playerBox.getTranslateY() < monster.getMonsterBox().getMinY()) {
                        monster.removeFromScene(scene);
                    }
                    return;
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

    public void jump() {
        yVelocity -= jumpPower;
        canJump = false;
    }


    AnimationTimer timer = new AnimationTimer() {
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


            //player movement
            if (yVelocity < maxGravity) {
                yVelocity += gravity;
            }

            verticalMovement(yVelocity);

            // movement
            if (isDPressed) {
                horizontalMovement(playerSpeed, true);
            }
            if (isAPressed) {
                horizontalMovement(playerSpeed, false);
            }
            // updating playerImage position to stay on playerBox
            player.setTranslateY(playerBox.getTranslateY() + 1);
            if (lookingRight) {
                player.setTranslateX(playerBox.getTranslateX() - 6);
            } else {
                player.setTranslateX(playerBox.getTranslateX() - 18);
            }
        }
    };

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
