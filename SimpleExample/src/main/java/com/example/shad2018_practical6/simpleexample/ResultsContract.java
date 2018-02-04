package com.example.shad2018_practical6.simpleexample;

import android.net.Uri;
import android.provider.BaseColumns;

public interface ResultsContract {

    static final String TABLE_NAME = "records";

    static interface Columns extends BaseColumns {
        static final String RESULT = "result";
    }

    static interface ColumnTypes {
        static final String RESULT = "text";
    }

    static final String CREATE_TABLE_SCRIPT =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                    "(" +
                    Columns._ID + "  INTEGER PRIMARY KEY, " +
                    Columns.RESULT + " " + ColumnTypes.RESULT +
                    ")";

    static final String DROP_TABLE_SCRIPT =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    static final String AUTHORITY = "com.yandex.shad2018_practical6.simpleexample";
    static final String RESULTS_PATH = "results";
    static final Uri AUTHORITY_URI = Uri.parse("content://" + AUTHORITY);
    static final Uri RESULTS_URI = Uri.withAppendedPath(AUTHORITY_URI, RESULTS_PATH);
}
