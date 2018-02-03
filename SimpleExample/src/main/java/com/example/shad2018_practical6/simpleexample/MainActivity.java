package com.example.shad2018_practical6.simpleexample;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.MainThread;
import android.support.annotation.WorkerThread;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

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
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {}

                synchronized (mLock2) {
                    Log.d("Shad", "Background job finished");
                    final TextView tv = mTextView;
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            tv.setText("Background job finished");
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
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {}
            }

            synchronized (mLock1) {
                Log.d("Shad", "Main thread job finished");
                mTextView.setText("Main thread job finished");
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
        mConcurrentJob = new ConcurrentJob();

        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                mConcurrentJob.doBackgroundJob();
            }
        });
        mThread.start();

        mConcurrentJob.doMainThreadJob();
    }
}
