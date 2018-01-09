package mgi.ivan_sison.camera.method;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;

import mgi.ivan_sison.camera.model.Images;

/**
 * Created by MGI-Ivan on 08/12/2017.
 */

public class ImageMethod {
    private static final ImageMethod ourInstance = new ImageMethod();

    private static Context mContext;
    private ArrayList<Images> images = new ArrayList();
    private ArrayList<Images> cache = new ArrayList();

    public static ImageMethod getInstance(Context context) {
        mContext = context;

        return ourInstance;
    }

    private ImageMethod() {

    }

    public ArrayList<Images> getImages() {
        images.clear();
        cache.clear();

        boolean isFolder = false;

        int int_position = 0;
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name;

        String absolutePathOfImage = null;
        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

        final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
        cursor = mContext.getContentResolver().query(uri, projection, null, null, orderBy + " DESC");

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);
            Log.e("Column", absolutePathOfImage);
            Log.e("Folder", cursor.getString(column_index_folder_name));

            for (int i = 0; i < cache.size(); i++) {
                if (cache.get(i).getImageFolder().equals(cursor.getString(column_index_folder_name))) {
                    isFolder = true;
                    int_position = i;
                    break;
                } else {
                    isFolder = false;
                }
            }

            if (isFolder) {

                ArrayList<String> al_path = new ArrayList<>();
                al_path.addAll(cache.get(int_position).getImagePath());
                al_path.add(absolutePathOfImage);
                cache.get(int_position).setImagePath(al_path);

            }
            else {
                ArrayList<String> al_path = new ArrayList<>();
                al_path.add(absolutePathOfImage);

                Images image = new Images();
                image.setImageFolder(cursor.getString(column_index_folder_name));
                image.setImagePath(al_path);

                cache.add(image);
            }
        }


        for (int i = 0; i < cache.size(); i++) {
            //Log.e("FOLDER", images.get(i).getImageFolder());
            for (int j = 0; j < cache.get(i).getImagePath().size(); j++) {
                //Log.e("FILE", images.get(i).getImagePath().get(j));
            }
        }

        addAllPhotosFolder();
        sortAlbum();

        return images;
    }

    private void addAllPhotosFolder() {
        ArrayList mImages = new ArrayList(cache.size());

        for (int i = 0; i < cache.size(); i++) {
            Images image = (Images) cache.get(i);

            for (int c = 0; c < image.getImagePath().size(); c++) {
                mImages.add(image.getImagePath().get(c));
            }
        }

        Images image = new Images();
        image.setImageFolder("All Photos");
        image.setImagePath(mImages);

        cache.add(image);
    }

    private void sortAlbum() {
        for (int i = cache.size() - 1; i > 0; i--) {
            images.add(cache.get(i));
        }
    }

    private boolean hasCameraFolder() {
        boolean hasFolder = false;

        for (int i = 0; i < cache.size(); i++) {
            if (cache.get(i).getImageFolder().equals("Camera")) {
                hasFolder = true;
            }
        }

        return hasFolder;
    }
}
