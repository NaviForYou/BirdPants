package com.example.naviforyou_finding;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    RelativeLayout searching, result_1;
    LinearLayout way, box_time,box_hour, box_min, box_money, menuBar;
    EditText searchStart, searchEnd;
    Button rotation, noName_1, lift, bookMark, btn_finding, btn_subway, btn_taxi, btn_bookMark;
    TextView all, walking, bus, subway_result, busnsbway;
    TextView hour, min, money, startAMPM, startHour, startMin, endAMPM, endHour, endMin;
    int n_hour = 0, n_min = 0, n_money = 0, n_startHour = 00, n_startMin=0, n_endHour=0, n_endMin=0;
    String s_startAMPM = "오전", s_endAMPM = "오전";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listview;
        ListViewAdapter adapter;

        {//findViewId(시간/돈)
            hour = findViewById(R.id.hour);
            min = findViewById(R.id.min);
            money = findViewById(R.id.money);
            startAMPM = findViewById(R.id.startAMPM);
            startHour = findViewById(R.id.startHour);
            startMin = findViewById(R.id.startMin);
            endAMPM = findViewById(R.id.endAMPM);
            endHour = findViewById(R.id.endHour);
            endMin = findViewById(R.id.endMin);
        }
        { //setText(시간/돈)  변수 n_@@ 불러온 값을 넣는다
            hour.setText(String.valueOf(n_hour));           //00시간
            if (n_hour == 0)
                box_hour.setVisibility(View.GONE);
            min.setText(String.valueOf(n_min));             //00분
            money.setText(String.valueOf(n_money));         //원
            if (12 < n_startHour)
                s_startAMPM = "오후";
            startHour.setText(String.valueOf(n_startHour));
            startMin.setText(String.valueOf(n_startMin));
            if (12 < n_endHour)
                s_startAMPM = "오후";
            endHour.setText(String.valueOf(n_endHour));
            endMin.setText(String.valueOf(n_endMin));
        }

        adapter = new ListViewAdapter();
        listview = (ListView) findViewById(R.id.listview1);
        listview.setAdapter(adapter);
    }//onCreate
}
