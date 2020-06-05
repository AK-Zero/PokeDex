package com.example.pokedex;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class FavouriteDBHelper extends SQLiteOpenHelper {

    public FavouriteDBHelper(@Nullable Context context) {
        super(context, "favouritelist.db", null , 1);//can use constants
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_FAVOURITES_TABLE = "CREATE TABLE " +
                FavouriteContract.FavouriteEntry.TABLE_NAME + " (" +
                FavouriteContract.FavouriteEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FavouriteContract.FavouriteEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                FavouriteContract.FavouriteEntry.COLUMN_IMAGE + " TEXT NOT NULL, " +
                FavouriteContract.FavouriteEntry.COLUMN_HEIGHT + " INTEGER NOT NULL, " +
                FavouriteContract.FavouriteEntry.COLUMN_WEIGHT + " INTEGER NOT NULL, " +
                FavouriteContract.FavouriteEntry.COLUMN_EVOLUTION_CHAIN + " TEXT NOT NULL, " +
                FavouriteContract.FavouriteEntry.COLUMN_STATS + " TEXT NOT NULL, " +
                FavouriteContract.FavouriteEntry.COLUMN_TYPE + " TEXT NOT NULL, " +
                FavouriteContract.FavouriteEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ");";
        db.execSQL(SQL_CREATE_FAVOURITES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FavouriteContract.FavouriteEntry.TABLE_NAME);
        onCreate(db);
    }
}
