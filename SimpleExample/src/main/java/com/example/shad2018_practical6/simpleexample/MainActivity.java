package com.example.shad2018_practical6.simpleexample;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    static Thread sThread;
    static Handler sHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView tv = findViewById(R.id.main);
        final long startTime = System.currentTimeMillis();

        if (sThread == null) {
            sHandler = new Handler();
            sThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    final long result = calculate();
                    final long totalTime = System.currentTimeMillis() - startTime;
                    Log.i("Shad", "Calculate finished");
                    sHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Log.i("Shad", "Set value to tv = " + tv);
                            tv.setText(String.format("Calculate value %d in %d milliseconds", result, totalTime));
                        }
                    });
                }
            });

            sThread.start();
        }
    }

    private int calculate() {
        int result = 0;
        for (int i = 1; i < 1000; i ++) {
            result ++;
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {

            }
        }
        return result;
    }
}
