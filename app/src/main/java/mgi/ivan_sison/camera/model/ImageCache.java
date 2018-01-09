package mgi.ivan_sison.camera.model;

import java.util.ArrayList;

/**
 * Created by MGI-Ivan on 08/12/2017.
 */

public class ImageCache {

    String imageFolder;
    String imagePath;

    public ImageCache(String imageFolder, String imagePath) {
        this.imageFolder = imageFolder;
        this.imagePath = imagePath;
    }

    public String getImageFolder() {
        return imageFolder;
    }

    public void setImageFolder(String imageFolder) {
        this.imageFolder = imageFolder;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }


}
