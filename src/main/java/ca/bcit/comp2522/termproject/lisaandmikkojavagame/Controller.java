package ca.bcit.comp2522.termproject.lisaandmikkojavagame;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private AnchorPane scene;
    @FXML
    private ImageView player;
    private PlayerMovement playerMovement = new PlayerMovement();
    private ArrayList<Level> levels;

    private Level createLevel1() {
        Level level = new Level();
        // Create platforms
        level.addPlatform(0, 460, 1600, 100, Color.DODGERBLUE);
        level.addPlatform(900, 400, 60, 60, Color.DODGERBLUE);
        level.addPlatform(640, 400, 60, 60, Color.DODGERBLUE);
        return level;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Level level1 = createLevel1();
        level1.fillScene(scene);
        player.setViewOrder(-1);
        playerMovement.makeMovable(player, scene, level1.getPlatforms());
    }
}