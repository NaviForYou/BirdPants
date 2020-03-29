/*

package com.example.naviforyou;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class database extends AppCompatActivity {

    public static final String NVFU_DB = "NVFU_DB";
    public static final String NVFU_TABLE = "NVFU";
    public static SQLiteDatabase nvfuDB = null;

    public static ArrayList<Double> mAverage Attention = new ArrayList<Double>()

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        nvfuDB = this.openOrCreateDatabase( NVFU_DB, MODE_PRIVATE, null);
        nvfuDB.execSQL("CREATE TABLE IF NOT EXISTS" + NVFU_TABLE + "(id VARCHAR(50)), name VARCHAR(50));"); //id와 name 자리 -> 들어가는 데이터 내용으로 바꿔야함

    }

    //필요없을 것 같아서 주석처리해놓음
    private void addAverageAttention(double attention) {
        mAverageAttention.add(attention);
    }

    private void resetAverageAttention() {
        mAverageAttention.clear();
    }

    private double calculateAverageAttention() {
        double attentionSum =0;
        for(double attention : mAverageAttention) {
            attentionSum +=attention;
        };
        return attention Sum/ mAverageAttention.size();

    }



}

*/