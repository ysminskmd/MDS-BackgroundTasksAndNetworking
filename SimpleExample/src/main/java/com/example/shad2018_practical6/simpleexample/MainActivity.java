package com.example.shad2018_practical6.simpleexample;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.WorkerThread;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Shad";

    class CalculationAsyncTask extends AsyncTask<Integer, Integer, Integer> {

        private int mResult;
        private StringBuilder mStringBuilder = new StringBuilder();

        @Override
        @MainThread
        protected void onPreExecute() {
            Log.i(TAG, "CalculationAsyncTask#onPreExecute()");
            mStringBuilder.append("Prepare calculation").append("\n");
            mTv.setText(mStringBuilder.toString());
        }

        @Override
        @WorkerThread
        protected Integer doInBackground(Integer... startValue) {
            Log.i(TAG, String.format("CalculationAsyncTask#doInBackground(%s)", Arrays.asList(startValue)));
            mResult = startValue[0];
            for (int i = 1; i < 1000; i ++) {
                if (isCancelled() == false) {
                    mResult ++;
                    if (i % 100 == 0) {
                        publishProgress(i/10);
                    }
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {

                    }
                }
            }
            return mResult;
        }

        @MainThread
        @Override
        protected void onPostExecute(final Integer integer) {
            super.onPostExecute(integer);
            Log.i(TAG, "CalculationAsyncTask#onPostExecute");
            mStringBuilder.append("Calculation finished with result = ").append(integer);
            mTv.setText(mStringBuilder.toString());
        }

        @MainThread
        @Override
        protected void onProgressUpdate(final Integer... values) {
            super.onProgressUpdate(values);
            Log.i(TAG, String.format("CalculationAsyncTask#onProgressUpdate(%s)", Arrays.toString(values)));
            int progress = values[0];
            mStringBuilder.append("CurrentProgress: ").append(progress).append("%").append("\n");
            mTv.setText(mStringBuilder.toString());
        }

        @MainThread
        @Override
        protected void onCancelled(final Integer integer) {
            super.onCancelled(integer);
            Log.i(TAG, "CalculationAsyncTask#onCanceled");
            mStringBuilder.append("Canceled calculation with result: ").append(integer);
            mTv.setText(mStringBuilder.toString());
        }
    }

    private TextView mTv;
    private CalculationAsyncTask mCalculationAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTv = findViewById(R.id.main);
        mCalculationAsyncTask = new CalculationAsyncTask();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mCalculationAsyncTask.execute(1000);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mCalculationAsyncTask.cancel(true);
    }
}
