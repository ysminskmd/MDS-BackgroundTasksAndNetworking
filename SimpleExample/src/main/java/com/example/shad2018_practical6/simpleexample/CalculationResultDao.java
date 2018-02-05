package com.example.shad2018_practical6.simpleexample;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

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
