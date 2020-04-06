package com.example.shad.factorialcalculator;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    private CalculationResultAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private final String CALCULATION_TASK_TAG = "Calculation";

    private WorkManager mWorkManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startBtn = findViewById(R.id.btnStart);
        Button stopBtn = findViewById(R.id.btnStop);
        mRecyclerView = findViewById(R.id.lv_results);

        mWorkManager = WorkManager.getInstance(this);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new CalculationResultAdapter(new ArrayList<CalculationResult>());
        mRecyclerView.setAdapter(mAdapter);

        CalculationViewModel calculationViewModel = new ViewModelProvider(this).get(CalculationViewModel.class);
        calculationViewModel.getCalculatingLiveData().observe(this, new Observer<List<CalculationResult>>() {
            @Override
            public void onChanged(@Nullable final List<CalculationResult> calculationResults) {
                List<CalculationResult> lastItem = new ArrayList<>();
                if (calculationResults != null && !calculationResults.isEmpty()) {
                    lastItem = Collections.singletonList(calculationResults.get(calculationResults.size() - 1));
                }
                mAdapter.setData(lastItem);
                mAdapter.notifyDataSetChanged();
            }
        });

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWorkManager.enqueue(
                        new OneTimeWorkRequest.Builder(CalculationWorker.class)
                                .addTag(CALCULATION_TASK_TAG)
                                .build()
                );
//                Intent intent = new Intent(getApplicationContext(), CalculationService.class);
//                startService(intent);
            }
        });
        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopTaskWorkManager();
//                stopTaskService();
            }
        });
    }

    private void stopTaskWorkManager() {
        mWorkManager.cancelAllWorkByTag(CALCULATION_TASK_TAG);
    }

    private void stopTaskService() {
        stopService(new Intent(getApplicationContext(), CalculationService.class));
    }
}