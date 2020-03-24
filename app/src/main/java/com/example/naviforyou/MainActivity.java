package com.example.naviforyou;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.LocationOverlay;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.FusedLocationSource;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

    // 위치를 반환하는 FusedLocationSource 선언
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private FusedLocationSource locationSource;

    //마커 객체 생성
    Marker maker = new Marker();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //NaverMap 객체 얻어오기 - api 호출하는 인터페이스 역할을 함
        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment)fm.findFragmentById(R.id.map_fragment);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().add(R.id.map_fragment, mapFragment).commit();
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
        uiSettings.setCompassEnabled(true); // 나침반 활성화 여부
        uiSettings.setScaleBarEnabled(true); // 축적바
        uiSettings.setZoomControlEnabled(true); //줌버튼
        uiSettings.setLocationButtonEnabled(true); //현위치 버튼

        // FusedLocationSource을 NaverMap에 지정정
       naverMap.setLocationSource(locationSource);
       naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);

       //위치 오버레이 객체 추가
       LocationOverlay locationOverlay = naverMap.getLocationOverlay();

       //오버레이 추가
        maker.setMap(naverMap);
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
