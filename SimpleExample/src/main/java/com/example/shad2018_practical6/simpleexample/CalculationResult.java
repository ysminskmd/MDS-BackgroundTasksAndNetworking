package com.example.shad2018_practical6.simpleexample;

public class CalculationResult {
    private String mId;
    private String mResult;

    public CalculationResult(final String id, final String result) {
        mId = id;
        mResult = result;
    }

    public String getId() {
        return mId;
    }

    public void setId(final String id) {
        mId = id;
    }

    public String getResult() {
        return mResult;
    }

    public void setResult(final String result) {
        mResult = result;
    }
}
