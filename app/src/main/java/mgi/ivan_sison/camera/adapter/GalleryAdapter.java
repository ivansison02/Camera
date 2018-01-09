package mgi.ivan_sison.camera.adapter;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import mgi.ivan_sison.camera.R;

/**
 * Created by MGI-Ivan on 07/12/2017.
 */

public class GalleryAdapter extends BaseAdapter {

    private Context context;
    private ArrayList items;

    public GalleryAdapter(Context context, ArrayList<File> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.layout_image_item, parent);

        ImageView imageView = (ImageView) convertView.findViewById(R.id.image_item);

        Picasso.with(context).load(Uri.parse(items.get(position).toString())).into(imageView);

        Log.e("GalleryAdapter", items.get(position).toString());

        return convertView;
    }
}
