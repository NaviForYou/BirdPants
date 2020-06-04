package com.example.naviforyou;

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

import com.example.naviforyou.API.Gc;
import com.example.naviforyou.Activity.MainActivity;
import com.example.naviforyou.Activity.RouteMenuActivity;
import com.example.naviforyou.DB.AppDatebase;
import com.example.naviforyou.DB.Facility;
import com.example.naviforyou.DB.FacilityDao;

import java.util.List;
import java.util.concurrent.ExecutionException;


public class Fragment_search2 extends Fragment {

    ImageView route;
    ImageView start;
    ImageView end;

    TextView place_name;
    TextView place_address;

    // True = 검색 False = 심벌클릭
    Boolean isSearch = false;

    // 심벌
    Gc gc;
    List<Facility> facility;


    // 검색
    String placeName;
    String roadAddress;
    double X;
    double Y;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        AppDatebase db = MainActivity.db;

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

            place_name.setText(placeName);
            place_address.setText(roadAddress);
        }else {
            gc = (Gc) bundle.getSerializable("Gc");
            place_name.setText(gc.getBuildName());
            place_address.setText(gc.getRoadAddress());
        }

        /*try {

        }
        catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        if(isSearch) {
            new SearchAsyncTask(db.facilityDao()).execute(roadAddress);
        }
        else {
            new SearchAsyncTask(db.facilityDao()).execute(gc.getRoadAddress());
        }


        // 출발 버튼 클릭
        start = (ImageView)layout.findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RouteMenuActivity.class);
                intent.putExtra("type","start");
                if(isSearch) {
                    intent.putExtra("start", placeName);
                    intent.putExtra("startX", X);
                    intent.putExtra("startY", Y);
                }else {
                    intent.putExtra("start", gc.getBuildName());
                    intent.putExtra("startX", gc.getX());
                    intent.putExtra("startY", gc.getY());
                }
                startActivity(intent);
            }
        });

        // 도착 버튼 클릭
        end = (ImageView)layout.findViewById(R.id.end);
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });


        //괄호안이 데이터 값
        return layout;
    }

    //데이터 베이스 검색
    private static class SearchAsyncTask extends AsyncTask<String,Void,List<Facility>> {
        private FacilityDao facilityDao;

        public SearchAsyncTask(FacilityDao facilityDao){
            this.facilityDao = facilityDao;
        }
        @Override
        protected List<Facility> doInBackground(String... strings) {
            Log.d("TEXT","fragment2 : " + strings[0]);
            List<Facility> facility = facilityDao.findBuildWithAddress(strings[0]);
            Log.d("TEXT","fragment2 : " + facility.size());
            return facility;
        }

        @Override
        protected void onPostExecute(List<Facility> facility) {
            super.onPostExecute(facility);
            if(facility != null)
                Log.d("TEXT", "fragment_search2 : " + facility.toString());
        }
    }

}