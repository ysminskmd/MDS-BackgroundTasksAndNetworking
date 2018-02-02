package com.example.shad2018_practical6.simpleexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        long startTime = System.currentTimeMillis();
        long result = calculate();
        long totalTime = System.currentTimeMillis() - startTime;

        TextView tv = findViewById(R.id.main);
        tv.setText(String.format("Calculate value %d in %d milliseconds", result, totalTime));
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
