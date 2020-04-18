package com.example.naviforyou;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    TextView search_bar; //main layout의 검색창

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search_bar = findViewById(R.id.search_bar);
        search_bar.setOnClickListener(new View.OnClickListener() {
            @Override  //클릭했얼 경우 검책 프래그먼트창 켜지기 근데 이게 프레임 레이아웃이 바뀌는 느낌이어서 드로워 레이아웃 뒤로 가는듯 하닫
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                fragment_search fragment_search = new fragment_search();
                transaction.replace(R.id.frame, fragment_search);
                transaction.commit();
            }
        });
    }


}
