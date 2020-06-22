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


import com.example.naviforyou.API.IsBusStop;
import com.example.naviforyou.API.IsBusStop_Parser;
import com.example.naviforyou.API.Gc;
import com.example.naviforyou.API.Gc_Parser;
import com.example.naviforyou.DB.DataBase.AppDatabase_Facility;
import com.example.naviforyou.DB.DataBase.AppDatabase_Favorite;
import com.example.naviforyou.DB.DataBase.AppDatabase_Runway;
import com.example.naviforyou.DB.Data.Facility;
import com.example.naviforyou.DB.DataBase.AppDatabase_Transfer;
import com.example.naviforyou.DB.DataBase.FacilityDao;
import com.example.naviforyou.DB.Data.Facility_Parser;
import com.example.naviforyou.DB.Data.Runway;
import com.example.naviforyou.DB.DataBase.RunwayDao;
import com.example.naviforyou.DB.Data.Runway_Parser;
import com.example.naviforyou.Data.IsSubwayStop;
import com.example.naviforyou.Fragment.BusInformationFragment;
import com.example.naviforyou.Fragment.SubwayInformationFragment;
import com.example.naviforyou.R;
import com.example.naviforyou.Fragment.SearchFragment;
import com.example.naviforyou.Fragment.InformationFragment;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
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
    public static AppDatabase_Runway db_runway;
    public static AppDatabase_Transfer db_transfer;

    // 위치를 반환하는 FusedLocationSource 선언
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private FusedLocationSource locationSource;

    //마커 객체 생성
    Marker marker = new Marker();
    InfoWindow infoWindow = new InfoWindow();

    //파서 객체
    Gc_Parser gc_parser = new Gc_Parser();
    IsBusStop_Parser isBusStop_parser = new IsBusStop_Parser();
    Gc gc;
    IsBusStop isBusStop;
    IsSubwayStop isSubwayStop;

    //1번째
    static boolean isfirst = true;
    //2번째
    boolean isSearch = false;
    boolean isRoute = false;
    double searchX = 0;
    double searchY = 0;
    String placeName;
    String roadAddress;
    String stationId;
    String routeType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = MainActivity.this;
        prefs = getSharedPreferences("Pref", MODE_PRIVATE);

        db_facility = Room.databaseBuilder(this, AppDatabase_Facility.class,
                "facility-db")
                .fallbackToDestructiveMigration()
                .build();

        db_favorite = Room.databaseBuilder(this,AppDatabase_Favorite.class,
                "favorite-db")
                .fallbackToDestructiveMigration()
                .build();

        db_runway = Room.databaseBuilder(this, AppDatabase_Runway.class,"runway-db")
                .fallbackToDestructiveMigration()
                .build();

        //첫실행시 json Parser 및 데이터베이스에 추가
        checkFirstRun();
        new GetAsyncTask(db_facility.facilityDao()).execute();

        //NaverMap 객체 얻어오기 - api 호출하는 인터페이스 역할을 함
        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment)fm.findFragmentById(R.id.map);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().add(R.id.map, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);

        db_favorite.favoriteDao().getAll().observe(this,favorites -> {
            Log.d("TEXT","Favorite : " + favorites.toString());
        });

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

        if(intent.hasExtra("isRoute")){
            //FindMapActivity
            isRoute = intent.getExtras().getBoolean("isRoute");
            searchX = intent.getExtras().getDouble("X");
            searchY = intent.getExtras().getDouble("Y");
            placeName = intent.getExtras().getString("name");
            stationId = intent.getExtras().getString("id");
            routeType = intent.getExtras().getString("type");
        }
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.naverMap = naverMap;

        //버스 정류장 표시
        naverMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_TRANSIT, true);

        // UI 설정
        //UiSettings uiSettings = naverMap.getUiSettings();
        //uiSettings.setCompassEnabled(false);
        //uiSettings.setZoomControlEnabled(false);

        // FusedLocationSource을 NaverMap에 지정
        naverMap.setLocationSource(locationSource);

        //처음 카메라 위치 조정
        if(isfirst) {
            naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
            CameraUpdate cameraUpdate = CameraUpdate.zoomTo(16.5);
            naverMap.moveCamera(cameraUpdate);
            isfirst = false;
        }

        //위치 오버레이 객체 추가
        LocationOverlay locationOverlay = naverMap.getLocationOverlay();

        //오버레이 추가
        SearchFragment searchFragment_ = new SearchFragment();
        AtomicReference<InformationFragment> fragment_imformation = new AtomicReference<>(new InformationFragment());
        AtomicReference<BusInformationFragment> fragment_Bus= new AtomicReference<>(new BusInformationFragment());
        AtomicReference<SubwayInformationFragment> fragment_subway= new AtomicReference<>(new SubwayInformationFragment());
        AtomicBoolean frag = new AtomicBoolean(true); //fragment_Search 추가 여부

        FragmentTransaction tras = getSupportFragmentManager().beginTransaction();
        tras.replace(R.id.frame, searchFragment_);
        tras.commit();


        //검색 장소 클릭 후
        if(isSearch){

            //카메라 이동
            CameraUpdate cameraUpdate = CameraUpdate.scrollAndZoomTo(new LatLng(searchY, searchX),16.5);
            naverMap.moveCamera(cameraUpdate);
            // 마커
            marker.setMap(null);
            marker.setPosition(new LatLng(searchY, searchX));
            marker.setMap(naverMap);


            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            if (fragment_imformation.get().isAdded()) {
                transaction.remove(fragment_imformation.get());
                fragment_imformation.set(new InformationFragment());
            }

            transaction.add(R.id.frame, fragment_imformation.get());
            transaction.commit();

            Bundle bundle = new Bundle();
            bundle.putString("roadAddress", roadAddress);
            bundle.putString("placeName", placeName);
            bundle.putDouble("X", searchX);
            bundle.putDouble("Y", searchY);
            bundle.putBoolean("isSearch",isSearch);
            fragment_imformation.get().setArguments(bundle);

            isSearch = false;
            searchX = 0;
            searchY = 0;

        }

        Log.d("TEXT","isRoute" + isRoute);
        //길찾기 후
        if(isRoute){
            //카메라 이동
            CameraUpdate cameraUpdate = CameraUpdate.scrollAndZoomTo(new LatLng(searchY, searchX),16.5);
            naverMap.moveCamera(cameraUpdate);
            // 마커
            marker.setMap(null);
            marker.setPosition(new LatLng(searchY, searchX));
            marker.setMap(naverMap);


            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            Bundle bundle = new Bundle();
            try {
                switch (routeType) {
                    case "start":
                    case "end":
                        if (fragment_imformation.get().isAdded()) {
                            transaction.remove(fragment_imformation.get());
                            fragment_imformation.set(new InformationFragment());
                        }

                        if (fragment_Bus.get().isAdded()) {
                            transaction.remove(fragment_Bus.get());
                        }

                        if (fragment_subway.get().isAdded()){
                            transaction.remove(fragment_subway.get());
                        }

                        transaction.add(R.id.frame, fragment_imformation.get());

                        gc = new NaverAsync_Gc().execute(searchX + "," + searchY).get(); //doInBackground메서드 호출
                        gc.setBuildName(placeName);

                        transaction.commit();

                        bundle.putSerializable("isSearch", isSearch);
                        bundle.putSerializable("Gc",gc);
                        fragment_imformation.get().setArguments(bundle);

                        break;
                    case "bus":
                        isBusStop = new SeoulAsync_IsBusStop().execute(String.valueOf(searchY),String.valueOf(searchX)).get();
                        if (isBusStop != null) {
                            if (fragment_imformation.get().isAdded()) {
                                transaction.remove(fragment_imformation.get());
                            }

                            if (fragment_subway.get().isAdded()) {
                                transaction.remove(fragment_subway.get());
                            }

                            if (fragment_Bus.get().isAdded()) {
                                transaction.remove(fragment_Bus.get());
                                fragment_Bus.set(new BusInformationFragment());
                            }

                            isBusStop.setX(searchX);
                            isBusStop.setY(searchY);
                            bundle.putSerializable("bus", isBusStop);
                            transaction.add(R.id.frame, fragment_Bus.get());
                            transaction.commit();
                            fragment_Bus.get().setArguments(bundle);
                        }
                        break;
                    case "subway":
                        if (fragment_imformation.get().isAdded()){
                            transaction.remove(fragment_imformation.get());
                        }

                        if (fragment_subway.get().isAdded()){
                            transaction.remove(fragment_subway.get());
                            fragment_subway.set(new SubwayInformationFragment());
                        }

                        if (fragment_Bus.get().isAdded()) {
                            transaction.remove(fragment_Bus.get());
                        }

                        isSubwayStop = new IsSubwayStop();
                        isSubwayStop.setX(searchX);
                        isSubwayStop.setY(searchY);
                        bundle.putSerializable("subway",isSubwayStop);
                        transaction.add(R.id.frame, fragment_subway.get());
                        transaction.commit();
                        fragment_subway.get().setArguments(bundle);
                        break;
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            isRoute = false;
            searchX = 0;
            searchY = 0;

        }

        //심벌 클릭
        naverMap.setOnSymbolClickListener(symbol -> {
           //Toast.makeText(this, symbol.getCaption(), Toast.LENGTH_SHORT).show();
            try {
                isBusStop =null;
                isBusStop = new SeoulAsync_IsBusStop().execute(String.valueOf(symbol.getPosition().latitude),String.valueOf(symbol.getPosition().longitude)).get();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                if(isBusStop != null){

                    if (fragment_Bus.get().isAdded()) {
                        transaction.remove(fragment_Bus.get());
                        fragment_Bus.set(new BusInformationFragment());
                    }

                    if (fragment_imformation.get().isAdded()){
                        transaction.remove(fragment_imformation.get());
                    }

                    if (fragment_subway.get().isAdded()){
                        transaction.remove(fragment_subway.get());
                    }

                    transaction.add(R.id.frame, fragment_Bus.get());
                    transaction.commit();

                    isBusStop.setY(symbol.getPosition().latitude);
                    isBusStop.setX(symbol.getPosition().longitude);

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("bus",isBusStop);
                    fragment_Bus.get().setArguments(bundle);


                }else {
                    String temp = symbol.getCaption();

                    if (temp.length() != 0) {
                        if (temp.substring(temp.length() - 1).equals("역")) {
                            if (fragment_Bus.get().isAdded()) {
                                transaction.remove(fragment_Bus.get());
                            }

                            if (fragment_imformation.get().isAdded()) {
                                transaction.remove(fragment_imformation.get());
                            }

                            if (fragment_subway.get().isAdded()){
                                transaction.remove(fragment_subway.get());
                                fragment_subway.set(new SubwayInformationFragment());
                            }

                            isSubwayStop = new IsSubwayStop();
                            isSubwayStop.setStation(temp);
                            isSubwayStop.setX(symbol.getPosition().longitude);
                            isSubwayStop.setY(symbol.getPosition().latitude);
                            transaction.add(R.id.frame, fragment_subway.get());
                            transaction.commit();
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("subway",isSubwayStop);
                            fragment_subway.get().setArguments(bundle);


                        } else {
                            gc = new NaverAsync_Gc().execute(symbol.getPosition().longitude + "," + symbol.getPosition().latitude).get(); //doInBackground메서드 호출
                            gc.setBuildName(symbol.getCaption());

                            if (fragment_Bus.get().isAdded()) {
                                transaction.remove(fragment_Bus.get());
                            }

                            if (fragment_subway.get().isAdded()){
                                transaction.remove(fragment_subway.get());
                            }

                            if (fragment_imformation.get().isAdded()) {
                                transaction.remove(fragment_imformation.get());
                                fragment_imformation.set(new InformationFragment());
                            }

                            transaction.add(R.id.frame, fragment_imformation.get());
                            transaction.commit();


                            Bundle bundle = new Bundle();
                            bundle.putSerializable("isSearch", isSearch);
                            bundle.putSerializable("Gc", gc);
                            fragment_imformation.get().setArguments(bundle);
                        }
                    }
                }


                if (!searchFragment_.isAdded()) {
                    transaction.replace(R.id.frame, searchFragment_);
                }
                frag.set(false);

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
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            if (!frag.get()) {
                if(!searchFragment_.isAdded()) transaction.replace(R.id.frame, searchFragment_);
                frag.set(true);
            } else {
                transaction.remove(searchFragment_);
                frag.set(false);
            }

            if (fragment_imformation.get().isAdded()){
                transaction.remove(fragment_imformation.get());
            }

            if (fragment_Bus.get().isAdded()){
                transaction.remove(fragment_Bus.get());
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
    public void checkFirstRun(){
        boolean isFirstRun = prefs.getBoolean("isFirstRun",true);
        Log.d("IsFirst","IsFirst"+isFirstRun);
        if(isFirstRun)
        {
            Facility_Parser facility_parser = new Facility_Parser();
            String json = facility_parser.getJsonString(this);
            ArrayList<Facility> facilityArrayList = facility_parser.Parser(json);
            for(int i = 0; i < facilityArrayList.size(); i++){
                new InsetAsyncTask(db_facility.facilityDao()).execute(facilityArrayList);
            }
            new GetAsyncTask(db_facility.facilityDao()).execute();

            Runway_Parser runway_parser = new Runway_Parser();
            String json2 = runway_parser.getJsonString(this);
            ArrayList<Runway> runwayArrayList = runway_parser.Parser(json2);
            for (int i = 0; i < runwayArrayList.size(); i++){
                new RunWayInsert(db_runway.runwayDao()).execute(runwayArrayList);
            }

            new RunWayGetAll(db_runway.runwayDao()).execute();

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

    //통신을 위한 백그라운드 작업 설정 - 버스 정류장 여부 확인
    class SeoulAsync_IsBusStop extends AsyncTask<String, String, IsBusStop> {

        @Override
        protected IsBusStop doInBackground(String... strings) {
            return isBusStop_parser.connectSeoul(strings);
        }

        @Override
        protected void onPostExecute(IsBusStop s) {
            //doinBackground를 통해 완료된 작업 결과 처리
            super.onPostExecute(s);
            //로그 기록
            if (s != null){
                Log.d("BusStop",s.getId());
                Log.d("BusStop",s.getName());
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

    private static class RunWayInsert extends AsyncTask<ArrayList<Runway>, Void, Void>{
        private RunwayDao runwayDao;

        public RunWayInsert(RunwayDao runwayDao) { this.runwayDao = runwayDao; }

        @Override
        protected Void doInBackground(ArrayList<Runway>... arrayLists) {
            for(int i =0; i< arrayLists[0].size(); i++){
                runwayDao.insert(arrayLists[0].get(i));
            }
            return null;
        }
    }

    private static class RunWayGetAll extends AsyncTask<Void, Void, Void>{
        private RunwayDao runwayDao;

        public RunWayGetAll(RunwayDao runwayDao) { this.runwayDao = runwayDao; }


        @Override
        protected Void doInBackground(Void... voids) {
            Log.d("TEXT" ,"Runway : " +runwayDao.getAll().toString());
            return null;
        }
    }

}

