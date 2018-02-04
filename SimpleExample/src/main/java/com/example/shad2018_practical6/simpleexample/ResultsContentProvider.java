package com.example.shad2018_practical6.simpleexample;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class ResultsContentProvider extends ContentProvider {

    private static final UriMatcher sUriMather = new UriMatcher(0);
    SQLiteOpenHelper mHelper;

    public ResultsContentProvider() {
        sUriMather.addURI(ResultsContract.AUTHORITY, ResultsContract.RESULTS_PATH, 1);
    }

    @Override
    public boolean onCreate() {
        mHelper = new ResultsDbHelper(getContext());
        return true;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        switch (sUriMather.match(uri)) {

            case 1:
                SQLiteDatabase db = mHelper.getWritableDatabase();
                int count = db.delete(ResultsContract.TABLE_NAME, selection, selectionArgs);
                getContext().getContentResolver().notifyChange(uri, null);
                return count;

            default:
                return -1;
        }
    }

    @Override
    public String getType(Uri uri) {
        switch (sUriMather.match(uri)) {
            case 1:
                return "vnd.com.example.shadloadersample/vnd.result";
            default:
                return null;
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        switch (sUriMather.match(uri)) {
            case 1:
                SQLiteDatabase db = mHelper.getWritableDatabase();
                long id = db.insert(ResultsContract.TABLE_NAME, null, values);
                Uri insertedUri = ContentUris.withAppendedId(uri, id);
                getContext().getContentResolver().notifyChange(ResultsContract.AUTHORITY_URI, null);
                return insertedUri;
            default:
                return null;

        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        switch (sUriMather.match(uri)) {
            case 1:
                SQLiteDatabase db = mHelper.getReadableDatabase();
                Cursor cursor = db.query(ResultsContract.TABLE_NAME,
                        projection, selection, selectionArgs, null, null, sortOrder);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);
                return cursor;
            default:
                return null;
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        switch (sUriMather.match(uri)) {
            case 1:
                SQLiteDatabase db = mHelper.getWritableDatabase();
                int count = db.update(ResultsContract.TABLE_NAME, values, selection, selectionArgs);
                getContext().getContentResolver().notifyChange(uri, null);
                return count;
            default:
                return -1;
        }
    }
}
