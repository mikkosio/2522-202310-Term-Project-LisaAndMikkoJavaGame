package ca.bcit.comp2522.termproject.lisaandmikkojavagame;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;

public class PlayerMovement {
    private boolean isDPressed = false;
    private boolean isAPressed = false;
    private boolean isSpacePressed = false;
    private int playerSpeed = 2;
    @FXML
    private ImageView player;
    @FXML
    private AnchorPane scene;
    private ArrayList<Node> platforms;

    public void makeMovable(ImageView player, AnchorPane scene, ArrayList<Node> platforms) {
        this.player = player;
        this.scene = scene;
        this.platforms = platforms;
        movementSetup();
        timer.start();
    }

    public void move(int power, boolean right) {
        if (!right) {
            power *= -1;
        }
        // reflect player
        if (player.getScaleX() < 0 && right) {
            player.setScaleX(player.getScaleX() * -1);
        } else if (player.getScaleX() > 0 && !right) {
            player.setScaleX(player.getScaleX() * -1);
        }
        // move player
        for (Node platform : platforms) {
            if (player.getBoundsInParent().intersects(platform.getBoundsInParent())) {
                if (right && player.getLayoutX() + 42 == platform.getLayoutX()
                        || !right && player.getLayoutX() == platform.getLayoutX()
                        + platform.getBoundsInParent().getWidth() - 15) {
                    return;
                }
            }
        }
        player.setLayoutX(player.getLayoutX() + power);
    }

    public void jump(int jumpPower) {
        for (int i = 0; i < jumpPower; i++) {
            player.setTranslateY(player.getTranslateY() - 1);
        }
    }

    AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long l) {
            if (isSpacePressed) {
                jump(10);
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
        });
    }

}
