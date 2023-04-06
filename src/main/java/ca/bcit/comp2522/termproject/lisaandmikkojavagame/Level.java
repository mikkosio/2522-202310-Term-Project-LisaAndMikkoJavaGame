package ca.bcit.comp2522.termproject.lisaandmikkojavagame;

import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;


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
    private Level nextLevel;

    Level(int levelWidth, int levelHeight, int startX, int startY) {
        platforms = new ArrayList<>();
        monsters = new ArrayList<>();
        powerUps = new ArrayList<>();
        this.levelWidth = levelWidth;
        this.levelHeight = levelHeight;
        this.startX = startX;
        this.startY = startY;
    }

    public void setNextLevel(Level nextLevel) {
        this.nextLevel = nextLevel;
    }

    // Checks if player has reached portal.
    private AnimationTimer checkCompletion = new AnimationTimer() {
        @Override
        public void handle(long l) {
            if (playerBox.getBoundsInParent().intersects(portal.getBoundsInParent())) {
                if (nextLevel != null) {
                    System.out.println(nextLevel);
                } else {
                    System.out.println("you win");
                }
            }
        }
    };

    public int getLevelWidth() {
        return levelWidth;
    }

    public int getLevelHeight() {
        return levelHeight;
    }

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
