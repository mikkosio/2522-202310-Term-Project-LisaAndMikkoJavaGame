package ca.bcit.comp2522.termproject.lisaandmikkojavagame;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private VBox healthBarVBox;
    @FXML
    private AnchorPane scene;
    @FXML
    private ImageView player;
    private Camera camera;
    private PlayerMovement playerMovement = new PlayerMovement();
    private PlayerGun playerGun =  new PlayerGun();
    private ArrayList<Level> levels;

    private PlayerHealth health;

    @FXML
    private ProgressBar healthBar;


    private Level createTestLevel() {
        Level level = new Level(3200, 1600);
        // Place player
        level.placePlayer(0, 400, 32, 60);
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
        level.addMonster(MonsterType.MONSTER1, 1050, 398);
        return level;
    }

    private Level createLevel1() {
        Level level = new Level(3200, 1600);
        level.placePlayer(50, 100, 32, 60);
        level.addPlatform(0, 460, 200, 340, Color.DARKGRAY);
        level.addPlatform(300, 460, 200, 400, Color.DARKGRAY);

        return level;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Level level1 = createTestLevel();
        level1.fillScene(scene);
        player.setViewOrder(-1);
        camera = new Camera(scene, level1.getPlayerBox(), level1.getLevelWidth(), level1.getLevelHeight(),
                healthBarVBox);
        playerMovement.makeMovable(player, scene, level1.getPlayerBox(), level1.getPlatforms(), level1.getMonsters(),
                level1.getPowerUps(), healthBar);
        playerGun.makeGun(scene, player, level1.getPlatforms(), level1.getMonsters(), level1.getLevelWidth());
        health = new PlayerHealth(healthBar);
    }

}