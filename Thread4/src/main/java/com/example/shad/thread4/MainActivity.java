package com.example.shad.thread4;

import android.os.Bundle;
import androidx.annotation.MainThread;
import androidx.annotation.WorkerThread;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shad.deadlock.R;

public class MainActivity extends AppCompatActivity {

    class ConcurrentJob {

        /**
         * https://developer.android.com/studio/write/annotations.html#thread-annotations
         */
        @WorkerThread
        void doBackgroundJob() {
            Log.d("Shad", "Background job starting");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {}

            Log.d("Shad", "Background job finished");
            final TextView tv = mTextView;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tv.setText(tv.getText() + "\n" + "Background job finished");
                }
            });
        }

        /**
         * https://developer.android.com/studio/write/annotations.html#thread-annotations
         */
        @MainThread
        void doMainThreadJob() {
            Log.d("Shad", "Main job starting");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {}

            Log.d("Shad", "Main thread job finished");
            mTextView.setText(mTextView.getText() + "\n" + "Main thread job finished");
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
//                    @MainThread
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
