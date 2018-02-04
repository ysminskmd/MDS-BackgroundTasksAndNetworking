package com.example.shad2018_practical6.simpleexample;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.math.BigInteger;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CalculationService extends Service {

    private class CalculationTask implements Runnable {

        @Override
        public void run() {
            while (true) {
                int num = new Random().nextInt(1000);
                BigInteger result = factorial(num);
                String resultString = result.toString();
                ContentValues cv = new ContentValues();
                cv.put(ResultsContract.Columns.RESULT, resultString);
                Log.d("Shad",
                        String.format("Insert into %s values: %s",
                                ResultsContract.RESULTS_URI.toString(),
                                cv.toString()));
                getContentResolver().insert(ResultsContract.RESULTS_URI, cv);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }

        private BigInteger factorial(int num) {
            BigInteger result = BigInteger.ONE;
            if (num > 0) {
                for (int i = 1; i <= num; i ++) {
                    result = result.multiply(BigInteger.valueOf(i));
                }
            }

            return result;
        }

    }

    private ExecutorService mExecutor;

    public CalculationService() {
        mExecutor = Executors.newSingleThreadExecutor();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        mExecutor.execute(new CalculationTask());
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mExecutor.shutdownNow();
    }
}
