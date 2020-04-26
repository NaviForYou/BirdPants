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

    private static int DATABASE_VERSION = 2; //DB 버전 몇번인지
    private static String DATABASE_NAME = "PlaceDB.db";
    public static SQLiteDatabase mDB;
    private DatabaseHelper mDBHelper;
    private Context mCtx;

    public static  final class DB implements BaseColumns {
        private static final String TABLE_NAME = "favoriteTable"; // SQLite 사용하기에 만드는 표이름
        private static final String KEY_ID = "id";
        public static final String PLACE_TITLE = "placeTitle"; // 장소 이름
        public static final String ROAD_ADDRESS = "rAddress";
        private static final String CREATE_TABLE =  "create table if not exists " + TABLE_NAME + "("
                +_ID+" integer primary key autoincrement, "
                + KEY_ID + " text not null,"
                + PLACE_TITLE + " text not null,"
                + ROAD_ADDRESS + " text not null );";
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
    public long insert(String id, String place_title,  String road_address ) {
        ContentValues cv = new ContentValues();
        cv.put(DB.PLACE_TITLE , place_title);
        cv.put(DB.KEY_ID, id);
        cv.put(DB.ROAD_ADDRESS, road_address);
        Log.d("FavDB Status", place_title +  " roadAddress- . " +road_address + ", ." +cv);
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
        cv.put(DB.ROAD_ADDRESS, road_address);
        Log.d("FavDB Status", place_title + ", buildAddress - "+ build_address  + " roadAddress- . " +road_address + ", ." +cv);
        return mDB.update(DB.TABLE_NAME, cv, "_id=" + id, null) > 0;
    }

    // 모든 데이터 지우기
    public void deleteAll() {
        mDB.delete(DB.TABLE_NAME, null, null);
    }

    // 특정 행 지우기
    public boolean deleteColumn(long id){
        return mDB.delete(DB.TABLE_NAME, "_id="+id, null) > 0;
    }

    // 모든 데이터 읽기
    public Cursor readAll(String id) {
        String sql = "select * from " + DB.TABLE_NAME + " where " + DB.KEY_ID+"="+id+"";
        return mDB.rawQuery(sql,null,null);
    }


}
