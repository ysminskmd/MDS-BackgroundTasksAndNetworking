package com.example.shad.async;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;
    private HandlerThread mHandlerThread;
    private Handler mBgHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.main);
        Button toastButton = findViewById(R.id.showtoast);
        Button calculateButton = findViewById(R.id.calculate);

        mHandlerThread = new HandlerThread("Shad-calc");
        mHandlerThread.start();
        mBgHandler = new Handler(mHandlerThread.getLooper());

        toastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Toast button pressed", Toast.LENGTH_LONG).show();
            }
        });

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBgHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 50; i++) {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {

                            }
                        }
                        Log.i("Shad", "Calculation finished in thread: " + Thread.currentThread().getName());
                        mTextView.post(new Runnable() {
                            @Override
                            public void run() {
                                Log.i("Shad", "Update UI in thread: " + Thread.currentThread().getName());
                                mTextView.setText("Calculation finished");
                            }
                        });
                    }
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandlerThread.quit();
//        mHandlerThread.quitSafely();
    }
}
