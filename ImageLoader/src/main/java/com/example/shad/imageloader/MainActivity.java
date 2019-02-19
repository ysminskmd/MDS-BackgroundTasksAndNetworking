package com.example.shad.imageloader;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private class ImageLoaderAsyncTask extends AsyncTask<Void, Void, Drawable> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Drawable doInBackground(final Void... voids) {
            return loadImage();
        }

        @Override
        protected void onPostExecute(final Drawable drawable) {
            super.onPostExecute(drawable);
            setDrawable(drawable);
        }
    }

    private View mRootLayout;
    private View mProgressBar;
    private ImageLoader mImageLoader;

    private AsyncTask<Void, Void, Drawable> mDrawableLoaderAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mImageLoader = new ImageLoader();
        mRootLayout = findViewById(R.id.layout);
        mProgressBar = findViewById(R.id.progressBar);

        final FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                mDrawableLoaderAsyncTask = new ImageLoaderAsyncTask();
                mDrawableLoaderAsyncTask.execute();
            }
        });
    }

    @Nullable
    private Drawable loadImage() {
        final Bitmap bitmap = mImageLoader.loadBitmap("https://f1.upet.com/A_AW8y4KQDMH_M.jpg");
        return new BitmapDrawable(getResources(), bitmap);
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
