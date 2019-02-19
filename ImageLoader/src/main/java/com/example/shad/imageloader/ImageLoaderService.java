package com.example.shad.imageloader;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class ImageLoaderService extends IntentService {

    private static final String TAG = "Shad";

    public static final String SERVICE_ACTION_LOAD_IMAGE = "com.example.shad2018_practical6.simpleexample.LOAD_IMAGE";
    public static final String BROADCAST_ACTION_UPDATE_IMAGE =
            "com.example.shad2018_practical6.simpleexample.UPDATE_IMAGE";
    public static final String BROADCAST_PARAM_IMAGE = "com.example.shad2018_practical6.simpleexample.IMAGE";


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
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "ImageLoaderService#onBind()");
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    protected void onHandleIntent(@Nullable final Intent intent) {
        String action = intent.getAction();
        Log.d("Shad", "LoaderService#onHandleIntent() with action = " + action);
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
