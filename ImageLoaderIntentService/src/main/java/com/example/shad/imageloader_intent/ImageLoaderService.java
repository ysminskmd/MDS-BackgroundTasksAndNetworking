package com.example.shad.imageloader_intent;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;

import androidx.annotation.Nullable;
import android.util.Log;

public class ImageLoaderService extends IntentService {

    private static final String TAG = "Shad";

    public static final String SERVICE_ACTION_LOAD_IMAGE = "com.example.shad.imageloader_intent.LOAD_IMAGE";
    public static final String BROADCAST_ACTION_UPDATE_IMAGE =
            "com.example.shad.imageloader_intent.UPDATE_IMAGE";
    public static final String BROADCAST_PARAM_IMAGE = "com.example.shad.imageloader_intent.IMAGE";


    private final ImageLoader mImageLoader;

    public ImageLoaderService() {
        super("ImageLoaderService");
        mImageLoader = new ImageLoader();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "ImageLoaderService#onCreate()");
    }

    @Override
    protected void onHandleIntent(@Nullable final Intent intent) {
        if (intent == null) {
            Log.d(TAG, "LoaderService#onHandleIntent() with null intent");
            return;
        }
        String action = intent.getAction();
        Log.d(TAG, "LoaderService#onHandleIntent() with action = " + action);
        if (SERVICE_ACTION_LOAD_IMAGE.equals(action)) {
            final Bitmap bitmap = mImageLoader.loadBitmap("https://f1.upet.com/A_AW8y4KQDMH_M.jpg");
            final String imageName = "myImage.png";
            ImageSaver.getInstance().saveImage(getApplicationContext(), bitmap, imageName);

            final Intent broadcastIntent = new Intent(BROADCAST_ACTION_UPDATE_IMAGE);
            broadcastIntent.putExtra(BROADCAST_PARAM_IMAGE, imageName);
            sendBroadcast(broadcastIntent);
        }
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "ImageLoaderService#onDestroy()");
        super.onDestroy();
    }
}
