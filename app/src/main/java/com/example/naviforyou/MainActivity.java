package com.example.naviforyou;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;



import androidx.fragment.app.FragmentTransaction;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    // 위치를 반환하는 FusedLocationSource 선언
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private FusedLocationSource locationSource;

    //마커 객체 생성
    Marker marker = new Marker();
    InfoWindow infoWindow = new InfoWindow();
    Button btn;

    //파서 객체
    Gc_Parser gc_parser = new Gc_Parser();
    Search_Parser search_parser = new Search_Parser();
    LowbusStop_Parser lowbusStop_parser = new LowbusStop_Parser();
    Gc gc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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




    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        //버스 정류장 표시
        naverMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_TRANSIT, true);

        // UI 설정
        UiSettings uiSettings = naverMap.getUiSettings();

        // FusedLocationSource을 NaverMap에 지정
        naverMap.setLocationSource(locationSource);
        naverMap.setLocationTrackingMode(LocationTrackingMode.NoFollow);

        //위치 오버레이 객체 추가
        LocationOverlay locationOverlay = naverMap.getLocationOverlay();

        //오버레이 추가


        Log.i(this.getClass().getName(), String.valueOf(uiSettings.isCompassEnabled()));

        //자기 위치로 돌아가는 버튼 데모
        btn = findViewById(R.id.me);
        btn.setOnClickListener(v -> {
            if (naverMap.getLocationTrackingMode() == LocationTrackingMode.NoFollow) {
                naverMap.setLocationTrackingMode(LocationTrackingMode.Face);
            }
            else {
                naverMap.setLocationTrackingMode(LocationTrackingMode.NoFollow);
            }
        });

        fragment_search fragment_search = new fragment_search();
        AtomicReference<fragment_search2> fragment_search2 = new AtomicReference<>(new fragment_search2());



        //심벌 클릭
        naverMap.setOnSymbolClickListener(symbol -> {
            Toast.makeText(this, symbol.getCaption(), Toast.LENGTH_SHORT).show();
            try {
                gc = new NaverAsync_Gc().execute(symbol.getPosition().longitude + "," + symbol.getPosition().latitude).get(); //doInBackground메서드 호출
                gc.setBuildName(symbol.getCaption());

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if (!fragment_search.isAdded()) {
                    transaction.replace(R.id.frame, fragment_search);
                }


                if (fragment_search2.get().isAdded()) {
                    transaction.remove(fragment_search2.get());
                    fragment_search2.set(new fragment_search2());
                }

                transaction.add(R.id.frame, fragment_search2.get());
                transaction.commit();

                Bundle bundle = new Bundle();
                bundle.putSerializable("Gc", gc);
                fragment_search2.get().setArguments(bundle);

                marker.setPosition(new LatLng(symbol.getPosition().latitude, symbol.getPosition().longitude));
                marker.setMap(naverMap);

            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }




            return true;
        });


        //클릭
        naverMap.setOnMapClickListener((point, coord) -> {
            Toast.makeText(this, coord.latitude + ", " + coord.longitude,
                        Toast.LENGTH_SHORT).show();

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            if (!fragment_search.isAdded()) {
                transaction.replace(R.id.frame, fragment_search);
            } else {
                transaction.remove(fragment_search);
            }

            if (fragment_search2.get().isAdded()){
                transaction.remove(fragment_search2.get());
            }

            transaction.commit();

            marker.setMap(null);
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

    //통신을 위한 백그라운드 작업 설정 - 심벌 정보
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
            Log.d("address", "roadAdress : " + s.getRoadAdress());
            Log.d("address", "bulidAdress : " + s.getBulidAdress());
            Log.d("address", "legalCode : " + s.getLegalCode());
            Log.d("address", "admCode : " + s.getAdmCode());

            gc=s;

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
            for (int i = 0; i < s.size(); i++) {
                Log.d("address", "PlaceName : " + s.get(i).getPlaceName());
                Log.d("address", "BulidAdress : " + s.get(i).getBulidAddress());
                Log.d("address", "RoadAddress : " + s.get(i).getRoadAddress());
                Log.d("address", "Phone_number : " + s.get(i).getPhone_number());
                Log.d("address", "Distance : " + s.get(i).getDistancs());
            }
        }
    }

    //통신을 위한 백그라운드 작업 설정 - 버스 정류장 도착 정보
    class SeoulAsync_LowbusStop extends AsyncTask<String, String, ArrayList<LowbusStop>> {

        @Override
        protected ArrayList<LowbusStop> doInBackground(String... strings) {
            return lowbusStop_parser.connectSeoul(strings);
        }

        @Override
        protected void onPostExecute(ArrayList<LowbusStop> s) {
            //doinBackground를 통해 완료된 작업 결과 처리
            super.onPostExecute(s);
            //로그 기록
            for (int i = 0; i < s.size(); i++) {
                Log.d("Lowbus", "rtName : " + s.get(i).getRtName());
                Log.d("Lowbus", "arrmsg1 : " + s.get(i).getTime1());
                Log.d("Lowbus", "arrmsg2 : " + s.get(i).getTime2());
            }
        }

    }


}

