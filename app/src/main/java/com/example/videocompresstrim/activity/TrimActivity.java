package com.example.videocompresstrim.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trimcompress.K4LVideoTrimmer;
import com.example.trimcompress.SiliCompressor;
import com.example.trimcompress.interfaces.OnK4LVideoListener;
import com.example.trimcompress.interfaces.OnTrimVideoListener;
import com.example.videocompresstrim.R;

import java.net.URISyntaxException;

public class TrimActivity extends AppCompatActivity implements OnTrimVideoListener, OnK4LVideoListener {

    private K4LVideoTrimmer videoTrimmer;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trim);
        videoTrimmer = findViewById(R.id.timeLine);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Trimming in Progress");
        if (getIntent() != null) {

            String videoPath = getIntent().getExtras().getString("video");

            videoTrimmer.setMaxDuration(1000);
            videoTrimmer.setOnTrimVideoListener(this);
            videoTrimmer.setOnK4LVideoListener(this);
            videoTrimmer.setVideoURI(Uri.parse(videoPath));
            videoTrimmer.setVideoInformationVisibility(true);
        }
    }

    @Override
    public void onVideoPrepared() {

    }

    @Override
    public void onTrimStarted() {
        progressDialog.show();
    }
    @Override
    public void getResult(Uri uri) {

        new VideoCompressAsyncTask().execute(uri.getPath(), getExternalFilesDir(null).getAbsolutePath());
    }
    @Override
    public void cancelAction() {
        videoTrimmer.destroy();
        finish();

    }

    @Override
    public void onError(String message) {

    }

    @SuppressLint("StaticFieldLeak")
    class VideoCompressAsyncTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... paths) {
            String filePath = null;
            try {

                filePath = SiliCompressor.with(TrimActivity.this).compressVideo(paths[0], paths[1]);

            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            return filePath;
        }

        @Override
        protected void onPostExecute(final String compressedFilePath) {
            super.onPostExecute(compressedFilePath);
            progressDialog.dismiss();
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(compressedFilePath));
            intent.setDataAndType(Uri.parse(compressedFilePath), "video/mp4");
            startActivity(intent);
            finish();
        }

    }

}
