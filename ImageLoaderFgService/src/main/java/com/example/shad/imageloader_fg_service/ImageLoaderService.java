package com.example.shad.imageloader_fg_service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class ImageLoaderService extends Service {

    public static final int NOTIFICATION_ID = 42;
    public static final String CHANNEL_ID = "com.example.shad.imageloader.ImageLoaderService";

    private static final String TAG = "Shad";

    public static final String SERVICE_ACTION_LOAD_IMAGE = "com.example.shad.imageloader_fg_service.LOAD_IMAGE";
    public static final String BROADCAST_ACTION_UPDATE_IMAGE =
            "com.example.shad.imageloader_fg_service.UPDATE_IMAGE";
    public static final String BROADCAST_PARAM_IMAGE = "com.example.shad.imageloader_fg_service.IMAGE";


    private final ImageLoader mImageLoader;

    public ImageLoaderService() {
        mImageLoader = new ImageLoader();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "ImageLoaderService#onCreate()");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "ImageLoaderService#onBind()");
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(final Intent intent,
                              final int flags,
                              final int startId) {
        String action = intent.getAction();
        Log.d(TAG, "ImageLoaderService#onStartCommand() with action = " + action);

        createNotificationChannel();
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Foreground Service")
                .setContentText("I download images")
                .setSmallIcon(androidx.appcompat.R.drawable.abc_ic_star_black_16dp)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(NOTIFICATION_ID, notification);

        if (SERVICE_ACTION_LOAD_IMAGE.equals(action)) {
            load();
        }

        return START_NOT_STICKY;
    }

    private void load() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Bitmap bitmap = mImageLoader.loadBitmap("https://f1.upet.com/A_AW8y4KQDMH_M.jpg");
                final String imageName = "myImage.png";
                ImageSaver.getInstance().saveImage(getApplicationContext(), bitmap, imageName);

                final Intent intent = new Intent(BROADCAST_ACTION_UPDATE_IMAGE);
                intent.putExtra(BROADCAST_PARAM_IMAGE, imageName);
                sendBroadcast(intent);
            }
        }).start();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "MyService, onDestroy");
        super.onDestroy();
    }
}
