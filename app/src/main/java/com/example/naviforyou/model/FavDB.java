// 줄겨찾기 데이터베이스
// made by Jiu
// https://www.youtube.com/watch?v=T3y3370UE8w

package com.example.naviforyou.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;

public class FavDB {

    private static int DATABASE_VERSION = 1; //DB 버전 몇번인지
    private static String DATABASE_NAME = "PlaceDB";
    public static SQLiteDatabase mDB;
    private DatabaseHelper mDBHelper;
    private Context mCtx;

    public static  final class DB implements BaseColumns {
        private static String TABLE_NAME = "favoriteTable"; // SQLite 사용하기에 만드는 표이름
        private static String KEY_ID = "id";
        public static String PLACE_TITLE = "placeTitle"; // 장소 이름
        public static String BUILD_ADDRESS = "bAddress";
        public static String ROAD_ADDRESS = "rAddress";
        private static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                +_ID+" int"
                + KEY_ID + "integer primary key autoincrement, "
                + PLACE_TITLE + " TEXT,"
                + ROAD_ADDRESS + " TEXT,"
                + BUILD_ADDRESS + "TEXT )";
    }

    private class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(DB.CREATE_TABLE);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            db.execSQL("DROP TABLE IF EXISTS "+DB.TABLE_NAME);
            onCreate(db);
        }

    }

    public FavDB(Context context){
        this.mCtx = context;
    }

    public FavDB open() throws SQLException {
        mDBHelper = new DatabaseHelper(mCtx, DATABASE_NAME, null, DATABASE_VERSION);
        mDB = mDBHelper.getWritableDatabase();
        return this;
    }

    public void create(){
        mDBHelper.onCreate(mDB);
    }

    public void close(){
        mDB.close();
    }


    // insert data into database
    public long insert(String id, String place_title, String build_address , String road_address ) {
        ContentValues cv = new ContentValues();
        cv.put(DB.PLACE_TITLE , place_title);
        cv.put(DB.KEY_ID, id);
        cv.put(DB.BUILD_ADDRESS, build_address );
        cv.put(DB.ROAD_ADDRESS, road_address);
        Log.d("FavDB Status", place_title + ", buildAddress - "+ build_address  + " roadAddress- . " +road_address + ", ." +cv);
        return mDB.insert(DB.TABLE_NAME, null, cv);
    }

    //특정 colums 선택
    public Cursor selectColumns(){
        return mDB.query(DB.TABLE_NAME, null, null, null, null, null, null);
    }

    //DB에 존재 여부 확인
    public Boolean isFavStatus(String id){
        Cursor iCursor = selectColumns();

        while(iCursor.moveToNext()){
            String tempID = iCursor.getString(iCursor.getColumnIndex("id"));
            if(tempID.equals(id)){
                return true;
            }
        }
        return false;
    }

    //특정 즐겨찾기 저장 버튼 클릭시 해당 정보 제공
    // id,placeTitle,buildAddress,RoadAddress
    public ArrayList<String> infoFav(String id){
        Cursor iCursor = selectColumns();
        ArrayList<String> temp = new ArrayList<>();

        while(iCursor.moveToNext()){
            String tempID = iCursor.getString(iCursor.getColumnIndex("id"));
            if(tempID.equals(id)){
                temp.add(tempID);
                temp.add(iCursor.getString(iCursor.getColumnIndex("placeTitle")));
                temp.add(iCursor.getString(iCursor.getColumnIndex("bAddress")));
                temp.add(iCursor.getString(iCursor.getColumnIndex("rAddress")));
            }
        }
        return temp;
    }

    //데이터 갱신
    public boolean updateColumn(String id, String place_title, String build_address , String road_address ) {
        ContentValues cv = new ContentValues();
        cv.put(DB.PLACE_TITLE , place_title);
        cv.put(DB.KEY_ID, id);
        cv.put(DB.BUILD_ADDRESS, build_address );
        cv.put(DB.ROAD_ADDRESS, road_address);
        Log.d("FavDB Status", place_title + ", buildAddress - "+ build_address  + " roadAddress- . " +road_address + ", ." +cv);
        return mDB.update(DB.TABLE_NAME, cv, "_id=" + id, null) > 0;
    }

    // Delete All
    public void deleteAllColumns() {
        mDB.delete(DB.TABLE_NAME, null, null);
    }

    // Delete Column
    public boolean deleteColumn(long id){
        return mDB.delete(DB.TABLE_NAME, "_id="+id, null) > 0;
    }


}
