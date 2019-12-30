package com.example.videocompresstrim.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.videocompresstrim.R;

import java.util.ArrayList;

public class GridVideoAdapter extends BaseAdapter {

    private Activity context;
    private ArrayList<String> videos;

    public GridVideoAdapter(Activity localContext, ArrayList<String> videos) {
        context = localContext;
        this.videos = videos;


    }

    public int getCount() {
        return videos.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView,
                        ViewGroup parent) {

        String image = videos.get(position);
        ImageView imageView;


        View view = LayoutInflater.from(context).inflate(R.layout.item_grid_video, parent, false);

        imageView = view.findViewById(R.id.videoThumbnailIVID);


        Glide.with(context).load(image)
                .placeholder(R.drawable.no_video).centerCrop()
                .into(imageView);


        return view;
    }

}
