package com.example.shad.imageloader_work;

import androidx.lifecycle.Observer;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.work.Constraints;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Shad";

    private View mRootLayout;
    private View mProgressBar;

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

                OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(ImageLoaderWorker.class).build();
                WorkManager workManager = WorkManager.getInstance(getApplicationContext());
                workManager.enqueue(request);
                workManager.getWorkInfoByIdLiveData(request.getId()).observe(MainActivity.this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(@Nullable WorkInfo workInfo) {
                        Log.d(TAG, "Work status changed");
                        if (workInfo == null) {
                            return;
                        }
                        Log.d(TAG, "New status: " + workInfo.getState());
                        if (workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                            Log.d(TAG, "Work succeeded");
                            mProgressBar.setVisibility(View.INVISIBLE);
                            String imagePath = workInfo.getOutputData().getString(ImageLoaderWorker.PARAM_IMAGE);
                            if (TextUtils.isEmpty(imagePath) == false) {
                                final Bitmap bitmap = ImageSaver.getInstance().loadImage(getApplicationContext(), imagePath);
                                final Drawable drawable = new BitmapDrawable(getResources(), bitmap);
                                setDrawable(drawable);
                            }
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "MainActivity#onResume()");
    }

    @Override
    protected void onPause() {
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
