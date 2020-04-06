package com.example.shad.imageloader_okhttp_async;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity {

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
        fab.setOnClickListener(v -> {
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder().url("https://f1.upet.com/A_AW8y4KQDMH_M.jpg").build();
            mProgressBar.setVisibility(VISIBLE);
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    mProgressBar.setVisibility(View.INVISIBLE);
                    Log.e("Shad", "Error during bitmap loading", e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    mProgressBar.setVisibility(View.INVISIBLE);
                    ResponseBody responseBody = response.body();
                    if (responseBody != null) {
                        byte[] bitmapBytes = responseBody.bytes();
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
                        runOnUiThread(() -> setDrawable(new BitmapDrawable(getResources(), bitmap)));
                    }
                }
            });
        });
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
