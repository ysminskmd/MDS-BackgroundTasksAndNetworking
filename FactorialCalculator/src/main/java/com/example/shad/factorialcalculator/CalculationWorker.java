package com.example.shad.factorialcalculator;

import android.content.Context;
import androidx.annotation.NonNull;
import android.util.Log;

import java.math.BigInteger;
import java.util.Random;

import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class CalculationWorker extends Worker {

    public CalculationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        CalculationResultDao dao = CalculationResultDbHolder.getInstance()
                .getDb(getApplicationContext()).calculationResultDao();
        while (!isStopped()) {
            int num = new Random().nextInt(1000);
            BigInteger result = factorial(num);
            String resultString = result.toString();
            CalculationResult calculationResult = new CalculationResult();
            calculationResult.result = resultString;

            Log.d("Shad", String.format("Insert value: %s", calculationResult.result));

            dao.insert(calculationResult);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                break;
            }
        }

        return Result.success();
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
