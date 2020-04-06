package com.example.shad.imageloader_fg_service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private class UpdateImageBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(final Context context, final Intent intent) {
            String action = intent.getAction();
            Log.d(TAG, "UpdateImageBroadcastReceiver#onReceive() with action");
            if (ImageLoaderService.BROADCAST_ACTION_UPDATE_IMAGE.equals(action)) {
                final String imageName = intent.getStringExtra(ImageLoaderService.BROADCAST_PARAM_IMAGE);
                if (TextUtils.isEmpty(imageName) == false) {
                    final Bitmap bitmap = ImageSaver.getInstance().loadImage(getApplicationContext(), imageName);
                    final Drawable drawable = new BitmapDrawable(getResources(), bitmap);
                    setDrawable(drawable);
                }
            }
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
                Intent intent = new Intent(MainActivity.this, ImageLoaderService.class)
                        .setAction(ImageLoaderService.SERVICE_ACTION_LOAD_IMAGE);
                startService(intent);
            }
        });

        mUpdateImageBroadcastReceiver = new UpdateImageBroadcastReceiver();
    }

    @Override
    protected void onDestroy() {
        Log.i("Shad", "Destroy service from onDestroy: " + stopService(new Intent(this, ImageLoaderService.class)));
        super.onDestroy();
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
