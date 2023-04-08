package ca.bcit.comp2522.termproject.lisaandmikkojavagame;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;
import java.util.Optional;


/**
 * Level.
 *
 * @author Mikko Sio
 * @author Lisa Jung
 * @version April 6, 2023
 */
public class Level {
    // List of platforms in the level.
    private ArrayList<Node> platforms;
    // List of monsters in the level.
    private ArrayList<Monster> monsters;
    // List of power-ups in the level.
    private ArrayList<PowerUp> powerUps;
    // Player's collision box.
    private Rectangle playerBox;
    // End portal of the level.
    private ImageView portal;
    // How wide level is.
    private final int levelWidth;
    // How high level is.
    private final int levelHeight;
    // Starting xCoordinate of the player.
    private int startX;
    // Starting yCoordinate of the player.
    private int startY;

    /**
     * Constructor for Level class.
     * @param levelWidth Width of the level.
     * @param levelHeight Height of the level.
     * @param startX Player's initial xCoordinate.
     * @param startY Player's initial yCoordinate.
     */
    public Level(final int levelWidth, final int levelHeight, final int startX, final int startY) {
        platforms = new ArrayList<>();
        monsters = new ArrayList<>();
        powerUps = new ArrayList<>();
        this.levelWidth = levelWidth;
        this.levelHeight = levelHeight;
        this.startX = startX;
        this.startY = startY;
    }

    // Timer to track if the level has been completed.
    private AnimationTimer checkCompletion = new AnimationTimer() {
        @Override
        public void handle(long l) {
            if (playerBox.getBoundsInParent().intersects(portal.getBoundsInParent())) {
                winPopup();
                checkCompletion.stop();
            }
        }
    };

    // Popup message for completion of the game.
    private void winPopup() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Win");
            alert.setHeaderText("You Won!!");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                System.exit(0);
            }
        });
    }

    /**
     * Getter for level width.
     * @return Width of the level.
     */
    public int getLevelWidth() {
        return levelWidth;
    }

    /**
     * Getter for level height.
     * @return Height of the level.
     */
    public int getLevelHeight() {
        return levelHeight;
    }

    /**
     * Add a platform to the level.
     * @param xCoordinate xCoordinate of the platform.
     * @param yCoordinate yCoordinate of the platform.
     * @param width Width of the platform.
     * @param height Height of the platform.
     * @param color Color of the platform.
     */
    public void addPlatform(final int xCoordinate, final int yCoordinate, final int width, final int height,
                            final Color color) {
        // Create platform.
        Rectangle platform = new Rectangle(width, height, color);
        platform.setTranslateX(xCoordinate);
        platform.setTranslateY(yCoordinate);
        platform.setStroke(Color.BLACK);
        // Add platform to platforms list of level.
        platforms.add(platform);
    }

    /**
     * Getter for platforms ArrayList.
     * @return List of platforms in the level.
     */
    public ArrayList<Node> getPlatforms() {
        return platforms;
    }

    /**
     * Add a monster to the level.
     * @param monsterType Type of monster.
     * @param xCoordinate xCoordinate of monster.
     * @param yCoordinate yCoordinate of monster.
     */
    public void addMonster(MonsterType monsterType, int xCoordinate, int yCoordinate) {
        Image image = new Image(getClass().getResourceAsStream("/ca/bcit/comp2522/termproject/lisaandmikkojavagame/monster1.png"));
        ImageView monsterImage = new ImageView(image);
        Monster monster = new Monster(monsterImage, MonsterType.MONSTER1, 0.50);
        monster.getMonsterImage().setLayoutX(xCoordinate);
        monster.getMonsterImage().setLayoutY(yCoordinate);
        monster.getMonsterImage().setFitHeight(50);
        monster.getMonsterImage().setFitWidth(50);
        monsters.add(monster);
    }

    public void addMonster2(MonsterType monsterType, int xCoordinate, int yCoordinate) {
        Image image = new Image(getClass().getResourceAsStream("/ca/bcit/comp2522/termproject/lisaandmikkojavagame/monster2.png"));
        ImageView monsterImage = new ImageView(image);
        Monster monster = new Monster(monsterImage, MonsterType.MONSTER2, 0.70);
        monster.getMonsterImage().setLayoutX(xCoordinate);
        monster.getMonsterImage().setLayoutY(yCoordinate);
        monster.getMonsterImage().setFitHeight(50);
        monster.getMonsterImage().setFitWidth(50);
        monsters.add(monster);
    }

    public void addMonster3(MonsterType monsterType, int xCoordinate, int yCoordinate) {
        Image image = new Image(getClass().getResourceAsStream("/ca/bcit/comp2522/termproject/lisaandmikkojavagame/monster3.png"));
        ImageView monsterImage = new ImageView(image);
        Monster monster = new Monster(monsterImage, MonsterType.MONSTER3, 0.70);
        monster.getMonsterImage().setLayoutX(xCoordinate);
        monster.getMonsterImage().setLayoutY(yCoordinate);
        monster.getMonsterImage().setFitHeight(50);
        monster.getMonsterImage().setFitWidth(50);
        monsters.add(monster);
    }

    public ArrayList<Monster> getMonsters() {
        return monsters;
    }

    // Add power ups
    public void addPowerUp(int xCoordinate, int yCoordinate) {
        Image image = new Image(getClass().getResourceAsStream("/ca/bcit/comp2522/termproject/lisaandmikkojavagame/icecream.png"));
        ImageView powerUpImage = new ImageView(image);
        PowerUp powerUp = new PowerUp(powerUpImage);
        powerUp.getPowerUpImage().setLayoutX(xCoordinate);
        powerUp.getPowerUpImage().setLayoutY(yCoordinate);
        powerUp.getPowerUpImage().setFitHeight(25);
        powerUp.getPowerUpImage().setFitWidth(25);
        powerUps.add(powerUp);
    }

    public ArrayList<PowerUp> getPowerUps() {
        return powerUps;
    }

    public void placeGoal(int xCoord, int yCoord) {
        Image image = new Image(getClass().getResourceAsStream("/ca/bcit/comp2522/termproject/lisaandmikkojavagame/portal.png"));
        ImageView portalImage = new ImageView(image);
        Portal portal = new Portal(portalImage);
        portal.getPortalImage().setTranslateX(xCoord);
        portal.getPortalImage().setTranslateY(yCoord);
        portal.getPortalImage().setFitWidth(100);
        portal.getPortalImage().setFitHeight(100);
        this.portal = portal.getPortalImage();
        checkCompletion.start();
    }

    public void placePlayer(int width, int height) {
        Rectangle player = new Rectangle(width, height);
        player.setTranslateX(startX);
        player.setTranslateY(startY);
        playerBox = player;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public Rectangle getPlayerBox() {
        return playerBox;
    }

    // Add monster, platforms, and powerups to scene

    public void fillScene(AnchorPane scene) {
        for (Node platform : platforms) {
            scene.getChildren().add(platform);
        }
        for (Monster monster: monsters) {
            scene.getChildren().add(monster.getMonsterImage());
        }
        for (PowerUp powerup : powerUps) {
            scene.getChildren().add(powerup.getPowerUpImage());
        }
        scene.getChildren().add(portal);
    }

}
