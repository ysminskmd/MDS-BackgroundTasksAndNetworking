package com.example.shad.factorialcalculator;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import java.util.List;

public class CalculationViewModel extends AndroidViewModel {

    @NonNull
    private LiveData<List<CalculationResult>> mCalculatingLiveData;

    public CalculationViewModel(@NonNull final Application application) {
        super(application);
        mCalculatingLiveData = CalculationResultDbHolder.getInstance().getDb(this.getApplication())
                .calculationResultDao().loadCalculationResultSync();
    }

    @NonNull
    public LiveData<List<CalculationResult>> getCalculatingLiveData() {
        return mCalculatingLiveData;
    }
}
