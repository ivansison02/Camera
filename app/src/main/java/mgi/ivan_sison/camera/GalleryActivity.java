package mgi.ivan_sison.camera;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import mgi.ivan_sison.camera.adapter.GalleryAdapter;
import mgi.ivan_sison.camera.adapter.GalleryFolderAdapter;
import mgi.ivan_sison.camera.adapter.GalleryImageAdapter;
import mgi.ivan_sison.camera.method.ImageMethod;
import mgi.ivan_sison.camera.model.Images;

public class GalleryActivity extends AppCompatActivity implements AlbumActivity.AlbumSelector {

    GridView imageView;
    GalleryImageAdapter adapter;
    private ArrayList<Images> images = new ArrayList<>();
    private static final int REQUEST_PERMISSIONS = 100;

    private GalleryFolderAdapter imageAdapter;
    private boolean boolean_folder;
    private int position = 0;

    private AlbumActivity albumActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        checkPermission();

        images = ImageMethod.getInstance(this).getImages();

        albumActivity = new AlbumActivity();
        albumActivity.selector = this;

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        ImageButton btnBack = (ImageButton) findViewById(R.id.gallery_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GalleryActivity.super.onBackPressed();
            }
        });

        ImageButton btnArrow = (ImageButton) findViewById(R.id.gallery_album_arrow);
        btnArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), albumActivity.getClass());
                startActivity(intent);
            }
        });

        TextView textAlbum = (TextView) findViewById(R.id.gallery_album) ;
        textAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), albumActivity.getClass());
                startActivity(intent);
            }
        });

        imageView = (GridView) findViewById(R.id.gallery_grid);

        position = 0;

        setAdapter();
    }

    private void setAdapter() {
        setFolder();

        adapter = new GalleryImageAdapter(this, images, position);
        imageView.setAdapter(adapter);
    }


    private void setFolder() {
        TextView folderTitle = (TextView) findViewById(R.id.gallery_album);
        folderTitle.setText(images.get(position).getImageFolder());
    }

    private boolean hasSdCard() {
        Boolean isSDPresent = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
        return isSDPresent;
    }

    private void checkPermission() {
        if ((ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            if ((ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE))) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);
            }
        }else {
            Log.e("Else","Else");
        }
    }

    /*public ArrayList<Images> getImages() {
        images.clear();

        int int_position = 0;
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name;

        String absolutePathOfImage = null;
        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

        final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
        cursor = getApplicationContext().getContentResolver().query(uri, projection, null, null, orderBy + " DESC");

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);
            //Log.e("Column", absolutePathOfImage);
            //Log.e("Folder", cursor.getString(column_index_folder_name));

            for (int i = 0; i < images.size(); i++) {
                if (images.get(i).getImageFolder().equals(cursor.getString(column_index_folder_name))) {
                    boolean_folder = true;
                    int_position = i;
                    break;
                } else {
                    boolean_folder = false;
                }
            }


            if (boolean_folder) {

                ArrayList<String> al_path = new ArrayList<>();
                al_path.addAll(images.get(int_position).getImagePath());
                al_path.add(absolutePathOfImage);
                images.get(int_position).setImagePath(al_path);

            }
            else {
                ArrayList<String> al_path = new ArrayList<>();
                al_path.add(absolutePathOfImage);

                Images image = new Images();
                image.setImageFolder(cursor.getString(column_index_folder_name));
                image.setImagePath(al_path);

                images.add(image);
            }
        }


        for (int i = 0; i < images.size(); i++) {
            //Log.e("FOLDER", images.get(i).getImageFolder());
            for (int j = 0; j < images.get(i).getImagePath().size(); j++) {
                //Log.e("FILE", images.get(i).getImagePath().get(j));
            }
        }

        return images;
    }*/

    /*private void addAllPhotosFolder() {
        ArrayList mImages = new ArrayList(images.size());

        for (int i = 0; i < images.size(); i++) {
            Images image = (Images) images.get(i);

            for (int c = 0; c < image.getImagePath().size(); c++) {
                mImages.add(image.getImagePath().get(c));
            }
        }

        Images image = new Images();
        image.setImageFolder("All Photos");
        image.setImagePath(mImages);

        images.add(image);
    }*/

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        View view = getWindow().getDecorView();

        if (hasFocus) {
            view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_PERMISSIONS: {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults.length > 0 && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        //getImages();
                    } else {
                        Toast.makeText(this, "This app requires to read memory permission!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }

    @Override
    public void changeAlbum(String album) {
        position = findAlbumIndex(album);

        setAdapter();
    }

    private int findAlbumIndex(String album) {
        int index = 0;

        for (int i = 0; i < images.size(); i++) {
            Images image = (Images) images.get(i);

            if (image.getImageFolder().equals(album)) {
                index = i;
            }
        }

        return index;
    }
}
