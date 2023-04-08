package ca.bcit.comp2522.termproject.lisaandmikkojavagame;

import javafx.scene.image.ImageView;

/**
 * Portal.
 *
 * @author Mikko Sio
 * @author Lisa Jung
 * @version April 6, 2023
 */
public class Portal {
    private final ImageView portalImage;

    /**
     * Constructor for Portal class.
     * @param portalImage Image of portal.
     */
    public Portal(final ImageView portalImage) {
        this.portalImage = portalImage;
    }

    /**
     * Get portal's image.
     * @return Image of portal.
     */
    public ImageView getPortalImage() {
        return portalImage;
    }
}
