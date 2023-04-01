package ca.bcit.comp2522.termproject.lisaandmikkojavagame;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;

import java.util.ArrayList;

public class PlayerMovement {
    private boolean isDPressed = false;
    private boolean isAPressed = false;
    private boolean isSpacePressed = false;
    private boolean lookingRight = true;
    private boolean canJump = true;
    private int playerSpeed = 2;
    private int jumpPower = 40;
    private int numOfJumps;
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

    private PlayerHealth health;

    private double monsterStartX = 0;
    private double monsterEndX = 200;
    private boolean monsterMovingRight = true;

    public void makeMovable(ImageView player, AnchorPane scene, Rectangle playerBox, ArrayList<Node> platforms, ArrayList<Monster> monsters, ProgressBar healthBar) {
        this.player = player;
        this.playerBox = playerBox;
        this.scene = scene;
        this.platforms = platforms;
        this.monsters = monsters;
        this.health = new PlayerHealth(healthBar);
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
            // player intersects with monster
            for (Monster monster : monsters) {
                if (playerBox.getBoundsInParent().intersects(monster.getMonsterBox())) {
                    System.out.println(playerBox.getTranslateX());
                    System.out.println(monster.getMonsterImage().getTranslateX()
                            + monster.getMonsterBox().getWidth());
                    // what happens when u hit the monster
                    boolean movingRight = player.getScaleX() > 0;
                    int direction = movingRight ? -1 : 1;
                    // Move the player back in the opposite direction
                    playerBox.setTranslateX(playerBox.getTranslateX() + (direction * 50));
                    player.setTranslateX(playerBox.getTranslateX());
                    monster.doesDamage(health);
                    return;
                }
                playerBox.setTranslateX(playerBox.getTranslateX() + (right ? 1 : -1));
                power--;
            }
        }
    }

    public void verticalMovement(int power) {
        for (int i = 0; i < Math.abs(power); i++) {
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

                if (playerBox.getBoundsInParent().intersects(monster.getMonsterBox())) {
                    boolean movingRight = player.getScaleX() > 0;
                    int direction = movingRight ? -1 : 1;
                    playerBox.setTranslateX(playerBox.getTranslateX() + (direction * 50));
                    player.setTranslateX(playerBox.getTranslateX());
                    monster.doesDamage(health);
                }
            }
            //player movement
            if (yVelocity < maxGravity) {
                yVelocity += gravity;
            }

            verticalMovement(yVelocity);

            // movement
            if (isSpacePressed && canJump && numOfJumps < 1) {
                jump();
            }
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
            if (keyEvent.getCode() == KeyCode.SPACE) {
                isSpacePressed = true;
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
                isSpacePressed = false;
            }
        });
    }

}
