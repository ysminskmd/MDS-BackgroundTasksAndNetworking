package com.example.shad.async;

import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.WorkerThread;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    class ConcurrentJob {

        Object mLock1 = new Object();
        Object mLock2 = new Object();

        /**
         * https://developer.android.com/studio/write/annotations.html#thread-annotations
         */
        @WorkerThread
        void doBackgroundJob() {
            synchronized (mLock1) {
                Log.d("Shad", "Background job acquire first lock");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {}

                synchronized (mLock2) {
                    Log.d("Shad", "Background job finished");
                    final TextView tv = mTextView;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv.setText(tv.getText() + "\n" + "Background job finished");
                        }
                    });
                }
            }
        }

        /**
         * https://developer.android.com/studio/write/annotations.html#thread-annotations
         */
        @MainThread
        void doMainThreadJob() {
            synchronized (mLock2) {
                Log.d("Shad", "Main job acquire second lock");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {}

                synchronized (mLock1) {
                    Log.d("Shad", "Main thread job finished");
                    mTextView.setText(mTextView.getText() + "\n" + "Main thread job finished");
                }
            }
        }
    }

    private TextView mTextView;
    private Thread mThread;
    private ConcurrentJob mConcurrentJob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.main);
        Button toastButton = findViewById(R.id.showtoast);
        Button calculateButton = findViewById(R.id.calculate);

        toastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Toast button pressed", Toast.LENGTH_LONG).show();
            }
        });

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mConcurrentJob = new ConcurrentJob();

                mThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mConcurrentJob.doBackgroundJob();
                    }
                });
                mThread.start();

                try {
                    Thread.sleep(100);
                } catch (Exception e) {}

                mConcurrentJob.doMainThreadJob();
            }
        });
    }
}
