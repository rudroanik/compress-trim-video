package com.example.videocompresstrim.activity;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.videocompresstrim.R;
import com.example.videocompresstrim.adapter.GridVideoAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

public class GridVideoActivity extends AppCompatActivity {

    private ArrayList<String> videos;
    GridVideoAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_video);
        GridView galleryVideo = findViewById(R.id.gridViewID);
        videos = new ArrayList<>();

        videos = getAllMedia();
        adapter = new GridVideoAdapter(GridVideoActivity.this, videos);
        galleryVideo.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        galleryVideo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               /* Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(videos.get(position)));
                startActivity(intent);*/

                startActivity(new Intent(GridVideoActivity.this,VideoFromGalleryActivity.class)
                        .putExtra("videoUrl",videos.get(position))
                .putExtra("fromGridVideo",true));
            }
        });

    }

    public ArrayList<String> getAllMedia() {
        HashSet<String> videoItemHashSet = new HashSet<>();
        final String column = "_data";

        Cursor cursor = getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        try {
            if (cursor != null) {
                cursor.moveToFirst();
            }
            if (cursor != null) {
                do {
                    videoItemHashSet.add((cursor.getString(cursor.getColumnIndexOrThrow(column))));
                } while (cursor.moveToNext());
            }

            if (cursor != null) {
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>(videoItemHashSet);
    }
}
