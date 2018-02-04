package com.example.shad2018_practical6.simpleexample;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private Button mServiceControlButton;
    private ListView mListView;
    boolean isRunning = false;

    private static final int RESULTS_CONTENT = 1;

    CursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mServiceControlButton = findViewById(R.id.btn_service_control);
        mListView = findViewById(R.id.lv_results);

        mCursorAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, null,
                new String[]{ResultsContract.Columns._ID, ResultsContract.Columns.RESULT},
                new int[]{android.R.id.text1, android.R.id.text2 }, 0);

        mListView.setAdapter(mCursorAdapter);

        mServiceControlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CalculationService.class);
                if (isRunning) {
                    isRunning = false;
                    mServiceControlButton.setText(getString(R.string.start_calculation));
                    stopService(intent);
                } else {
                    isRunning = true;
                    mServiceControlButton.setText(getString(R.string.stop_calculation));
                    startService(intent);
                }
            }
        });

        getLoaderManager().initLoader(RESULTS_CONTENT, null, this);

    }

    @Override
    protected void onStop() {
        super.onStop();
        isRunning = false;
        stopService(new Intent(getApplicationContext(), CalculationService.class));
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri basePath = ResultsContract.RESULTS_URI;
        String[] projection = new String[] {ResultsContract.Columns._ID, ResultsContract.Columns.RESULT};
        return new CursorLoader(this, basePath, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }
}