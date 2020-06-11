package com.example.naviforyou.Fragment;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.naviforyou.Activity.SubwayActivity;
import com.example.naviforyou.ODsay.Subway;
import com.example.naviforyou.R;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.NaverMap;


public class Fragment_search extends Fragment {

    RelativeLayout search_layout;
    ImageView find;
    ImageView gps;
    ImageView subway;
    ViewGroup layout;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        layout = (ViewGroup) inflater.inflate(R.layout.fragment_search, container, false);

        search_layout = layout.findViewById(R.id.search_layout);
        search_layout.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SearchActivity.class);
            intent.putExtra("type","main");
            startActivity(intent);
        });

        find = layout.findViewById(R.id.find);
        find.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), RouteMenuActivity.class);
            intent.putExtra("type","main");
            startActivity(intent);
        });

        NaverMap naverMap = MainActivity.naverMap;
        gps = layout.findViewById(R.id.gps);
        gps.setOnClickListener(v -> {
            if (naverMap.getLocationTrackingMode() == LocationTrackingMode.NoFollow) {
                naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
            }
            else {
                naverMap.setLocationTrackingMode(LocationTrackingMode.NoFollow);
            }
        });

        subway = layout.findViewById(R.id.subway);
        subway.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SubwayActivity.class);
            startActivity(intent);
        });

        return layout;
    }

}
