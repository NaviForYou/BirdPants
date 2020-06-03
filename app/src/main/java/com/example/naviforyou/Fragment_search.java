package com.example.naviforyou;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.naviforyou.Activity.MainActivity;
import com.example.naviforyou.Activity.RouteMenuActivity;
import com.example.naviforyou.Activity.SearchActivity;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.NaverMap;


public class Fragment_search extends Fragment {

    RelativeLayout search_layout;
    ImageView find;
    ImageView gps;
    ViewGroup layout;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        layout = (ViewGroup) inflater.inflate(R.layout.fragment_search, container, false);

        search_layout = (RelativeLayout) layout.findViewById(R.id.search_layout);
        search_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra("type","main");
                startActivity(intent);
            }
        });

        find = (ImageView)layout.findViewById(R.id.find);
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RouteMenuActivity.class);
                intent.putExtra("type","main");
                startActivity(intent);
            }
        });

        NaverMap naverMap = MainActivity.naverMap;
        gps = layout.findViewById(R.id.gps);
        gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TEXT","HI");
                if (naverMap.getLocationTrackingMode() == LocationTrackingMode.NoFollow) {
                    naverMap.setLocationTrackingMode(LocationTrackingMode.Face);
                }
                else {
                    naverMap.setLocationTrackingMode(LocationTrackingMode.NoFollow);
                }
            }
        });

        return layout;
    }

}
