package com.example.shad.deadlock;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private final Object mLock1 = new Object();
    private final Object mLock2 = new Object();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.makeDeadlock).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeDeadlock();
            }
        });
    }

    private void makeDeadlock() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("Shad", "Background thread is trying to acquire mLock1");
                synchronized (mLock1) {
                    Log.i("Shad", "Background thread acquired mLock1");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) { }

                    Log.i("Shad", "Background thread is trying to acquire mLock2");
                    synchronized (mLock2) {
                        Log.i("Shad", "Background thread acquired mLock2");
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) { }
                    }
                }
            }
        }).start();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) { }
        Log.i("Shad", "Main thread is trying to acquire mLock2");
        synchronized (mLock2) {
            Log.i("Shad", "Main thread acquired mLock2");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) { }

            Log.i("Shad", "Main thread is trying to acquire mLock1");
            synchronized (mLock1) {
                Log.i("Shad", "Main thread acquired mLock1");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) { }
            }
        }
    }
}
