package com.example.videocompresstrim.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.videocompresstrim.R;
import com.example.videocompresstrim.util.ReadExternalStoragePermission;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ReadExternalStoragePermission.isReadStoragePermissionGranted(this);
        showDialog();
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


    private void showDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        @SuppressLint("InflateParams")
        View mView = getLayoutInflater().inflate(R.layout.dialog_choose_video, null);
        LinearLayout openGallery = mView.findViewById(R.id.galleryLayoutID);
        LinearLayout openCustomGallery = mView.findViewById(R.id.customGalleryLayoutID);
        LinearLayout captureImage = mView.findViewById(R.id.captureVideoLayoutID);
        ImageView close = mView.findViewById(R.id.closeID);

        builder.setView(mView);
        final AlertDialog dialog = builder.create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        openGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, VideoFromGalleryActivity.class));

            }
        });


        openCustomGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ReadExternalStoragePermission.isReadStoragePermissionGranted(MainActivity.this)) {
                    startActivity(new Intent(MainActivity.this, GridVideoActivity.class));

                }
            }
        });

        captureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ReadExternalStoragePermission.isReadStoragePermissionGranted(MainActivity.this)) {
                    startActivity(new Intent(MainActivity.this, VideoRecoderActivity.class));
                }
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }


}
