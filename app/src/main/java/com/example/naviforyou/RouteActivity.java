package com.example.naviforyou;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class RouteActivity extends AppCompatActivity {

    ArrayList<ArrayList<Object>> LIST;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        Intent intent = getIntent();
        LIST = (ArrayList<ArrayList<Object>>) intent.getSerializableExtra("LIST");
        Log.d("TEXT","TEXT : Route");
        Log.d("TEXT","TEXT : " + LIST.get(0).get(0).getClass());
    }
}
