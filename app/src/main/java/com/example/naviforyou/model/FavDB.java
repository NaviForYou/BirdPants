package com.example.naviforyou.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class FavDB extends SQLiteOpenHelper {

    private static int DB_VERSION = 1;
    private static String DATABASE_NAME = "PlaceDB";
    private static String TABLE_NAME = "favoriteTable";
    private static String KEY_ID = "id";
    public static String PLACE_TITLE = "placeTitle";
    public static String FAVORITE_STATUS ="fStatus";
    private static String CREATE_TABLE = "CREATE TABLE" + TABLE_NAME + "("
            + KEY_ID + " TEXT,"+ PLACE_TITLE + " TEXT,"
            + FAVORITE_STATUS + "TEXT)";

    public FavDB(Context context) {super (context, DATABASE_NAME, null, DB_VERSION);}
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    //create empty table
    public void insertEmpty() {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues cv = new.ContentValues();
        //enter the value
        for (int x=1; x<11 ; x++){
            cv.put(KEY_ID, x);
            cv.put(FAVORITE_STATUS, "0");

            db.insert(TABLE_NAME, null, cv);

        }

    }

    // insert data into database
    public void insertIntoTheDatabase(String place_title, String id, String fav_status) {
        SQLiteDatabase db;
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(PLACE_TITLE , place_title);
        cv.put(KEY_ID, id);
        cv.put(FAVORITE_STATUS, fav_status);
        db.insert(TABLE_NAME, null, cv);
        Log.d("FavDB Status", place_title + ", favstatus - "+fav_status+ " - . "+cv);

    }

    //read all data
    public Cursor read_all_data (String id) {
        SQLiteDatabase db =this.getReadableDatabase();
        String sql = "select * from " + TABLE_NAME +" where " + KEY_ID +"="+id+"";
        return  db.rawQuery(sql, null, null);
    }

    //remove line from database
    public void remove_fa(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "UPDATE " + TABLE_NAME + "SET " + FAVORITE_STATUS+ " = '0' WHERE " +KEY_ID+"="+id+"";
        db.execSQL(sql);
        Log.d("remove", id.toString());

    }

    //Select All Favorite List

    public Cursor select_all_favorite_list() {
        SQLiteDatabase db= this.getReadableDatabase();
        String sql = "SELECT * from "+TABLE_NAME+" WHERE " + FAVORITE_STATUS+" ='1'";
        return db.rawQuery(sql, null,null);

    }
}
