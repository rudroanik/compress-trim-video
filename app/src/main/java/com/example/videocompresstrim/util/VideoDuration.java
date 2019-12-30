package com.example.videocompresstrim.util;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

public class VideoDuration {
    public static long getDuration(Activity activity, Uri uri) {
        long duration = 0L;
        Cursor cursor = MediaStore.Video.query(activity.getContentResolver(), uri,
                new String[]{MediaStore.Video.VideoColumns.DURATION});
        if (cursor != null) {
            cursor.moveToFirst();
            duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.VideoColumns.DURATION));
            cursor.close();
        }

        return duration;
    }

    public static String convertMillieToHMmSs(long millie) {
        long seconds = (millie / 1000);
        long second = seconds % 60;
        long minute = (seconds / 60) % 60;
        long hour = (seconds / (60 * 60)) % 24;

        if (hour > 0) {
            return String.format("%02d:%02d:%02d", hour, minute, second);
        } else {
            return String.format("%02d:%02d", minute, second);
        }

    }
}
