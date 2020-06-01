package com.example.naviforyou;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.naviforyou.Activity.SearchActivity;


public class fragment_search extends Fragment {

    RelativeLayout search_layout;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.fragment_search, container, false);

        search_layout = (RelativeLayout) layout.findViewById(R.id.search_layout);
        search_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra("type","main");
                startActivity(intent);
            }
        });

        return layout;
    }
}
