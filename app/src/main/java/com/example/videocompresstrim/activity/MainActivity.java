package com.example.videocompresstrim.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.videocompresstrim.R;
import com.example.videocompresstrim.util.ReadExternalStoragePermission;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ReadExternalStoragePermission.isReadStoragePermissionGranted(this);
    }

    public void openGallery(View view) {
        startActivity(new Intent(MainActivity.this, VideoFromGalleryActivity.class));
    }

    public void openCustomGallery(View view) {
        if (ReadExternalStoragePermission.isReadStoragePermissionGranted(this)) {
            startActivity(new Intent(MainActivity.this, GridVideoActivity.class));

        }
    }

    public void captureVideo(View view) {
        if (ReadExternalStoragePermission.isReadStoragePermissionGranted(this)) {
            startActivity(new Intent(MainActivity.this, VideoRecoderActivity.class));
        }
    }


}
