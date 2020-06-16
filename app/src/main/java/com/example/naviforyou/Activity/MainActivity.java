package com.example.naviforyou.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;



import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.naviforyou.API.Gc;
import com.example.naviforyou.API.Gc_Parser;
import com.example.naviforyou.API.LowbusStop;
import com.example.naviforyou.API.LowbusStop_Parser;
import com.example.naviforyou.API.Search_Parser;
import com.example.naviforyou.DB.AppDatabase_Facility;
import com.example.naviforyou.DB.AppDatabase_Favorite;
import com.example.naviforyou.DB.Facility;
import com.example.naviforyou.DB.FacilityDao;
import com.example.naviforyou.DB.Facility_Parser;
import com.example.naviforyou.R;
import com.example.naviforyou.Fragment.Fragment_search;
import com.example.naviforyou.Fragment.Fragment_search2;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraUpdate;
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
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    public SharedPreferences prefs; // 최초 실행 여부 확인
    public static Activity activity;
    public static NaverMap naverMap;

    //시설 데이터 베이스 생성
   public static AppDatabase_Facility db_facility;
   public static AppDatabase_Favorite db_favorite;

    // 위치를 반환하는 FusedLocationSource 선언
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private FusedLocationSource locationSource;

    //마커 객체 생성
    Marker marker = new Marker();
    InfoWindow infoWindow = new InfoWindow();
    ImageView gps;

    //파서 객체
    Gc_Parser gc_parser = new Gc_Parser();
    Search_Parser search_parser = new Search_Parser();
    LowbusStop_Parser lowbusStop_parser = new LowbusStop_Parser();
    Gc gc;

    //1번째
    static boolean isfirst = true;
    //2번째
    boolean isSearch = false;
    double searchX = 0;
    double searchY = 0;
    String placeName;
    String roadAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = MainActivity.this;
        prefs = getSharedPreferences("Pref", MODE_PRIVATE);

        db_facility = Room.databaseBuilder(this, AppDatabase_Facility.class,
                "facility-db").build();

        db_favorite = Room.databaseBuilder(this,AppDatabase_Favorite.class,
                "favorite-db").build();

        //첫실행시 json Parser 및 데이터베이스에 추가
        checkFirstRun(db_facility);
        new GetAsyncTask(db_facility.facilityDao()).execute();
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
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();

        //Search Activity에서 검색후 검색 정보 전달
        if(intent.hasExtra("isSearch")){
            //SearchActivity
            isSearch = intent.getExtras().getBoolean("isSearch");
            searchX = intent.getExtras().getDouble("X");
            searchY = intent.getExtras().getDouble("Y");
            roadAddress = intent.getExtras().getString("RoadAddress");
            placeName = intent.getExtras().getString("PlaceName");
        }
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.naverMap = naverMap;

        //버스 정류장 표시
        naverMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_TRANSIT, true);

        // UI 설정
        UiSettings uiSettings = naverMap.getUiSettings();
        //uiSettings.setCompassEnabled(false);
        //uiSettings.setZoomControlEnabled(false);

        // FusedLocationSource을 NaverMap에 지정
        naverMap.setLocationSource(locationSource);
        //처음 카메라 위치 조정
        if(isfirst) {
            naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
            isfirst = false;
        }

        //위치 오버레이 객체 추가
        LocationOverlay locationOverlay = naverMap.getLocationOverlay();

        //오버레이 추가
        Fragment_search fragment_search = new Fragment_search();
        AtomicReference<Fragment_search2> fragment_search2 = new AtomicReference<>(new Fragment_search2());
        AtomicBoolean frag = new AtomicBoolean(true); //fragment_Search 추가 여부

        FragmentTransaction tras = getSupportFragmentManager().beginTransaction();
        tras.replace(R.id.frame, fragment_search);
        tras.commit();


        //검색 장소 클릭 후
        if(isSearch){
            Toast.makeText(this, "X : " + searchX + ",Y : " + searchY,Toast.LENGTH_SHORT).show();
            //카메라 이동
            CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(searchY, searchX));
            naverMap.moveCamera(cameraUpdate);
            // 마커
            marker.setMap(null);
            marker.setPosition(new LatLng(searchY, searchX));
            marker.setMap(naverMap);


            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            if (fragment_search2.get().isAdded()) {
                transaction.remove(fragment_search2.get());
                fragment_search2.set(new Fragment_search2());
            }

            transaction.add(R.id.frame, fragment_search2.get());
            transaction.commit();

            Bundle bundle = new Bundle();
            bundle.putString("roadAddress", roadAddress);
            bundle.putString("placeName", placeName);
            bundle.putDouble("X", searchX);
            bundle.putDouble("Y", searchY);
            bundle.putBoolean("isSearch",isSearch);
            fragment_search2.get().setArguments(bundle);

            isSearch = false;
            searchX = 0;
            searchY = 0;

        }

        //심벌 클릭
        naverMap.setOnSymbolClickListener(symbol -> {
           //Toast.makeText(this, symbol.getCaption(), Toast.LENGTH_SHORT).show();
            try {
                gc = new NaverAsync_Gc().execute(symbol.getPosition().longitude + "," + symbol.getPosition().latitude).get(); //doInBackground메서드 호출
                gc.setBuildName(symbol.getCaption());

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if (!fragment_search.isAdded()) {
                    transaction.replace(R.id.frame, fragment_search);
                }
                frag.set(false);


                if (fragment_search2.get().isAdded()) {
                    transaction.remove(fragment_search2.get());
                    fragment_search2.set(new Fragment_search2());
                }

                transaction.add(R.id.frame, fragment_search2.get());
                transaction.commit();

                Bundle bundle = new Bundle();
                bundle.putSerializable("isSearch", isSearch);
                bundle.putSerializable("Gc", gc);
                fragment_search2.get().setArguments(bundle);

                marker.setMap(null);
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
            //Toast.makeText(this, coord.latitude + ", " + coord.longitude,
            //           Toast.LENGTH_SHORT).show();

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            if (!frag.get()) {
                if(!fragment_search.isAdded()) transaction.replace(R.id.frame, fragment_search);
                frag.set(true);
            } else {
                transaction.remove(fragment_search);
                frag.set(false);
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

    //앱 첫 실행시
    public void checkFirstRun(AppDatabase_Facility db){
        boolean isFirstRun = prefs.getBoolean("isFirstRun",true);
        if(isFirstRun)
        {
            Facility_Parser facility_parser = new Facility_Parser();
            String json = facility_parser.getJsonString(this);
            ArrayList<Facility> facilityArrayList = facility_parser.Parser(json);
            for(int i = 0; i < facilityArrayList.size(); i++){
                new InsetAsyncTask(db.facilityDao()).execute(facilityArrayList);
            }
            prefs.edit().putBoolean("isFirstRun",false).apply();
            //처음만 true 그다음부터는 false 바꾸는 동작
        }
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
            Log.d("address", "roadAdress : " + s.getRoadAddress());
            Log.d("address", "bulidAdress : " + s.getBuildAddress());
            Log.d("address", "legalCode : " + s.getLegalCode());
            Log.d("address", "admCode : " + s.getAdmCode());

            gc=s;

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

    //데이터 베이스에 추가
    private static class InsetAsyncTask extends AsyncTask<ArrayList<Facility>, Void, Void>{
        private FacilityDao facilityDao;

        public InsetAsyncTask(FacilityDao facilityDao){
            this.facilityDao = facilityDao;
        }

        @Override
        protected Void doInBackground(ArrayList<Facility>... arrayLists) {
            for(int i =0; i< arrayLists[0].size(); i++){
                facilityDao.insert(arrayLists[0].get(i));
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d("TEXT", "facility : " + facilityDao.getAll().toString());
        }
    }

    //데이터 베이스 모든 데이터 읽기
    private static class GetAsyncTask extends AsyncTask<Void,Void,Void>{
        private FacilityDao facilityDao;

        public GetAsyncTask(FacilityDao facilityDao){
            this.facilityDao = facilityDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            Log.d("TEXT" ,"facility : " +facilityDao.getAll().toString());
            return null;
        }
    }



}

