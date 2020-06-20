package com.example.naviforyou.Fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.example.naviforyou.API.Gc;
import com.example.naviforyou.Activity.MainActivity;
import com.example.naviforyou.Activity.RouteMenuActivity;
import com.example.naviforyou.DB.AppDatabase_Facility;
import com.example.naviforyou.DB.AppDatabase_Favorite;
import com.example.naviforyou.DB.Facility;
import com.example.naviforyou.DB.FacilityDao;
import com.example.naviforyou.DB.Favorite;
import com.example.naviforyou.DB.FavoriteDao;
import com.example.naviforyou.R;

import java.util.List;
import java.util.concurrent.ExecutionException;


public class Fragment_search2 extends Fragment {

    ImageView favorite;
    ImageView start;
    ImageView end;

    TextView place_name;
    TextView place_address;

    // True = 검색 False = 심벌클릭
    Boolean isSearch = false;

    // 심벌
    Gc gc;
    List<Facility> facilities;


    // 검색
    String placeName;
    String roadAddress;
    double X;
    double Y;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        AppDatabase_Facility db_facility = MainActivity.db_facility;
        AppDatabase_Favorite db_fFavorite = MainActivity.db_favorite;
        facilities = null;

        // 텍스트 추가
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.fragment_search2, container, false);

        place_name = (TextView) layout.findViewById(R.id.place_name);
        place_address = (TextView) layout.findViewById(R.id.place_address);

        //데이터 받기
        Bundle bundle = getArguments();
        isSearch = bundle.getBoolean("isSearch");

        //true : 검색, false : 심벌 클릭
        if(isSearch) {
            placeName = bundle.getString("placeName");
            roadAddress = bundle.getString("roadAddress");
            X = bundle.getDouble("X");
            Y = bundle.getDouble("Y");
            Log.d("TEXT","TEXT : X : " + X + " Y : " + Y);
            place_name.setText(placeName);
            place_address.setText(roadAddress);
        }else {
            gc = (Gc) bundle.getSerializable("Gc");
            place_name.setText(gc.getBuildName());
            place_address.setText(gc.getRoadAddress());
        }

        // 시설 여부 확인
        if(isSearch) {
            Log.d("XY","XY : " + X +","+Y);
            new FacilitySearch(db_facility.facilityDao()).execute(X,Y);
        }
        else {
            Log.d("XY","XY : " + gc.getX() +","+gc.getY());
            new FacilitySearch(db_facility.facilityDao()).execute(Double.parseDouble(gc.getX()),Double.parseDouble(gc.getY()));
        }



        // 출발 버튼 클릭
        start = (ImageView)layout.findViewById(R.id.start);
        start.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), RouteMenuActivity.class);
            intent.putExtra("type","start");
            if(isSearch) {
                intent.putExtra("start", placeName);
                intent.putExtra("startX", String.valueOf(X));
                intent.putExtra("startY", String.valueOf(Y));
            }else {
                intent.putExtra("start", gc.getBuildName());
                intent.putExtra("startX", gc.getX());
                intent.putExtra("startY", gc.getY());
            }
            startActivity(intent);
        });

        // 도착 버튼 클릭
        end = (ImageView)layout.findViewById(R.id.end);
        end.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), RouteMenuActivity.class);
            intent.putExtra("type","end");
            if(isSearch) {
                intent.putExtra("end", placeName);
                intent.putExtra("endX", X);
                intent.putExtra("endY", Y);
            }else {
                intent.putExtra("end", gc.getBuildName());
                intent.putExtra("endX", gc.getX());
                intent.putExtra("endY", gc.getY());
            }
            startActivity(intent);
        });

        //즐겨찾기 버튼 클릭
        favorite = (ImageView)layout.findViewById(R.id.favorite);
        favorite.setOnClickListener(v -> {
            Favorite favorite;
            if(isSearch) {
                favorite = new Favorite(placeName,placeName,roadAddress,X,Y);
            }else{
                favorite = new Favorite(
                        gc.getBuildName(),gc.getBuildName(),gc.getRoadAddress(),
                        Double.parseDouble(gc.getX()), Double.parseDouble(gc.getY()));;
            }
            new FavoriteInsert(db_fFavorite.favoriteDao()).execute(favorite);
        });

        db_fFavorite.favoriteDao().getAll().observe(this, favorites -> {
            Log.d("FavoriteList",favorites.toString());
        });

        //괄호안이 데이터 값
        return layout;
    }

    //Facility 데이터 베이스 검색
    private static class FacilitySearch extends AsyncTask<Double,Void,List<Facility>> {
        private FacilityDao facilityDao;

        public FacilitySearch(FacilityDao facilityDao){
            this.facilityDao = facilityDao;
        }
        @Override
        protected List<Facility> doInBackground(Double... doubles) {
            List<Facility> facility = facilityDao.findWithXY(doubles[0],doubles[1]);
            return facility;
        }

        @Override
        protected void onPostExecute(List<Facility> facility) {
            super.onPostExecute(facility);
            if(!facility.isEmpty()) {
                Log.d("TEXT", "fragment_search2 : " + facility.get(0).toString());
            }
        }
    }

    //Favorite 데이터 베이스 추가
    private static class FavoriteInsert extends AsyncTask<Favorite,Void,Void>{
        private FavoriteDao favoriteDao;

        public FavoriteInsert(FavoriteDao favoriteDao){ this.favoriteDao = favoriteDao; }
        @Override
        protected Void doInBackground(Favorite... favorites) {
            favoriteDao.insert(favorites[0]);
            return null;
        }
    }

}