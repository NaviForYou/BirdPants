

package com.example.naviforyou;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseCreate {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "sdf.db";
    private static final String TABLE_NAME = "SeoulDisabledFacilites";
    public static final String BLDG_X = "bldg_x";
    public static final String BLDG_Y = "bldg_y";
    Category openHelper;
    private SQLiteDatabase database;

    public DatabaseCreate(Context context){
        openHelper = new Category(context);
        database = openHelper.getWritableDatabase();
    }
    public void saveCategoryRecord(String id, String name) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(BLDG_X, id);
        contentValues.put(BLDG_Y, name);
        database.insert(TABLE_NAME, null, contentValues);
    }
    public Cursor getTimeRecordList() {
        return database.rawQuery("select * from " + TABLE_NAME, null);
    }
    private class Category extends SQLiteOpenHelper {

        public Category(Context context) {
            // TODO Auto-generated constructor stub
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
            public void onCreate(SQLiteDatabase db) {
                // TODO Auto-generated method stub
                db.execSQL("CREATE TABLE " + TABLE_NAME + "( "
                        + BLDG_X + " INTEGER PRIMARY KEY, "
                        + BLDG_Y + " TEXT )" );

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub
            db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
            onCreate(db);
        }

    }
}