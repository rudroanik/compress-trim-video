package com.example.videocompresstrim.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trimcompress.FileUtils;
import com.example.videocompresstrim.R;
import com.example.videocompresstrim.util.FilePath;
import com.example.videocompresstrim.util.VideoDuration;

public class VideoFromGalleryActivity extends AppCompatActivity {

    private VideoView videoView;
    private Uri uri;
    private Button trimButton;
    boolean isLessThenTenSecond = false;
    String tmpPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_from_gallery);
        videoView = findViewById(R.id.GalleryViewVideoID);
        trimButton = findViewById(R.id.trimBtnID);

        if (getIntent().getBooleanExtra("fromGridVideo",false) == true){
            videoView.setVideoURI(Uri.parse(getIntent().getStringExtra("videoUrl")));
            videoView.start();
            trimButton.setVisibility(View.GONE);
        }else {
            try {
                Intent intent = new Intent();
                intent.setTypeAndNormalize("video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, "Select Video"), 2);
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }


        trimButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLessThenTenSecond) {
                    Toast.makeText(VideoFromGalleryActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();

                } else {
                    startActivity(new Intent(VideoFromGalleryActivity.this, TrimActivity.class).putExtra("video", FileUtils.getPath(VideoFromGalleryActivity.this, uri)));

                }
            }
        });
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK) {
            if (data != null) {
                uri = data.getData();

                videoView.setVideoURI(data.getData());
                videoView.start();
                tmpPath = FilePath.getPath(this, uri);

                //Video duration in millisecond
                MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                retriever.setDataSource(VideoFromGalleryActivity.this, data.getData());
                String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
                long timeInMillisec = Long.parseLong(time );
                Log.d("Duration", "onActivityResult: "+timeInMillisec);

                if (timeInMillisec > 10000) {
                    isLessThenTenSecond = false;

                } else {

                    trimButton.setVisibility(View.VISIBLE);
                    isLessThenTenSecond = true;
                    trimButton.setText("Upload Video");


                }
            }
        }

    }
}
