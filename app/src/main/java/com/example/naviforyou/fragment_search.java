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


public class fragment_search extends Fragment {

    TextView search_button;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.fragment_search, container, false);

        search_button = (TextView) layout.findViewById(R.id.search_bar);
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getActivity(), SearchAvtivity.class);
                startActivity(intent1);
            }
        });

        return layout;
    }
<<<<<<< HEAD
=======





>>>>>>> 40d1f467ed3249e8e8625880aaacc1463e201355
}
