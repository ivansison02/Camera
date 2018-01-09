package mgi.ivan_sison.camera.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import mgi.ivan_sison.camera.R;
import mgi.ivan_sison.camera.model.ImageCache;
import mgi.ivan_sison.camera.model.Images;

/**
 * Created by MGI-Ivan on 08/12/2017.
 */

public class GalleryImageAdapter extends ArrayAdapter<Images> {

    Context context;
    ArrayList<Images> images = new ArrayList<>();
    ArrayList<ImageCache> cache = new ArrayList<>();
    int int_position;

    public GalleryImageAdapter(Context context, ArrayList<Images> images, int int_position) {
        super(context, R.layout.layout_image_item, images);
        this.images = images;
        this.context = context;
        this.int_position = int_position;
    }

    @Override
    public int getCount() {
        return images.get(int_position).getImagePath().size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        if (images.get(int_position).getImagePath().size() > 0) {
            return images.get(int_position).getImagePath().size();
        } else {
            return 1;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            final Images image = (Images) images.get(int_position);

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_image_item, parent, false);

            final ImageView itemImage, itemHint;

            itemImage = (ImageView) convertView.findViewById(R.id.image_item);
            itemHint = (ImageView) convertView.findViewById(R.id.image_hint);

            final String imagePath = "file://" + images.get(int_position).getImagePath().get(position);

            Picasso.with(context).load(imagePath)
                    .fit()
                    .centerInside()
                    .into(itemImage);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onCache(imagePath)) {
                        Picasso.with(context).load(R.drawable.camera_capture)
                                .into(itemHint);

                        cache.remove(onCacheIndex(imagePath));
                    }
                    else {
                        Picasso.with(context).load(R.drawable.image_item_check)
                                .into(itemHint);

                        cache.add(new ImageCache(images.get(int_position).getImageFolder(), imagePath));
                    }
                }
            });
        }

        return convertView;

    }

    private boolean onCache(String imagePath) {
        boolean alreadyOnCache = false;

        for (int i = 0; i < cache.size(); i++) {
            ImageCache image = (ImageCache) cache.get(i);

            if (image.getImagePath().equals(imagePath)) {
                alreadyOnCache = true;
                break;
            }
        }

        return alreadyOnCache;
    }

    private int onCacheIndex(String imagePath) {
        int imageIndex = 0;

        for (int i = 0; i < cache.size(); i++) {
            ImageCache image = (ImageCache) cache.get(i);

            if (image.getImagePath().equals(imagePath)) {
                imageIndex = i;
                break;
            }
        }

        return imageIndex;
    }
}