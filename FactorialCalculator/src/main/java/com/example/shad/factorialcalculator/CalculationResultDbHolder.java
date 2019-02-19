package com.example.shad.factorialcalculator;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.NonNull;

class CalculationResultDbHolder {

    private static final CalculationResultDbHolder ourInstance = new CalculationResultDbHolder();

    static CalculationResultDbHolder getInstance() {
        return ourInstance;
    }

    private static final String DATABASE_NAME = "main";

    private volatile CalculationResultDb mCalculationResultDb;

    private CalculationResultDbHolder() {
    }

    public CalculationResultDb getDb(@NonNull Context context) {
        if (mCalculationResultDb == null) {
            synchronized (this) {
                if (mCalculationResultDb == null) {
                    mCalculationResultDb =
                            Room.databaseBuilder(context, CalculationResultDb.class, DATABASE_NAME).build();
                }
            }
        }
        return mCalculationResultDb;
    }
}
