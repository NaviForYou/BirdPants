package com.example.naviforyou;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;
import android.widget.Toast;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.LocationOverlay;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.FusedLocationSource;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

    // 위치를 반환하는 FusedLocationSource 선언
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private FusedLocationSource locationSource;

    //마커 객체 생성
    Marker marker = new Marker();
    InfoWindow infoWindow = new InfoWindow();
    Button btn;

    private TextView mTextViewResult;
    private RequestQueue mQueue;
    //파서 객체
    Gc_Parser gc_parser = new Gc_Parser();
    Search_Parser search_parser = new Search_Parser();
    LowbusStop_Parser lowbusStop_parser = new LowbusStop_Parser();
    Gc gc;

    Button datebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        datebase = findViewById(R.id.datebase);
        // 누나꺼 부분 버튼 이름: datebase로 변경
        /*
        mTextViewResult = findViewById(R.id.text_view_result);
        Button buttonParse = findViewById(R.id.button_parse);

        mQueue = Volley.newRequestQueue( this);

        buttonParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonParse();
            }
        })
         */

        // 검색 기능 추가되면 넣기
        /*
        String tempX ="127.06283102249932";
        String tempY ="37.514322572335935";
        new KakaoAsync_Search().execute(tempX,tempY);
        */

        // 버스 정류소 저상버스 도착
        /*
        SeoulAsync_LowbusStop seoulAsync_lowbusStop = new SeoulAsync_LowbusStop();
        seoulAsync_lowbusStop.execute("22167");
         */

        //NaverMap 객체 얻어오기 - api 호출하는 인터페이스 역할을 함
        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment)fm.findFragmentById(R.id.map);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().add(R.id.map, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);

        // 위치를 반환하는 FusedLocationSource 선언
        locationSource =
                new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);

        datebase.setOnClickListener(v -> {
            Log.d("address","gc : roadAdress : " + gc.getRoadAdress());
            Log.d("address","gc : bulidAdress : " + gc.getBulidAdress());
            Log.d("address","gc : legalCode : " + gc.getLegalCode());
            Log.d("address","gc : admCode : " + gc.getAdmCode());
        }
        );
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        //버스 정류장 표시
        naverMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_TRANSIT,true);

        // UI 설정
        UiSettings uiSettings = naverMap.getUiSettings();

        // FusedLocationSource을 NaverMap에 지정
        naverMap.setLocationSource(locationSource);
        naverMap.setLocationTrackingMode(LocationTrackingMode.NoFollow);

        //위치 오버레이 객체 추가
        LocationOverlay locationOverlay = naverMap.getLocationOverlay();

        //오버레이 추가
        marker.setPosition(new LatLng(37.5670135, 126.9783740));
        marker.setMap(naverMap);
        infoWindow.open(marker);

        Log.i(this.getClass().getName(), String.valueOf(uiSettings.isCompassEnabled()));

        //자기 위치로 돌아가는 버튼 데모
        btn = findViewById(R.id.me);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(naverMap.getLocationTrackingMode() == LocationTrackingMode.NoFollow){
                    naverMap.setLocationTrackingMode(LocationTrackingMode.Face);
                }else{
                    naverMap.setLocationTrackingMode(LocationTrackingMode.NoFollow);
                }
            }
        });

        //심벌 클릭
        naverMap.setOnSymbolClickListener(symbol -> {
            Toast.makeText(this, symbol.getCaption(), Toast.LENGTH_SHORT).show();
            new NaverAsync_Gc().execute(symbol.getPosition().longitude + "," +symbol.getPosition().latitude); //doInBackground메서드 호출
            Log.i("LAT", String.valueOf(symbol.getPosition()));

            return true;
        });

        //클릭
        naverMap.setOnMapClickListener((point, coord) -> {
            Toast.makeText(this, coord.latitude + ", " + coord.longitude,
                    Toast.LENGTH_SHORT).show();
            datebase.setVisibility(View.GONE);
        });


    }

    //위치 권한 허용
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (locationSource.onRequestPermissionsResult(
                requestCode, permissions, grantResults)) {
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //통신을 위한 백그라운드 작업 설정 - 심벌 정보, 주소 값
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
            Log.d("address","roadAdress : " + s.getRoadAdress());
            Log.d("address","bulidAdress : " + s.getBulidAdress());
            Log.d("address","legalCode : " + s.getLegalCode());
            Log.d("address","admCode : " + s.getAdmCode());

            gc = s;
            datebase.setVisibility(View.VISIBLE);
        }
    }

    //통신을 위한 백그라운드 작업 설정 - 검색
    class KakaoAsync_Search extends AsyncTask<String, String, ArrayList<Search>> {

        @Override
        protected ArrayList<Search> doInBackground(String... strings) {
            return search_parser.connectKakao(strings); // x,y 좌표
        }

        @Override
        protected void onPostExecute(ArrayList<Search> s) {
            //doinBackground를 통해 완료된 작업 결과 처리
            super.onPostExecute(s);
            //로그 기록
            for(int i = 0; i < s.size(); i++) {
                Log.d("address", "PlaceName : " + s.get(i).getPlaceName());
                Log.d("address", "BulidAdress : " + s.get(i).getBulidAddress());
                Log.d("address", "RoadAddress : " + s.get(i).getRoadAddress());
                Log.d("address", "Phone_number : " + s.get(i).getPhone_number());
                Log.d("address", "Distance : " + s.get(i).getDistancs());
            }
        }
    }

    //통신을 위한 백그라운드 작업 설정 - 버스 정류장 도착 정보
    class SeoulAsync_LowbusStop extends AsyncTask<String, String, ArrayList<LowbusStop>>{

        @Override
        protected ArrayList<LowbusStop> doInBackground(String... strings) {
            return lowbusStop_parser.connectSeoul(strings);
        }

        @Override
        protected void onPostExecute(ArrayList<LowbusStop> s) {
            //doinBackground를 통해 완료된 작업 결과 처리
            super.onPostExecute(s);
            //로그 기록
            for(int i = 0; i < s.size(); i++) {
                Log.d("Lowbus", "rtName : " + s.get(i).getRtName());
                Log.d("Lowbus", "arrmsg1 : " + s.get(i).getTime1());
                Log.d("Lowbus", "arrmsg2 : " + s.get(i).getTime2());
            }
        }
    }


    private void jsonParse()  {
        String url = "https://api.myjson.com/bins/7piyk";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("DATA");

                            for (int i=0; i<jsonArray.length(); i++) {
                                JSONObject DATA = jsonArray.getJSONObject(i);

                                String course1 = DATA.getString("course1");
                                String course2 = DATA.getString("course2");
                                String station = DATA.getString("station");
                                int no = DATA.getInt("no");
                                String telno_info = DATA.getString("telno_info");
                                String station_image = DATA.getString("station_image");
                                String exit_info = DATA.getString("exit_info");
                                String line = DATA.getString("line");
                                String icon_path = DATA.getString("icon_path");
                                String elevater = DATA.getString("elevater");
                                String elevater_txt = DATA.getString("elevater_txt");
                                String time_info2 = DATA.getString("time_info2");
                                String time_info3 = DATA.getString("time_info3");
                                String line_name = DATA.getString("line_name");
                                String time_info1 = DATA.getString("time_info1");
                                String station_image2 = DATA.getString("station_image2");
                                String time_info4 = DATA.getString("time_info4");
                                String useyn  = DATA.getString("useyn");
                                mTextViewResult.append( "운행방향 1: "+course1 + "\n" + "운행방향2: "+course2 + "\n" + "역 명칭: "+station + "\n"
                                        +"고유번호: "+ String.valueOf(no) + "\n" + "전화번호 안내: " + telno_info + "\n" + "역 이미지: "+ station_image + "\n" +
                                        "출구 정보: "+exit_info + "\n " + "노선: "+ line + "\n"+ "편의시설 아이콘 이미지 목록: "+icon_path + "\n" + "엘리베이터 동영상: "+elevater + "\n" +
                                        "엘리베이터 자막: "+ elevater_txt + "\n" + " 코스 1 막차 : "+ time_info2 + "\n" + "코스 2 첫차: " + time_info3 + "\n" + "노선 명칭: " + line_name +
                                        "\n " + "코스 1 첫차: "+ time_info1 + "\n" + "지하철 팝업 이미지: "+ station_image2 + "\n" + "코스 2 막차: " + time_info4 + "\n" + "사용여부: " + useyn + "\n\n\n");

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);

    }


}

