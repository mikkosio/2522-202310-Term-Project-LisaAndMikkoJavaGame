package ca.bcit.comp2522.termproject.lisaandmikkojavagame;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Level {
    private ArrayList<Node> platforms;

    Level() {
        platforms = new ArrayList<>();
    }

    public void addPlatform(int xCoordinate, int yCoordinate, int width, int height, Color color) {
        Rectangle platform = new Rectangle(width, height, color);
        platform.setLayoutX(xCoordinate);
        platform.setLayoutY(yCoordinate);
        platform.setStroke(Color.BLACK);
        platforms.add(platform);
    }

    public ArrayList<Node> getPlatforms() {
        return platforms;
    }

    public void fillScene(AnchorPane scene) {
        for (Node platform : platforms) {
            scene.getChildren().add(platform);
        }
    }

}
