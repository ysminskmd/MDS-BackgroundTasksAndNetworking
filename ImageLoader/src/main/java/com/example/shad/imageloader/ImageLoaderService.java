package com.example.shad.imageloader;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;

public class ImageLoaderService extends JobService {

    private static final String TAG = "Shad";

    public static final int JOB_ID_LOAD_IMAGE = 21234;

    public static final String BROADCAST_ACTION_UPDATE_IMAGE =
            "com.example.shad2018_practical6.simpleexample.UPDATE_IMAGE";
    public static final String BROADCAST_PARAM_IMAGE = "com.example.shad2018_practical6.simpleexample.IMAGE";


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
    public boolean onStartJob(final JobParameters params) {
        int jobId = params.getJobId();
        Log.d(TAG, "ImageLoaderService#onStartJob() with jobId = " + jobId);
        if (jobId == JOB_ID_LOAD_IMAGE) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final String imageUrl = mImageLoader.getImageUrl();
                    if (TextUtils.isEmpty(imageUrl) == false) {
                        final Bitmap bitmap = mImageLoader.loadBitmap(imageUrl);
                        final String imageName = "myImage.png";
                        ImageSaver.getInstance().saveImage(getApplicationContext(), bitmap, imageName);

                        final Intent broadcastIntent = new Intent(BROADCAST_ACTION_UPDATE_IMAGE);
                        broadcastIntent.putExtra(BROADCAST_PARAM_IMAGE, imageName);
                        sendBroadcast(broadcastIntent);
                        jobFinished(params, false);
                    }
                }
            }).start();
            return true;
        }
        return false;
    }

    @Override
    public boolean onStopJob(final JobParameters params) {
        Log.d("Shad", "ImageLoaderService#onStopJob() with id = " + params.getJobId());
        return false;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "ImageLoaderService#onDestroy()");
        super.onDestroy();
    }
}
