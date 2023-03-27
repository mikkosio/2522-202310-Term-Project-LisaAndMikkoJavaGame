package ca.bcit.comp2522.termproject.lisaandmikkojavagame;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class PlayerMovement {
    private boolean isDPressed = false;
    private boolean isAPressed = false;
    private boolean isSpacePressed = false;
    private boolean lookingRight = true;
    private boolean grounded = false;
    private int playerSpeed = 5;
    private int jumpPower = 3;
    private int gravity = 1;
    // Image of player
    @FXML
    private ImageView player;
    // For better collision checking
    private Rectangle playerBox;
    @FXML
    private AnchorPane scene;
    private ArrayList<Node> platforms;
    private ArrayList<ImageView> monsters;

    public void makeMovable(ImageView player, AnchorPane scene, Rectangle playerBox, ArrayList<Node> platforms, ArrayList<ImageView> monsters) {
        this.player = player;
        this.playerBox = playerBox;
        this.scene = scene;
        this.platforms = platforms;
        this.monsters = monsters;
        movementSetup();
        timer.start();
    }

    public void move(int power, boolean right) {
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
                    System.out.println(playerBox.getTranslateX());
                    System.out.println(platform.getTranslateX()
                            + platform.getBoundsInParent().getWidth());
                    if (right && playerBox.getTranslateX() + playerBox.getWidth() == platform.getTranslateX()
                            || !right && playerBox.getTranslateX() == platform.getTranslateX()
                            + platform.getBoundsInParent().getWidth() - 1) {
                        return;
                    }
                }
            }
            /// todo: intersect handling for monsters
            playerBox.setTranslateX(playerBox.getTranslateX() + (right ? 1 : -1));
            power--;
        }

    }

    public void jump(int jumpPower) {
        for (Node platform : platforms) {
            if (playerBox.getBoundsInParent().intersects(platform.getBoundsInParent())) {
                if (playerBox.getTranslateY() == platform.getTranslateY()
                        + platform.getBoundsInParent().getHeight() - jumpPower) {
                    return;
                }
            }
        }
        playerBox.setTranslateY(playerBox.getTranslateY() - jumpPower);
    }

    AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long l) {
            // checking if player is on ground
            for (Node platform : platforms) {
                if (playerBox.getTranslateY() + playerBox.getHeight() == platform.getTranslateY()
                        && playerBox.getTranslateX() < platform.getTranslateX()
                            + platform.getBoundsInParent().getWidth()
                        && playerBox.getTranslateX() + playerBox.getWidth() > platform.getTranslateX()) {
                    grounded = true;
                    break;
                } else {
                    grounded = false;
                }
            }

            // gravity
            if (!grounded) {
                playerBox.setTranslateY(playerBox.getTranslateY() + gravity);
            }

            // movement
            if (isSpacePressed) {
                jump(jumpPower);
            }
            if (isDPressed) {
                move(playerSpeed, true);
            }
            if (isAPressed) {
                move(playerSpeed, false);
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
