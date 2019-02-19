package com.example.shad.factorialcalculator;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button mServiceControlButton;
    private RecyclerView mRecyclerView;
    boolean isRunning = false;

    private CalculationResultAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mServiceControlButton = findViewById(R.id.btn_service_control);
        mRecyclerView = findViewById(R.id.lv_results);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new CalculationResultAdapter(new ArrayList<CalculationResult>());
        mRecyclerView.setAdapter(mAdapter);

        CalculationViewModel calculationViewModel = ViewModelProviders.of(this).get(CalculationViewModel.class);
        calculationViewModel.getCalculatingLiveData().observe(this, new Observer<List<CalculationResult>>() {
            @Override
            public void onChanged(@Nullable final List<CalculationResult> calculationResults) {
                mAdapter.setData(calculationResults);
                mAdapter.notifyDataSetChanged();
            }
        });

        mServiceControlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CalculationService.class);
                if (isRunning) {
                    isRunning = false;
                    mServiceControlButton.setText(getString(R.string.start_calculation));
                    stopService(intent);
                } else {
                    isRunning = true;
                    mServiceControlButton.setText(getString(R.string.stop_calculation));
                    startService(intent);
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        isRunning = false;
        stopService(new Intent(getApplicationContext(), CalculationService.class));
        mServiceControlButton.setText(getString(R.string.start_calculation));
    }
}