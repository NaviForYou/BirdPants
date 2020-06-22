package com.example.naviforyou.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.naviforyou.R;

import java.lang.reflect.Field;

public class SubwayMapActivity extends AppCompatActivity {
    String line;
    int station;

    ImageView subwayMap;
    Button exit;
    ImageView back;
    TextView line_name;
    LinearLayout title;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subwaymap);
        exit = findViewById(R.id.exit);
        back = findViewById(R.id.back);
        subwayMap = findViewById(R.id.subway_map);
        line_name = findViewById(R.id.lineMap_name);
        title = findViewById(R.id.lineMap_title);


        Intent intent = getIntent();
        line= intent.getStringExtra("line");
        station = intent.getExtras().getInt("station") + 1;
        Log.d("TEXT","station : " + station);
        String imgFormat = "line"+line+"_%s";

        switch (line){
            case "1" :
                line_name.setText("1호선");
                title.setBackgroundColor(Color.parseColor("#0d3692"));
                break;
            case "2" :
                line_name.setText("2호선");
                title.setBackgroundColor(Color.parseColor("#33a23d"));
                break;
            case "3" :
                line_name.setText("3호선");
                title.setBackgroundColor(Color.parseColor("#fe5d10"));
                break;
            case "4" :
                line_name.setText("4호선");
                title.setBackgroundColor(Color.parseColor("#00a2d1"));
                break;
            case "5" :
                line_name.setText("5호선");
                title.setBackgroundColor(Color.parseColor("#8b50a4"));
                break;
            case "6" :
                line_name.setText("6호선");
                title.setBackgroundColor(Color.parseColor("#c55c1d"));
                break;
            case "7" :
                line_name.setText("7호선");
                title.setBackgroundColor(Color.parseColor("#54640d"));
                break;
            case "8" :
                line_name.setText("8호선");
                title.setBackgroundColor(Color.parseColor("#f14c82"));
                break;
            case "9" :
                line_name.setText("9호선");
                title.setBackgroundColor(Color.parseColor("#aa9872"));
                break;
        }

        try {
            Field drawables = R.drawable.class.getField(String.format(imgFormat, station));
            subwayMap.setImageResource(drawables.getInt(R.drawable.class));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        back.setOnClickListener(v -> {
            this.finish();
        });

        exit.setOnClickListener(v -> {
            try {
                Field drawables = R.drawable.class.getField(String.format(imgFormat, station));
                subwayMap.setImageResource(drawables.getInt(R.drawable.class));
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });

    }
}
