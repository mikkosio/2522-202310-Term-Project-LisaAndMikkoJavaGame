package ca.bcit.comp2522.termproject.lisaandmikkojavagame;

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

    Level() {
        platforms = new ArrayList<>();
        monsters = new ArrayList<>();
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

    public void placePlayer(int xCoordinate, int yCoordinate, int width, int height) {
        Rectangle player = new Rectangle(width, height);
        player.setTranslateX(xCoordinate);
        player.setTranslateY(yCoordinate);
        playerBox = player;
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
    }

}
