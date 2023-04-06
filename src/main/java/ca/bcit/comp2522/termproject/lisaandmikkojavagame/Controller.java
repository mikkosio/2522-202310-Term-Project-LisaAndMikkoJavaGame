package ca.bcit.comp2522.termproject.lisaandmikkojavagame;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    static final int PLAYER_HEIGHT = 60;
    static final int PLAYER_WIDTH = 32;
    // Container for health bar.
    @FXML
    private VBox healthBarVBox;
    @FXML
    private AnchorPane scene;
    // Image of player.
    @FXML
    private ImageView player;
    // To update screen camera.
    private Camera camera;
    private PlayerMovement playerMovement;
    private PlayerGun playerGun;
    private PlayerHealth health;
    @FXML
    private ProgressBar healthBar;


    private Level createTestLevel() {
        Level level = new Level(3200, 1600, 0, 400);
        // Place player
        level.placePlayer(PLAYER_WIDTH, PLAYER_HEIGHT);
        // Create platforms
        level.addPlatform(0, 460, 200, 100, Color.DARKGRAY);
        level.addPlatform(300, 460, 2900, 100, Color.DARKGRAY);
        level.addPlatform(900, 400, 60, 60, Color.DARKGRAY);
        level.addPlatform(640, 400, 60, 60, Color.DARKGRAY);
        level.addPlatform(760, 290, 120, 60, Color.DARKGRAY);
        level.addPlatform(960, 150, 120, 60, Color.DARKGRAY);
        level.addPlatform(1060, 0, 120, 60, Color.DARKGRAY);
        level.addPlatform(1160, -150, 120, 60, Color.DARKGRAY);
        level.addPlatform(1260, -300, 120, 60, Color.DARKGRAY);
        level.addPlatform(1360, -450, 120, 60, Color.DARKGRAY);
        level.placeGoal(760, 400);
        level.addMonster(MonsterType.MONSTER1, 1050, 398);
        level.addPowerUp(1000, 300);
        return level;
    }

    private Level createLevel1() {
        // make new level object
        Level level = new Level(3200, 1600, 50, 100);
        // player
        level.placePlayer(PLAYER_WIDTH, PLAYER_HEIGHT);
        // borders
        level.addPlatform(-1, -800, 1, 1600, Color.DARKGRAY);
        level.addPlatform(3200, -800,1, 1600, Color.DARKGRAY);
        // platforms
        level.addPlatform(0, 460, 200, 340, Color.DARKGRAY);
        level.addPlatform(300, 430, 200, 100, Color.DARKGRAY);
        level.addPlatform(650, 460, 200, 100, Color.DARKGRAY);
        level.addPlatform(1000, 600, 200, 100, Color.DARKGRAY);
        level.addPlatform(1350, 460, 200, 100, Color.DARKGRAY);
        level.addPlatform(1700, 460, 200, 100, Color.DARKGRAY);
        level.addPlatform(2000, 300, 200, 100, Color.DARKGRAY);
        level.addPlatform(2300, 160, 200, 100, Color.DARKGRAY);
        level.addPlatform(2650, 0, 350, 100, Color.DARKGRAY);
        level.addPlatform(3000, 0, 200, 800, Color.DARKGRAY);
        // monsters
        level.addMonster(MonsterType.MONSTER1, 900, 400);
        level.addMonster(MonsterType.MONSTER3, 1100, 400);
        level.addMonster(MonsterType.MONSTER2, 1700, 350);
        // end goal
        level.placeGoal(3100, -100);
        return level;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playerMovement = new PlayerMovement();
        playerGun = new PlayerGun();
        init();
    }

    private void init() {
        resetScene();
//        Level level = createTestLevel();
        Level level = createLevel1();
        // Fill the scene with level's Nodes.
        level.fillScene(scene);
        // Set player image to front.
        player.setViewOrder(-1);
        // Initialize the camera of the scene.
        camera = new Camera(scene, level.getPlayerBox(), level.getLevelWidth(), level.getLevelHeight(),
                healthBarVBox);
        // Initialize the player's health.
        health = new PlayerHealth(healthBar);
        // Setup player movement.
        playerMovement.makeMovable(player, scene, level.getPlayerBox(), level.getPlatforms(), level.getMonsters(),
                level.getPowerUps(), health, level.getStartX(), level.getStartY(), camera);
        // Setup player gun.
        playerGun.makeGun(scene, player, level.getPlatforms(), level.getMonsters(), level.getLevelWidth());
        // Start restart checker.
        restartTimer.start();
    }

    // Reset scene for when game is restarted
    private void resetScene() {
        int size = scene.getChildren().size();
        for (int i = 3; i < size; i++) {
            scene.getChildren().remove(3);
        }
        scene.setLayoutX(0);
        scene.setLayoutY(0);
        healthBarVBox.setTranslateX(0);
        healthBarVBox.setTranslateY(0);
        healthBar.setProgress(100);
    }

    AnimationTimer restartTimer = new AnimationTimer() {
        @Override
        public void handle(long l) {
            if (health.needsRestart()) {
                init();
            }
        }
    };
}