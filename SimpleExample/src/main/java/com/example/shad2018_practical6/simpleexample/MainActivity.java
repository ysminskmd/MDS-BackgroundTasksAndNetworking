package com.example.shad2018_practical6.simpleexample;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private class UpdateImageBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(final Context context, final Intent intent) {
            String action = intent.getAction();
            Log.d(TAG, "UpdateImageBroadcastReceiver#onReceive() with action: " + action);
            if (ImageLoaderService.BROADCAST_ACTION_UPDATE_IMAGE.equals(action)) {
                final String imageName = intent.getStringExtra(ImageLoaderService.BROADCAST_PARAM_IMAGE);
                if (TextUtils.isEmpty(imageName) == false) {
                    final Bitmap bitmap = ImageSaver.getInstance().loadImage(getApplicationContext(), imageName);
                    final Drawable drawable = new BitmapDrawable(getResources(), bitmap);
                    setDrawable(drawable);
                }
            }

            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    private static final String TAG = "Shad";

    private View mRootLayout;
    private View mProgressBar;

    private UpdateImageBroadcastReceiver mUpdateImageBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRootLayout = findViewById(R.id.layout);
        mProgressBar = findViewById(R.id.progressBar);

        final FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Log.d(TAG, "Load image");
                mProgressBar.setVisibility(View.VISIBLE);
                JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
                if (jobScheduler != null) {
                    jobScheduler.schedule(
                            new JobInfo.Builder(ImageLoaderService.JOB_ID_LOAD_IMAGE,
                                    new ComponentName(getApplicationContext(), ImageLoaderService.class))
                                    .setOverrideDeadline(0L)
                                    .build()
                    );
                }
            }
        });

        mUpdateImageBroadcastReceiver = new UpdateImageBroadcastReceiver();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "MainActivity#onResume()");
        registerReceiver(mUpdateImageBroadcastReceiver,
                new IntentFilter(ImageLoaderService.BROADCAST_ACTION_UPDATE_IMAGE));
    }

    @Override
    protected void onPause() {
        unregisterReceiver(mUpdateImageBroadcastReceiver);
        Log.d(TAG, "MainActivity#onPause()");
        super.onPause();
    }

    private void setDrawable(Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mRootLayout.setBackground(drawable);
        } else {
            mRootLayout.setBackgroundDrawable(drawable);
        }

        mProgressBar.setVisibility(View.INVISIBLE);
    }
}
