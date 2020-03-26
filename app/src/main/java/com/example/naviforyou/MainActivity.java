package com.example.naviforyou;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

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

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

    // 위치를 반환하는 FusedLocationSource 선언
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private FusedLocationSource locationSource;

    //마커 객체 생성
    Marker marker = new Marker();
    InfoWindow infoWindow = new InfoWindow();
    Button btn;

    Gc_Parser gc_parser = new Gc_Parser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
            return true;
        });

        //클릭
        naverMap.setOnMapClickListener((point, coord) ->
                Toast.makeText(this, coord.latitude + ", " + coord.longitude,
                        Toast.LENGTH_SHORT).show());


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
            Log.d("address","roadAdress : " + s.getRoadAdress());
            Log.d("address","bulidAdress : " + s.getBulidAdress());
            Log.d("address","legalCode : " + s.getLegalCode());
            Log.d("address","admCode : " + s.getAdmCode());

        }
    }
}
