package com.example.shad.calculator;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;
import android.util.Log;

public class CalculateService extends JobIntentService {

    private static final String TAG = "Shad";

    public static final int JOB_ID_CALCULATE = 21234;

    public static final String ACTION_CALCULATE = "com.example.shad.calculator.CALCULATE";

    static void enqueueWork(Context context, Intent work) {
        Log.d(TAG, "ImageLoaderService#enqueueWork() with intent = " + work);
        enqueueWork(context, CalculateService.class, JOB_ID_CALCULATE, work);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "ImageLoaderService#onCreate()");
    }

    @Override
    protected void onHandleWork(@NonNull final Intent intent) {
        String action = intent.getAction();
        Log.d(TAG, "ImageLoaderService#onHandleWork() with action = " + action);

        if (ACTION_CALCULATE.equals(action)) {
            for (int i = 0; i < 1000; i++) {
                if (i % 10 == 0) {
                    Log.i("Shad", "Processing " + i + "...");
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "ImageLoaderService#onDestroy()");
        super.onDestroy();
    }
}
