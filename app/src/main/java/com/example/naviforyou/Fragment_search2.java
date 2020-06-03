package com.example.naviforyou;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.naviforyou.API.Gc;
import com.example.naviforyou.Activity.RouteMenuActivity;


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

    // 검색
    String placeName;
    String buildAddress;
    double X;
    double Y;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        // 텍스트 추가
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.fragment_search2, container, false);

        place_name = (TextView) layout.findViewById(R.id.place_name);
        place_address = (TextView) layout.findViewById(R.id.place_address);

        Bundle bundle = getArguments();
        isSearch = bundle.getBoolean("isSearch");
        if(isSearch) {
            placeName = bundle.getString("placeName");
            buildAddress= bundle.getString("buildAddress");
            X = bundle.getDouble("X");
            Y = bundle.getDouble("Y");

            place_name.setText(placeName);
            place_address.setText(buildAddress);
        }else {
            gc = (Gc) bundle.getSerializable("Gc");

            place_name.setText(gc.getBuildName());
            place_address.setText(gc.getBuildAddress());
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


}