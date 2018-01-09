package mgi.ivan_sison.camera;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import mgi.ivan_sison.camera.adapter.GalleryFolderAdapter;
import mgi.ivan_sison.camera.method.ImageMethod;
import mgi.ivan_sison.camera.model.Images;

public class AlbumActivity extends AppCompatActivity implements GalleryFolderAdapter.AlbumAdapter {

    private ArrayList<Images> images = new ArrayList<>();

    RecyclerView recyclerView;

    GalleryFolderAdapter adapter;

    public static AlbumSelector selector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_album);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        images = images = ImageMethod.getInstance(this).getImages();

        recyclerView = (RecyclerView) findViewById(R.id.albumView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new GalleryFolderAdapter(this, images);
        adapter.albumAdapter = this;
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        View view = getWindow().getDecorView();

        /*if (hasFocus) {
            view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            );
        }*/

        WindowManager.LayoutParams params = getWindow().getAttributes();

        int width = getWindow().getAttributes().width;
        int height = getWindow().getAttributes().height;
        params.x = 0;
        params.height = height;
        params.width = width;
        params.y = getWindow().getAttributes().y + 500;

        Log.e("TAG", "" + getWindow().getAttributes().y);

        this.getWindow().setAttributes(params);

    }

    @Override
    public void update(String album) {
        selector.changeAlbum(album);
        this.finish();
    }

    public interface AlbumSelector {
        void changeAlbum(String album);
    }
}
