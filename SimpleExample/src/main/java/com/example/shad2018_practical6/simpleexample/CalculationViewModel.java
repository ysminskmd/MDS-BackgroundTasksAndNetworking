package com.example.shad2018_practical6.simpleexample;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class CalculationViewModel extends AndroidViewModel {

    @NonNull
    private CalculatingLiveData mCalculatingLiveData;

    class CalculatingLiveData extends LiveData<List<CalculationResult>> {

        private ContentObserver mContentObserver;

        public CalculatingLiveData() {
            loadData();

            mContentObserver = new ContentObserver(new Handler()) {
                @Override
                public boolean deliverSelfNotifications() {
                    return true;
                }

                @Override
                public void onChange(final boolean selfChange) {
                    super.onChange(selfChange);
                    loadData();
                }
            };
        }

        @Override
        protected void onActive() {
            super.onActive();
            getApplication().getContentResolver().registerContentObserver(
                    ResultsContract.RESULTS_URI,
                    false,
                    mContentObserver
            );
        }

        @Override
        protected void onInactive() {
            super.onInactive();
            getApplication().getContentResolver().unregisterContentObserver(mContentObserver);
        }

        void loadData() {
            new AsyncTask<Void, Void, List<CalculationResult>>() {

                @Override
                protected List<CalculationResult> doInBackground(final Void... voids) {
                    List<CalculationResult> result = new ArrayList<>();
                    Cursor cursor = getApplication().getContentResolver().query(
                            ResultsContract.RESULTS_URI,
                            new String[]{ResultsContract.Columns._ID, ResultsContract.Columns.RESULT},
                            null,
                            null,
                            null
                            );
                    if (cursor != null) {
                        while (cursor.moveToNext()) {
                            CalculationResult calculationResult = new CalculationResult(
                                    cursor.getString(cursor.getColumnIndex(ResultsContract.Columns._ID)),
                                    cursor.getString(cursor.getColumnIndex(ResultsContract.Columns.RESULT))
                            );
                            result.add(calculationResult);
                        }
                    }
                    return result;
                }

                @Override
                protected void onPostExecute(final List<CalculationResult> calculationResults) {
                    setValue(calculationResults);
                }
            }.execute();
        }
    }

    public CalculationViewModel(@NonNull final Application application) {
        super(application);
        mCalculatingLiveData = new CalculatingLiveData();
    }

    @NonNull
    public CalculatingLiveData getCalculatingLiveData() {
        return mCalculatingLiveData;
    }
}
