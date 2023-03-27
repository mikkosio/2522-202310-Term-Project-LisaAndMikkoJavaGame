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
    private int playerSpeed = 2;
    private int jumpPower = 1;
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
        setPlayerImage.start();
        timer.start();
    }

    public void move(int power, boolean right) {
        if (!right) {
            power *= -1;
        }
        // reflect player
        if (player.getScaleX() < 0 && right) {
            lookingRight = true;
            player.setScaleX(player.getScaleX() * -1);
            player.setLayoutX(playerBox.getLayoutX() - 6);
        } else if (player.getScaleX() > 0 && !right) {
            lookingRight = false;
            player.setScaleX(player.getScaleX() * -1);
            player.setLayoutX(playerBox.getLayoutX() - 18);
        }
        // move player
        for (Node platform : platforms) {
            if (playerBox.getBoundsInParent().intersects(platform.getBoundsInParent())) {
                if (right && playerBox.getLayoutX() + playerBox.getWidth() == platform.getLayoutX()
                        || !right && playerBox.getLayoutX() == platform.getLayoutX()
                        + platform.getBoundsInParent().getWidth() - 1) {
                    return;
                }
            }
        }
        /// todo: intersect handling for monsters
        playerBox.setLayoutX(playerBox.getLayoutX() + power);
    }

    public void jump(int jumpPower) {

    }

    // Update the player image to stay on the playerBox
    AnimationTimer setPlayerImage = new AnimationTimer() {
        @Override
        public void handle(long l) {
            player.setLayoutY(playerBox.getLayoutY() + 1);
            if (lookingRight) {
                player.setLayoutX(playerBox.getLayoutX() - 6);
            } else {
                player.setLayoutX(playerBox.getLayoutX() - 18);
            }
        }
    };

    AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long l) {
            if (isSpacePressed) {
                jump(jumpPower);
            }
            if (isDPressed) {
                move(playerSpeed, true);
            }
            if (isAPressed) {
                move(playerSpeed, false);
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
