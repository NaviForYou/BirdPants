package com.example.naviforyou.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.naviforyou.R;
import com.example.naviforyou.SearchData;

public class RouteMenuActivity  extends AppCompatActivity {

    public static Activity activity;

    RelativeLayout relativeLayout;
    EditText searchStart;
    EditText searchEnd;
    ImageView startBtn;
    ImageView endBtn;

    String type; // start = 출발지 정해짐, end = 도착지 정해짐, none = 둘다 정해지지 않음.
    SearchData startData;
    SearchData endData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routemenu);
        activity = RouteMenuActivity.this;

        //layout 객체
        searchStart = findViewById(R.id.searchStart);
        searchEnd = findViewById(R.id.searchEnd);
        startBtn = findViewById(R.id.startBtn);
        endBtn = findViewById(R.id.endBtn);
        relativeLayout = findViewById(R.id.route);

        //기타
        startData = new SearchData("start");
        endData = new SearchData("end");

        //첫 intent 생성시
        Intent intent = getIntent();
        type = "none";
        if(intent.hasExtra("type")) {
            type = intent.getStringExtra("type");
            Log.d("RouteMenu","changmin= type : " + type);
        }

        if(type.equals("start")) {
            startData.searchData(
                    intent.getStringExtra("start"),
                    intent.getExtras().getDouble("startX"),
                    intent.getExtras().getDouble("startY")
                    );
            searchStart.setText(startData.getPlaceName());
        }else if(type.equals("end")) {
            endData.searchData(
                    intent.getStringExtra("end"),
                    intent.getExtras().getDouble("endX"),
                    intent.getExtras().getDouble("endY")
                    );
            searchEnd.setText(endData.getPlaceName());
        }


    }

    @Override
    protected void onResume() {
        super.onResume();

        //출발지 도착지 검색 버튼
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), SearchActivity.class);
                intent1.putExtra("type","searchStart");
                String text = searchStart.getText().toString();
                if(text.length() != 0){
                    intent1.putExtra("text",text);
                }
                startActivity(intent1);
            }
        });

        endBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), SearchActivity.class);
                intent1.putExtra("type","searchEnd");
                String text = searchEnd.getText().toString();
                if(text.length() != 0){
                    intent1.putExtra("text",text);
                }
                startActivity(intent1);
            }
        });

        // 길찾기 표시 여부
        if(startData.isData() && endData.isData()){
            relativeLayout.setVisibility(View.VISIBLE);
        }else{
            relativeLayout.setVisibility(View.GONE);
        }
    }

    public void setStartData(String placeName, double x, double y) {
        this.startData.searchData(placeName,x,y);
    }

    public void setEndData(String placeName, double x, double y) {
        this.endData.searchData(placeName,x,y);
    }
}

