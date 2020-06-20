package com.example.naviforyou.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.naviforyou.Adapter.Utility;
import com.example.naviforyou.Adapter.ViewFindingMapAdapter;
import com.example.naviforyou.ODsay.Bus;
import com.example.naviforyou.ODsay.Subway;
import com.example.naviforyou.ODsay.Traffic;
import com.example.naviforyou.ODsay.Walk;
import com.example.naviforyou.R;

import java.util.ArrayList;

public class FindingMapActivity extends AppCompatActivity {
    Traffic content;
    ArrayList<Object> route;
    String start;
    String end;
    TextView time;
    LinearLayout bar;
    ListView list;

    ViewFindingMapAdapter viewFindingMapAdapter;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findingmap);

        Intent intent = getIntent();
        content = (Traffic)intent.getExtras().getSerializable("content");
        route = (ArrayList<Object>)intent.getExtras().getSerializable("route");
        start = intent.getStringExtra("start");
        end = intent.getStringExtra("end");

        time = findViewById(R.id.time);
        bar = findViewById(R.id.routebar);
        list = findViewById(R.id.list);

        String hour = content.getHour();
        String min = content.getMin();

        if ( hour.equals("0") ){
            time.setText(min+"분");
        }else{
            time.setText(hour+"시간" + min+"분");
        }

        //bar 보여주기
        for (int i=0;i<route.size(); i++){
            String text = "";
            String color="#000000";
            Drawable img=null;
            int time=0;
            if (route.get(i) instanceof Bus){
                Bus bus = (Bus)route.get(i);
                time=Integer.parseInt(bus.getBusInfo_1()[1]);
                color="#4b89dc";
                img = this.getResources().getDrawable(R.drawable.customise_bus);
            }
            else if(route.get(i) instanceof Subway){
                Subway subway = (Subway)route.get(i);
                time=Integer.parseInt(subway.getSubwayInfo_1()[1]);
                img = this.getResources().getDrawable(R.drawable.customise_subway);
                int lane = Integer.parseInt(subway.getSubwayInfo_2()[0][1]);
                switch (lane){
                    case 1:
                        color="#0d3692";
                        break;
                    case 2:
                        color="#33a23d";
                        break;
                    case 3:
                        color="#fe5d10";
                        break;
                    case 4:
                        color="#00a2d1";
                        break;
                    case 5:
                        color="#8b50a4";
                        break;
                    case 6:
                        color="#c55c1d";
                        break;
                    case 7:
                        color="#54640d";
                        break;
                    case 8:
                        color="#f14c82";
                        break;
                    case 9:
                        color="#aa9872";
                        break;
                }
            }
            else if(route.get(i) instanceof Walk) {
                Walk walk = (Walk) route.get(i);
                time = Integer.parseInt(walk.getWalkInfo()[1]);
                img = this.getResources().getDrawable(R.drawable.customise_wheelchair);
                color="#d3d3d3";
            }


            float weight = (float) ((float)time / 5.0);

            TextView view = new TextView(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, weight);
            if (time != 0) {
                text=""+ time +"분";
                view.setText(text);
            }

            view.setGravity(Gravity.CENTER_HORIZONTAL);
            view.setMinWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            view.setBackgroundColor(Color.parseColor(color));
            view.setTextColor(Color.parseColor("#E3E9ED"));
            view.setLayoutParams(lp);
            view.setCompoundDrawablesRelativeWithIntrinsicBounds(img,null,null,null);
            bar.addView(view);
        }

        //list view
        route.add(0,start);
        route.add(end);
        viewFindingMapAdapter = new ViewFindingMapAdapter(
                FindingMapActivity.this,R.layout.item_findingmap,route);
        list.setAdapter(viewFindingMapAdapter);


    }
}
