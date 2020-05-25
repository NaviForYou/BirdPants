package com.example.naviforyou;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.naviforyou.API.Gc;


public class fragment_search2 extends Fragment {


    TextView place_name;
    TextView place_address;
    Gc gc;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.fragment_search2, container, false);

        Bundle bundle = getArguments();
        gc = (Gc)bundle.getSerializable("Gc");
        place_name = (TextView) layout.findViewById(R.id.place_name);
        place_address = (TextView) layout.findViewById(R.id.place_address);

        place_name.setText(gc.getBuildName());
        place_address.setText(gc.getBulidAdress());

        //괄호안이 데이터 값

        return layout;
    }


}
