package com.example.shad.factorialcalculator;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "calculation_result")
public class CalculationResult {
    @PrimaryKey(autoGenerate = true)
    public Integer id;
    @ColumnInfo(name = "result")
    public String result;
}
