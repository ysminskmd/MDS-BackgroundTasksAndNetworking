package com.example.shad.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;

import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class ImageLoaderWorker extends Worker {

    private static final String TAG = "Shad";

    public static final String PARAM_IMAGE = "com.example.shad2018_practical6.simpleexample.IMAGE";

    private final ImageLoader mImageLoader;

    public ImageLoaderWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        mImageLoader = new ImageLoader();
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, "Start background work");
        final Bitmap bitmap = mImageLoader.loadBitmap("https://f1.upet.com/A_AW8y4KQDMH_M.jpg");
        final String imageName = "myImage.png";
        ImageSaver.getInstance().saveImage(getApplicationContext(), bitmap, imageName);
        return Result.success(new Data.Builder().putString(PARAM_IMAGE, imageName).build());
    }
}
