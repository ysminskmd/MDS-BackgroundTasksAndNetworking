package com.example.shad.imageloader_service;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.IBinder;
import android.util.Log;

public class ImageLoaderService extends Service {

    private static final String TAG = "Shad";

    public static final String SERVICE_ACTION_LOAD_IMAGE = "com.example.shad.imageloader_service.LOAD_IMAGE";
    public static final String BROADCAST_ACTION_UPDATE_IMAGE =
            "com.example.shad.imageloader_service.UPDATE_IMAGE";
    public static final String BROADCAST_PARAM_IMAGE = "com.example.shad.imageloader_service.IMAGE";


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
        if (SERVICE_ACTION_LOAD_IMAGE.equals(action)) {
            load(startId);
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private void load(final int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Bitmap bitmap = mImageLoader.loadBitmap("https://f1.upet.com/A_AW8y4KQDMH_M.jpg");
                final String imageName = "myImage.png";
                ImageSaver.getInstance().saveImage(getApplicationContext(), bitmap, imageName);

                final Intent intent = new Intent(BROADCAST_ACTION_UPDATE_IMAGE);
                intent.putExtra(BROADCAST_PARAM_IMAGE, imageName);
                sendBroadcast(intent);
                stopSelf(startId);
            }
        }).start();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "ImageLoaderService#onDestroy");
        super.onDestroy();
    }
}
