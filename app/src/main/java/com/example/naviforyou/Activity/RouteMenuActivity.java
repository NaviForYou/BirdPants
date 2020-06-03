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

import com.example.naviforyou.ODsay.Bus;
import com.example.naviforyou.ODsay.Subway;
import com.example.naviforyou.ODsay.Traffic;
import com.example.naviforyou.ODsay.Walk;
import com.example.naviforyou.R;
import com.example.naviforyou.SearchData;
import com.odsay.odsayandroidsdk.API;
import com.odsay.odsayandroidsdk.ODsayData;
import com.odsay.odsayandroidsdk.ODsayService;
import com.odsay.odsayandroidsdk.OnResultCallbackListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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

        // 길찾기 표시 여부, ODsay Api
        ODsayService odsayService = ODsayService.init(this, "3BeWEymToCezTng4oHpttVpNpcq+3Qdn0WoQc/S9R+c");
        odsayService.setReadTimeout(5000);
        odsayService.setConnectionTimeout(5000);

        OnResultCallbackListener onResultCallbackListener = new OnResultCallbackListener() {
            @Override
            public void onSuccess(ODsayData oDsayData, API api) {
                {
                    try {if (api == API.SEARCH_PUB_TRANS_PATH) {
                        JSONObject json = oDsayData.getJson();
                        ArrayList<ArrayList<Object>> LIST = new ArrayList<>();
                        ODsay_TransPath_Parser(json, LIST);

                        String busCount= json.getJSONObject("result").getString("busCount");
                        String subwayCount = json.getJSONObject("result").getString("subwayCount");
                        String subwaybusCount = json.getJSONObject("result").getString("subwayBusCount");

                    }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onError(int i, String s, API api) {
                if (api == API.SEARCH_PUB_TRANS_PATH) {
                }
            }
        };
        if(startData.isData() && endData.isData()){
            odsayService.requestSearchPubTransPath("126.97839260101318","37.56660635021524","127.05842971801758","37.61979786831449","1","0","0",onResultCallbackListener);
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

    void ODsay_TransPath_Parser(JSONObject json,ArrayList<ArrayList<Object>> LIST){

        try {
            JSONArray path = json.getJSONObject("result").getJSONArray("path");
            ArrayList<Object> content = new ArrayList<>();
            for (int i = 0; i < path.length(); i++) {
                Traffic traffic = new Traffic(i);
                traffic.what(json);
                content.add(i, traffic);
                JSONArray subPath = path.getJSONObject(i).getJSONArray("subPath");
                ArrayList<Object> list = new ArrayList<>();
                for (int j = 0; j < subPath.length(); j++) {
                    String trafficType = subPath.getJSONObject(j).getString("trafficType");
                    int T = Integer.parseInt(trafficType);
                    if (T == 1) {
                        Subway subway = new Subway(i, j);
                        subway.what(json);
                        list.add(j, subway);
                    } else if (T == 2) {
                        Bus bus = new Bus(i, j);
                        bus.what(json);
                        list.add(j, bus);
                    } else if (T == 3) {
                        Walk walk = new Walk(i, j);
                        walk.what(json);
                        list.add(j, walk);
                    }
                }
                LIST.add(i, list);
            }

        }catch (Exception e){}

    }
}

