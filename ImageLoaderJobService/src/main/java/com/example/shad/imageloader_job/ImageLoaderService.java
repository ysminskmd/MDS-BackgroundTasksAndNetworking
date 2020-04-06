package com.example.shad.imageloader_job;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;

public class ImageLoaderService extends JobService {

    private static final String TAG = "Shad";

    public static final int JOB_ID_LOAD_IMAGE = 21234;

    public static final String BROADCAST_ACTION_UPDATE_IMAGE =
            "com.example.shad.imageloader_job.UPDATE_IMAGE";
    public static final String BROADCAST_PARAM_IMAGE = "com.example.shad.imageloader_job.IMAGE";


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
                    final Bitmap bitmap = mImageLoader.loadBitmap("https://f1.upet.com/A_AW8y4KQDMH_M.jpg");
                    final String imageName = "myImage.png";
                    ImageSaver.getInstance().saveImage(getApplicationContext(), bitmap, imageName);

                    final Intent broadcastIntent = new Intent(BROADCAST_ACTION_UPDATE_IMAGE);
                    broadcastIntent.putExtra(BROADCAST_PARAM_IMAGE, imageName);
                    sendBroadcast(broadcastIntent);
                    jobFinished(params, false);
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
