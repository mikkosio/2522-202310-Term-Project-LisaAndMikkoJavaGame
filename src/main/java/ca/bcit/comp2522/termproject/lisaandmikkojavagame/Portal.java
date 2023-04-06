package ca.bcit.comp2522.termproject.lisaandmikkojavagame;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Portal {
    private ImageView portalImage;

    public Portal(ImageView portalImage) {
        this.portalImage = portalImage;
    }

    public ImageView getPortalImage() {
        return portalImage;
    }
}
