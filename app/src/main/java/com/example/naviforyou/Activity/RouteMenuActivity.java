package com.example.naviforyou.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.naviforyou.API.Gc;
import com.example.naviforyou.API.Gc_Parser;
import com.example.naviforyou.Adapter.MyRecyclerViewAdapter;
import com.example.naviforyou.Adapter.ViewRouteAdapter;
import com.example.naviforyou.Data.GpsTracker;
import com.example.naviforyou.ODsay.Bus;
import com.example.naviforyou.ODsay.Subway;
import com.example.naviforyou.ODsay.Traffic;
import com.example.naviforyou.ODsay.Walk;
import com.example.naviforyou.R;
import com.example.naviforyou.Data.SearchData;
import com.odsay.odsayandroidsdk.API;
import com.odsay.odsayandroidsdk.ODsayData;
import com.odsay.odsayandroidsdk.ODsayService;
import com.odsay.odsayandroidsdk.OnResultCallbackListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class RouteMenuActivity  extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener{

    public static Activity activity;
    private MyRecyclerViewAdapter myRecyclerViewAdapter;
    ViewRouteAdapter ViewRouteAdapter;
    private GpsTracker gpsTracker;
    Gc_Parser gc_parser = new Gc_Parser();
    Gc tracking;

    RelativeLayout relativeLayout;
    TextView searchStart;
    TextView searchEnd;
    ImageView rotation;
    ImageView clear;
    ListView result;

    String type; // start = 출발지 정해짐, end = 도착지 정해짐, none = 둘다 정해지지 않음.
    SearchData startData;
    SearchData endData;
    ArrayList<ArrayList<Object>> routeList;
    ArrayList<Traffic> routeContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routemenu);
        activity = RouteMenuActivity.this;

        //layout 객체
        searchStart = findViewById(R.id.searchStart);
        searchEnd = findViewById(R.id.searchEnd);
        rotation = findViewById(R.id.rotation);
        clear = findViewById(R.id.clear);
        relativeLayout = findViewById(R.id.route);
        result = findViewById(R.id.result);

        //data to populate the RecyclerView with
        ArrayList<String> choose_main = new ArrayList<>();
        choose_main.add("전체");
        choose_main.add("버스");
        choose_main.add("지하철");
        choose_main.add("버스+지하철");

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.way);
        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        myRecyclerViewAdapter = new MyRecyclerViewAdapter(this, choose_main);
        myRecyclerViewAdapter.setClickListener(this);
        recyclerView.setAdapter(myRecyclerViewAdapter);

        //데이터 저장
        startData = new SearchData("start");
        endData = new SearchData("end");

        //출발지 도착지 검색
        searchStart.setOnClickListener(new View.OnClickListener() {
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

        searchEnd.setOnClickListener(v -> {
            Intent intent1 = new Intent(getApplicationContext(), SearchActivity.class);
            intent1.putExtra("type","searchEnd");
            String text = searchEnd.getText().toString();
            if(text.length() != 0){
                intent1.putExtra("text",text);
            }
            startActivity(intent1);
        });

        //clear
        clear.setOnClickListener(v -> {
            startData.clear();
            endData.clear();
            relativeLayout.setVisibility(View.GONE);
            routeList = null;
            searchStart.setText(null);
            searchEnd.setText(null);
        });

        //출발지 도착지 교환
        rotation.setOnClickListener(v -> {
            String tempPlaceName = endData.getPlaceName();
            Double tempX = endData.getX();
            Double tempY = endData.getY();
            boolean tempIsData = endData.isData();

            endData.setSearchData(
                    startData.getPlaceName(),
                    startData.getX(),
                    startData.getY()
            );

            startData.setSearchData(
                    tempPlaceName,
                    tempX,
                    tempY,
                    tempIsData
            );

            searchStart.setText(startData.getPlaceName());
            searchEnd.setText(endData.getPlaceName());
        });

        //첫 intent 생성시 데이터 받기
        Intent intent = getIntent();
        type = "none";
        if(intent.hasExtra("type")) {
            type = intent.getStringExtra("type");
        }

        if(type.equals("start")) {
            startData.setSearchData(
                    intent.getStringExtra("start"),
                    intent.getStringExtra("startX"),
                    intent.getStringExtra("startY")
                    );
            searchStart.setText(startData.getPlaceName());
        }else if(type.equals("end")) {
            endData.setSearchData(
                    intent.getStringExtra("end"),
                    intent.getStringExtra("endX"),
                    intent.getStringExtra("endY")
                    );
            searchEnd.setText(endData.getPlaceName());
        }

        if (type.equals("end") || type.equals("none")){

            gpsTracker = new GpsTracker(this);
            String latitude = String.valueOf(gpsTracker.getLatitude());
            String longitude = String.valueOf(gpsTracker.getLongitude());
            try {

                tracking = new NaverAsync_Gc().execute(longitude + "," + latitude).get();
                startData.setSearchData(
                        tracking.getBuildAddress(),
                        longitude,
                        latitude);

            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        searchStart.setText(startData.getPlaceName());
        searchEnd.setText(endData.getPlaceName());


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

                        ODsay_TransPath_Parser(json);

                        String busCount= json.getJSONObject("result").getString("busCount");
                        String subwayCount = json.getJSONObject("result").getString("subwayCount");
                        String subwaybusCount = json.getJSONObject("result").getString("subwayBusCount");
                        Log.d("COUNT","BUScount : " + busCount + ", SubwayCount : " + subwayCount + ", SubWayBusCount : " + subwaybusCount);

                        //list view
                        ViewRouteAdapter = new ViewRouteAdapter(RouteMenuActivity.this, R.layout.item_route, routeList, routeContent);
                        result.setAdapter(ViewRouteAdapter);
                        relativeLayout.setVisibility(View.VISIBLE);

                    }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onError(int i, String s, API api) {
                if (api == API.SEARCH_PUB_TRANS_PATH) {
                    Log.e("ERROR","ODSAY ERROR" + s);
                }
            }
        };

        Log.d("TEXT", "isData : " + endData.toString() + startData.toString());

        if(startData.isData() && endData.isData()){
            Log.d("Coords","SX : " + startData.getX_toString() + ", SY : " + startData.getY_toString() + ", EX :" + endData.getX_toString() +", EY : " + endData.getY_toString());
            odsayService.requestSearchPubTransPath(
                    startData.getX_toString(),startData.getY_toString(),
                    endData.getX_toString(),endData.getY_toString(),
                    "1","0","0",onResultCallbackListener);
            //odsayService.requestSearchPubTransPath("126.97839260101318","37.56660635021524","127.05842971801758","37.61979786831449","1","0","0",onResultCallbackListener);
        }else{
            relativeLayout.setVisibility(View.GONE);
        }


        OnResultCallbackListener searchStation = new OnResultCallbackListener() {
            @Override
            public void onSuccess(ODsayData oDsayData, API api) {
                try {if (api == API.SEARCH_STATION) {
                    JSONObject json = oDsayData.getJson();
                    JSONArray jsonArray = json.getJSONObject("result").getJSONArray("station");

                    String x = jsonArray.getJSONObject(0).getString("x");
                    String y = jsonArray.getJSONObject(0).getString("y");

                }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int i, String s, API api) {

            }
        };

        result.setOnItemClickListener((parent, view, position, id) -> {
            ArrayList<Object> route = routeList.get(position);

            for(int i=0; i < route.size(); i++) {
                if (route.get(i) instanceof Bus) {
                    Bus bus = (Bus) route.get(i);
                    odsayService.requestSearchStation(bus.getBusInfo_1()[3],"1000","1","10","1","127.0363583:37.5113295",searchStation);
                } else if (route.get(i) instanceof Subway){
                    Subway subway = (Subway) route.get(i);
                    odsayService.requestSearchStation(subway.getSubwayInfo_1()[3],"1000","2","10","1","127.0363583:37.5113295",searchStation);
                }
            }
        });
    }

    public void setStartData(String placeName, double x, double y) {
        this.startData.setSearchData(placeName,x,y);
    }

    public void setEndData(String placeName, double x, double y) {
        this.endData.setSearchData(placeName,x,y);
    }

    void ODsay_TransPath_Parser(JSONObject json){

        ArrayList<ArrayList<Object>> LIST = new ArrayList<>();
        ArrayList<Traffic> content = new ArrayList<>();

        try {
            JSONArray path = json.getJSONObject("result").getJSONArray("path");

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

        }catch (Exception e){
            e.printStackTrace();
        }

        routeList = LIST;
        routeContent = content;
    }

    @Override
    public void onItemClick(View view, int position) {
        String item =  myRecyclerViewAdapter.getItem(position);

        ArrayList<ArrayList<Object>> list = new ArrayList<>();
        ArrayList<Traffic> content = new ArrayList<>();
        int length = routeContent.size();

        if(item.equals("전체")){
            ViewRouteAdapter = new ViewRouteAdapter(RouteMenuActivity.this, R.layout.item_route, routeList, routeContent);
            result.setAdapter(ViewRouteAdapter);
            relativeLayout.setVisibility(View.VISIBLE);
        }
        else if(item.equals("지하철")){
            for(int i=0; i<length; i++){
                if (Integer.parseInt(routeContent.get(i).getContent()[0]) == 1){
                    list.add(routeList.get(i));
                    content.add(routeContent.get(i));
                }
            }
            ViewRouteAdapter = new ViewRouteAdapter(RouteMenuActivity.this, R.layout.item_route, list, content);
            result.setAdapter(ViewRouteAdapter);
            relativeLayout.setVisibility(View.VISIBLE);
        }
        else if(item.equals("버스")){
            for(int i=0; i<length; i++){
                if (Integer.parseInt(routeContent.get(i).getContent()[0]) == 2){
                    list.add(routeList.get(i));
                    content.add(routeContent.get(i));
                }
            };
            ViewRouteAdapter = new ViewRouteAdapter(RouteMenuActivity.this, R.layout.item_route, list, content);
            result.setAdapter(ViewRouteAdapter);
            relativeLayout.setVisibility(View.VISIBLE);
        }
        else if(item.equals("버스+지하철")){
            for(int i=0; i<length; i++){
                if (Integer.parseInt(routeContent.get(i).getContent()[0]) == 3){
                    list.add(routeList.get(i));
                    content.add(routeContent.get(i));
                }
            }
            ViewRouteAdapter = new ViewRouteAdapter(RouteMenuActivity.this, R.layout.item_route, list, content);
            result.setAdapter(ViewRouteAdapter);
            relativeLayout.setVisibility(View.VISIBLE);
        }
    }

    class NaverAsync_Gc extends AsyncTask<String, String, Gc> {

        @Override
        protected Gc doInBackground(String... strings) {
            //각종 반복이나 제어 등 주요 로직을 담당
            return gc_parser.connectNaver(strings);
        }

        @Override
        protected void onPostExecute(Gc s) {
            //doinBackground를 통해 완료된 작업 결과 처리
            super.onPostExecute(s);
            //로그 기록
            Log.d("address", "roadAdress : " + s.getRoadAddress());
            Log.d("address", "bulidAdress : " + s.getBuildAddress());
            Log.d("address", "legalCode : " + s.getLegalCode());
            Log.d("address", "admCode : " + s.getAdmCode());

            tracking =s;

        }
    }

}


