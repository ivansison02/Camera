package mgi.ivan_sison.camera.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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
import mgi.ivan_sison.camera.model.Images;

/**
 * Created by MGI-Ivan on 08/12/2017.
 */

public class GalleryFolderAdapter extends RecyclerView.Adapter<GalleryFolderAdapter.ViewHolder> {

    Context context;
    ArrayList<Images> images = new ArrayList<>();

    public AlbumAdapter albumAdapter = null;

    public GalleryFolderAdapter(Context context, ArrayList<Images> images) {
        this.images = images;
        this.context = context;
    }

    @Override
    public GalleryFolderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_album_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final String albumTitle = images.get(position).getImageFolder();

        Picasso.with(context)
                .load(images.get(position).getImagePath().get(0))
                .into(holder.itemImage);

        holder.itemTitle.setText(albumTitle);
        holder.itemCount.setText(String.valueOf(images.get(position).getImagePath().size()));

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                albumAdapter.update(albumTitle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

            public View view;
            public TextView itemTitle, itemCount;
            public ImageView itemImage;

            public ViewHolder(View view) {
                super(view);

                this.view = view;
                itemTitle = (TextView) view.findViewById(R.id.album_item_title);
                itemCount = (TextView) view.findViewById(R.id.album_item_count);
                itemImage = (ImageView) view.findViewById(R.id.album_item_image);
            }
    }

    public interface AlbumAdapter {
        void update(String album);
    }

}