package com.example.videocompresstrim.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.provider.MediaStore;
import android.util.Size;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.trimcompress.SiliCompressor;
import com.example.videocompresstrim.R;
import com.example.videocompresstrim.util.FilePath;
import com.example.videocompresstrim.util.VideoDuration;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;


public class VideoRecoderActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView textView;
    private RelativeLayout relativeLayout;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_recoder);
        imageView = findViewById(R.id.videoThumbnailIVID);
        textView = findViewById(R.id.durationTVID);
        relativeLayout = findViewById(R.id.imageThumLayoutID);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(null);
        progressDialog.setCancelable(false);


    }

    public void captureVideo(View view) {
        Intent captureVideoIntent = new Intent(android.provider.MediaStore.ACTION_VIDEO_CAPTURE);
        captureVideoIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 10);
        captureVideoIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        startActivityForResult(captureVideoIntent, 1);
    }

    public void playVideo(View view) {
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();

                String realPath = FilePath.getPath(this, uri);

                relativeLayout.setVisibility(View.VISIBLE);
                Glide.with(this).load(uri).placeholder(R.drawable.no_video).into(imageView);


                textView.setText(VideoDuration.convertMillieToHMmSs(VideoDuration.getDuration(this, uri)));
                progressDialog.setMessage("Please wait");
                progressDialog.show();
                new VideoCompressor().execute(realPath, getExternalFilesDir(null).getAbsolutePath());

            }

        }
    }

    @SuppressLint("StaticFieldLeak")
    private class VideoCompressor extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... paths) {
            String filePath = null;
            try {

                //  MediaController.getInstance().convertVideo(realPath,new File(Environment.getExternalStorageDirectory().toString()));
                //or
                filePath = SiliCompressor.with(VideoRecoderActivity.this).compressVideo(paths[0], paths[1]);

            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            return filePath;
        }


        @Override
        protected void onPostExecute(final String compressedFilePath) {
            super.onPostExecute(compressedFilePath);
            progressDialog.dismiss();
            Toast.makeText(VideoRecoderActivity.this, "Compressed Successfull", Toast.LENGTH_SHORT).show();
        }
    }

}
