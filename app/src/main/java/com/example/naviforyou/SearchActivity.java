package com.example.naviforyou;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    static EditText searchText;
    ListView myListView;
    Search_Parser search_parser;
    ArrayList<Search> list;
    ViewSearchAdapter adapter;
    ImageView search;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchText = findViewById(R.id.searchText);
        myListView = findViewById(R.id.myListView);
        search = findViewById(R.id.search);
        search_parser = new Search_Parser();
        final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        search.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                list = new ArrayList<>();

                adapter = null;

                if ( Build.VERSION.SDK_INT >= 23 &&
                        ContextCompat.checkSelfPermission( getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
                    ActivityCompat.requestPermissions( SearchActivity.this, new String[] {  android.Manifest.permission.ACCESS_FINE_LOCATION  },
                            0 );
                }
                else{
                    Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    String provider = location.getProvider();
                    double longitude = location.getLongitude();
                    double latitude = location.getLatitude();
                    double altitude = location.getAltitude();

                    /*
                    위치 정보 업데이트
                    lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                            1000,
                            1,
                            gpsLocationListener);
                    lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                            1000,
                            1,
                            gpsLocationListener);

                     */
                    if(searchText.getText().toString().length() != 0)
                        new KakaoAsync_Search().execute(String.valueOf(longitude), String.valueOf(latitude),searchText.getText().toString());
                }





            }
        });
    }

    //위치 리스너 구현
    final LocationListener gpsLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {

            String provider = location.getProvider();
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            double altitude = location.getAltitude();

        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onProviderDisabled(String provider) {
        }
    };



    //통신을 위한 백그라운드 작업 설정 - 검색
    class KakaoAsync_Search extends AsyncTask<String, String, ArrayList<Search>> {

        @Override
        protected ArrayList<Search> doInBackground(String... strings) {
            return search_parser.connectKakao(strings); // x,y 좌표, 검색어
        }

        @Override
        protected void onPostExecute(ArrayList<Search> list) {
            //doinBackground를 통해 완료된 작업 결과 처리
            super.onPostExecute(list);
            //로그 기록
            if(adapter == null){
                adapter = new ViewSearchAdapter( SearchActivity.this, R.layout.search_item, list);

                myListView.setAdapter(adapter);
            }
        }
    }
}

