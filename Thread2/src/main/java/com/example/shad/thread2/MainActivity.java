package com.example.shad.thread2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button toastButton = findViewById(R.id.showtoast);
        Button calculateButton = findViewById(R.id.calculate);
        final TextView tv = findViewById(R.id.main);

        toastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Toast button pressed", Toast.LENGTH_LONG).show();
            }
        });

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        long startTime = System.currentTimeMillis();
                        final long result = calculate();
                        final long totalTime = System.currentTimeMillis() - startTime;

                        tv.setText(String.format("Calculate value %d in %d milliseconds", result, totalTime));
                    }
                }).start();
            }
        });
    }

    private int calculate() {
        int result = 0;
        for (int i = 1; i < 700; i ++) {
            result ++;
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {

            }
        }
        return result;
    }
}
