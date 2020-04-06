package com.example.shad.factorialcalculator;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CalculationResultDao {

    @Insert
    public void insert(CalculationResult calculationResult);

    @Query("Select * from calculation_result")
    public CalculationResult[] loadAll();

    @Query("Select * from calculation_result")
    public LiveData<List<CalculationResult>> loadCalculationResultSync();
}
