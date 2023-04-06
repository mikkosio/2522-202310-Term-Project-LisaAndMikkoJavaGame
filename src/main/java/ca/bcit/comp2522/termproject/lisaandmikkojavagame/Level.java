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


public class Level {
    private ArrayList<Node> platforms;
    private Rectangle playerBox;
    private ArrayList<Monster> monsters;
    private ArrayList<PowerUp> powerUps;
    private int levelWidth;
    private int levelHeight;
    private ImageView portal;
    private int startX;
    private int startY;

    Level(int levelWidth, int levelHeight, int startX, int startY) {
        platforms = new ArrayList<>();
        monsters = new ArrayList<>();
        powerUps = new ArrayList<>();
        this.levelWidth = levelWidth;
        this.levelHeight = levelHeight;
        this.startX = startX;
        this.startY = startY;
    }

    // Checks if player has reached portal.
    private AnimationTimer checkCompletion = new AnimationTimer() {
        @Override
        public void handle(long l) {
            if (playerBox.getBoundsInParent().intersects(portal.getBoundsInParent())) {
                winPopup();
                checkCompletion.stop();
            }
        }
    };

    // popup for completion of the game
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

    public int getLevelWidth() {
        return levelWidth;
    }

    public int getLevelHeight() {
        return levelHeight;
    }

    // Add platform
    public void addPlatform(int xCoordinate, int yCoordinate, int width, int height, Color color) {
        Rectangle platform = new Rectangle(width, height, color);
        platform.setTranslateX(xCoordinate);
        platform.setTranslateY(yCoordinate);
        platform.setStroke(Color.BLACK);
        platforms.add(platform);
    }

    public ArrayList<Node> getPlatforms() {
        return platforms;
    }

    // Add monster
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
