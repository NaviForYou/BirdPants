package com.example.naviforyou;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


public class fragment_search2 extends Fragment {


    public fragment_search2() {

    }

    TextView place_name;
    TextView place_address;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.fragment_search, container, false);

        place_name = (TextView) layout.findViewById(R.id.place_name);
        place_address = (TextView) layout.findViewById(R.id.place_address);
        place_name.setText("광운대학교");
        place_address.setText("서울특별시 노원구 월계동 광운로 20");
        //괄호안이 데이터 값

        return inflater.inflate(R.layout.fragment_search2, container, false);
    }
}
