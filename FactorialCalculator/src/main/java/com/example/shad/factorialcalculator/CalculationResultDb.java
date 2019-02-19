package com.example.shad.factorialcalculator;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {CalculationResult.class}, version = 1)
public abstract class CalculationResultDb extends RoomDatabase {
    public abstract CalculationResultDao calculationResultDao();
}
