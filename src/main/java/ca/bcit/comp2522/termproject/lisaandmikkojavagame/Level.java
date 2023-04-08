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
import java.util.Objects;
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
    public void addMonster(final MonsterType monsterType, final int xCoordinate, final int yCoordinate) {
        // Setting monster type and image.
        Image image;
        ImageView monsterImage;
        Monster monster;
        switch (monsterType) {
            case MONSTER2:
                image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ca/bcit/comp2522/"
                        + "termproject/lisaandmikkojavagame/monster2.png")));
                monsterImage = new ImageView(image);
                monster = new Monster(monsterImage, MonsterType.MONSTER2, 0.70);
                break;
            case MONSTER3:
                image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ca/bcit/comp2522/"
                        + "termproject/lisaandmikkojavagame/monster3.png")));
                monsterImage = new ImageView(image);
                monster = new Monster(monsterImage, MonsterType.MONSTER3, 0.70);
                break;
            default:
                image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ca/bcit/comp2522/"
                        + "termproject/lisaandmikkojavagame/monster1.png")));
                monsterImage = new ImageView(image);
                monster = new Monster(monsterImage, MonsterType.MONSTER1, 0.50);
        }
        // Set monster coordinates.
        monster.getMonsterImage().setLayoutX(xCoordinate);
        monster.getMonsterImage().setLayoutY(yCoordinate);
        // Set monster size.
        monster.getMonsterImage().setFitHeight(50);
        monster.getMonsterImage().setFitWidth(50);
        // Add monster to monsters list.
        monsters.add(monster);
    }

    /**
     * Getter for monsters ArrayList.
     * @return List of monsters in level.
     */
    public ArrayList<Monster> getMonsters() {
        return monsters;
    }

    /**
     * Add power-ups to level.
     * @param xCoordinate xCoordinate of power-up.
     * @param yCoordinate yCoordinate of power-up.
     */
    public void addPowerUp(final int xCoordinate, final int yCoordinate) {
        // Set power-up's image.
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ca/bcit/comp2522/"
                + "termproject/lisaandmikkojavagame/icecream.png")));
        ImageView powerUpImage = new ImageView(image);
        PowerUp powerUp = new PowerUp(powerUpImage);
        // Set location of power-up.
        powerUp.getPowerUpImage().setLayoutX(xCoordinate);
        powerUp.getPowerUpImage().setLayoutY(yCoordinate);
        // Set size of power-up.
        powerUp.getPowerUpImage().setFitHeight(25);
        powerUp.getPowerUpImage().setFitWidth(25);
        // Add power-up to power-ups list.
        powerUps.add(powerUp);
    }

    /**
     * Getter for power-ups ArrayList
     * @return List of power-ups in level.
     */
    public ArrayList<PowerUp> getPowerUps() {
        return powerUps;
    }

    /**
     * Place the end goal of the level.
     * @param xCoordinate xCoordinate of the portal.
     * @param yCoordinate yCoordinate of the portal.
     */
    public void placeGoal(final int xCoordinate, final int yCoordinate) {
        // Set the image of the portal.
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ca/bcit/comp2522/"
                + "termproject/lisaandmikkojavagame/portal.png")));
        ImageView portalImage = new ImageView(image);
        Portal newPortal = new Portal(portalImage);
        // Set the location of the portal.
        newPortal.getPortalImage().setTranslateX(xCoordinate);
        newPortal.getPortalImage().setTranslateY(yCoordinate);
        // Set the size of the portal.
        newPortal.getPortalImage().setFitWidth(100);
        newPortal.getPortalImage().setFitHeight(100);
        // Update level's portal.
        portal = newPortal.getPortalImage();
        // Start timer for checking level completion.
        checkCompletion.start();
    }

    /**
     * Place player in level.
     * @param width Width of the player.
     * @param height Height of the player.
     */
    public void placePlayer(final int width, final int height) {
        Rectangle player = new Rectangle(width, height);
        player.setTranslateX(startX);
        player.setTranslateY(startY);
        playerBox = player;
    }

    /**
     * Getter for player starting xCoordinate.
     * @return Starting xCoordinate of the player.
     */
    public int getStartX() {
        return startX;
    }

    /**
     * Getter for player starting yCoordinate.
     * @return Starting yCoordinate of the player.
     */
    public int getStartY() {
        return startY;
    }

    /**
     * Getter for player's collision box.
     * @return Player's collision box.
     */
    public Rectangle getPlayerBox() {
        return playerBox;
    }

    /**
     * Add all the elements of the level to the scene.
     * Displaying them to the scene.
     * @param scene Scene of the game.
     */
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
