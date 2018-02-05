package com.example.shad2018_practical6.simpleexample;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "calculation_result")
public class CalculationResult {
    @PrimaryKey(autoGenerate = true)
    public Integer id;
    @ColumnInfo(name = "result")
    public String result;
}
