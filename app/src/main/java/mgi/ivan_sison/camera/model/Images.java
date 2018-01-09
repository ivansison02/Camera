package mgi.ivan_sison.camera.model;

import java.util.ArrayList;

/**
 * Created by MGI-Ivan on 07/12/2017.
 */

public class Images {

    String imageFolder;
    ArrayList<String> imagePath;

    public String getImageFolder() {
        return imageFolder;
    }

    public void setImageFolder(String imageFolder) {
        this.imageFolder = imageFolder;
    }

    public ArrayList<String> getImagePath() {
        return imagePath;
    }

    public void setImagePath(ArrayList<String> imagePath) {
        this.imagePath = imagePath;
    }

}
