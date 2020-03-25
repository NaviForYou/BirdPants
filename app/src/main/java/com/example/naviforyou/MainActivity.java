package com.example.naviforyou;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapOptions;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.LocationOverlay;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.FusedLocationSource;
import com.naver.maps.map.widget.ZoomControlView;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

    // 위치를 반환하는 FusedLocationSource 선언
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private FusedLocationSource locationSource;

    //마커 객체 생성
    Marker marker = new Marker();
    InfoWindow infoWindow = new InfoWindow();
    Button btn;

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

        btn = findViewById(R.id.me);

        //자기 위치로 돌아가는 버튼 데모
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
            return true;
            /*
            if ("서울특별시청".equals(symbol.getCaption())) {
                Toast.makeText(this, "서울시청 클릭", Toast.LENGTH_SHORT).show();
                // 이벤트 소비, OnMapClick 이벤트는 발생하지 않음
                return true;
            }
            // 이벤트 전파, OnMapClick 이벤트가 발생함
            return false;
            */
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
}
