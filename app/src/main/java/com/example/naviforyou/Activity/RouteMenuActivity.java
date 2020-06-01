package com.example.naviforyou.Activity;

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

public class RouteMenuActivity  extends AppCompatActivity {

    RelativeLayout relativeLayout;
    EditText searchStart;
    EditText searchEnd;
    ImageView startBtn;
    ImageView endBtn;

    String type; // start = 출발지 정해짐, end = 도착지 정해짐, none = 둘다 정해지지 않음.
    boolean isStart;
    boolean isEnd;
    String placeName;
    double startX;
    double startY;
    double endX;
    double endY;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routemenu);
        searchStart = findViewById(R.id.searchStart);
        searchEnd = findViewById(R.id.searchEnd);
        startBtn = findViewById(R.id.startBtn);
        endBtn = findViewById(R.id.endBtn);
        relativeLayout = findViewById(R.id.route);

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
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent1);
            }
        });

        endBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), SearchActivity.class);
                intent1.putExtra("type","searchEnd");
                String text = searchStart.getText().toString();
                if(text.length() != 0){
                    intent1.putExtra("text",text);
                }
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent1);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 넘겨받은 정보
        Intent intent = getIntent();
        type = "none";
        if(intent.hasExtra("type")) {
            type = intent.getStringExtra("type");
            Log.d("RouteMenu","changmin= type : " + type);
        }

        if(type.equals("start")) {
            placeName = intent.getStringExtra("start");
            startX=intent.getExtras().getDouble("startX");
            startY=intent.getExtras().getDouble("startY");
            searchStart.setText(placeName);
            isStart = true;
        }else if(type.equals("end")) {
            placeName = intent.getStringExtra("end");
            endX=intent.getExtras().getDouble("endX");
            endY=intent.getExtras().getDouble("endY");
            searchEnd.setText(placeName);
            isEnd = true;
        }else if(type.equals("none")) {

        }

        Log.d("RouteMenu","changmin= isEnd : " + isEnd + ", isStart : " + isStart);
        // 길찾기 표시 여부
        if(isEnd && isStart){
            relativeLayout.setVisibility(View.VISIBLE);
        }


    }
}

