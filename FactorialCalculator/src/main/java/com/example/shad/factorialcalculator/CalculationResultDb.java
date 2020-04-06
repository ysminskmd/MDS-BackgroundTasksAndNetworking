package com.example.shad.factorialcalculator;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {CalculationResult.class}, version = 1)
public abstract class CalculationResultDb extends RoomDatabase {
    public abstract CalculationResultDao calculationResultDao();
}
