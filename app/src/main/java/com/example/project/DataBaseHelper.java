package com.example.project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "lightriseDays.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_LIGHTRISE_DAYS = "mountain";
    public static final String COLLUMN_NAME = "name";
    public static final String COLLUMN_DATE = "date";
    public static final String COLLUMN_SUNUP = "sunUp";
    public static final String COLLUMN_SUNDOWN = "sunDown";
    public static final String COLLUMN_EVENTS = "events";

    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_LIGHTRISE_DAYS + " (" + COLLUMN_DATE + " INTEGER PRIMARY KEY, " + COLLUMN_NAME + " TEXT, " + COLLUMN_SUNUP + " TEXT, " + COLLUMN_SUNDOWN + " INTEGER, " + COLLUMN_EVENTS + " TEXT );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
