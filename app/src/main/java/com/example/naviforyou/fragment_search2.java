package com.example.naviforyou;

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
import com.example.naviforyou.ODsay.FindRoute;


public class fragment_search2 extends Fragment {

    ImageView route;
    TextView place_name;
    TextView place_address;
    ImageView start;
    ImageView end;
    Gc gc;
    Boolean isSearch = false;
    String placeName;
    String buildAddress;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        // 텍스트 추가
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.fragment_search2, container, false);

        place_name = (TextView) layout.findViewById(R.id.place_name);
        place_address = (TextView) layout.findViewById(R.id.place_address);
        route = (ImageView) layout.findViewById(R.id.route);

        Bundle bundle = getArguments();
        isSearch = bundle.getBoolean("isSearch");
        if(isSearch) {
            placeName = bundle.getString("placeName");
            buildAddress= bundle.getString("buildAddress");

            place_name.setText(placeName);
            place_address.setText(buildAddress);
        }else {
            gc = (Gc) bundle.getSerializable("Gc");

            place_name.setText(gc.getBuildName());
            place_address.setText(gc.getBulidAdress());
        }

        // 출발 버튼 클릭
        start = (ImageView)layout.findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle1 = new Bundle();
                bundle1.putString("start",gc.getBulidAdress());
            }
        });

        // 도착 버튼 클릭
        end = (ImageView)layout.findViewById(R.id.end);

        route.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FindRoute findRoute = new FindRoute();
                findRoute.execution(getActivity());
            }
        });
        //괄호안이 데이터 값
        return layout;
    }


}