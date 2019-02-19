package com.example.shad.async;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    static Handler sHandler;

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
                final TextView tv = findViewById(R.id.main);
                final long startTime = System.currentTimeMillis();

                sHandler = new Handler();
                Thread thread = new Thread(new Runnable() {
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

                thread.start();
            }
        });
    }

    private int calculate() {
        int result = 0;
        for (int i = 1; i < 1000; i ++) {
            result ++;
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {

            }
        }
        return result;
    }
}
