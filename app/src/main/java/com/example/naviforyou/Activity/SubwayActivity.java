package com.example.naviforyou.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.naviforyou.R;

import java.util.ArrayList;
import java.util.List;

public class SubwayActivity extends AppCompatActivity implements View.OnClickListener {
    TextView subway1;
    TextView subway2;
    TextView subway3;
    TextView subway4;
    TextView subway5;
    TextView subway6;
    TextView subway7;
    TextView subway8;
    TextView subway9;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subway);
        subway1 = findViewById(R.id.subway_1);
        subway2 = findViewById(R.id.subway_2);
        subway3 = findViewById(R.id.subway_3);
        subway4 = findViewById(R.id.subway_4);
        subway5 = findViewById(R.id.subway_5);
        subway6 = findViewById(R.id.subway_6);
        subway7 = findViewById(R.id.subway_7);
        subway8 = findViewById(R.id.subway_8);
        subway9 = findViewById(R.id.subway_9);

        subway1.setOnClickListener(this);
        subway2.setOnClickListener(this);
        subway3.setOnClickListener(this);
        subway4.setOnClickListener(this);
        subway5.setOnClickListener(this);
        subway6.setOnClickListener(this);
        subway7.setOnClickListener(this);
        subway8.setOnClickListener(this);
        subway9.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, LineActivity.class);
        switch (v.getId()){
            case R.id.subway_1 :
                intent.putExtra("line","1");
                break;
            case R.id.subway_2 :
                intent.putExtra("line","2");
                break;
            case R.id.subway_3 :
                intent.putExtra("line","3");
                break;
            case R.id.subway_4 :
                intent.putExtra("line","4");
                break;
            case R.id.subway_5 :
                intent.putExtra("line","5");
                break;
            case R.id.subway_6 :
                intent.putExtra("line","6");
                break;
            case R.id.subway_7 :
                intent.putExtra("line","7");
                break;
            case R.id.subway_8 :
                intent.putExtra("line","8");
                break;
            case R.id.subway_9 :
                intent.putExtra("line","9");
                break;
        }
        startActivity(intent);
    }

}
