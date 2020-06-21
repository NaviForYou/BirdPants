package com.example.naviforyou.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.naviforyou.R;

public class SubwayMapActivity extends AppCompatActivity {
    String line;
    String station;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subwaymap);

        Intent intent = getIntent();
        line= intent.getStringExtra("line");
        station = intent.getStringExtra("station");

        
    }
}
