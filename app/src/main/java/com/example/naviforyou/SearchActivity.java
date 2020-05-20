package com.example.naviforyou;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    static EditText searchText;
    ListView myListView;
    Search_Parser search_parser;
    ArrayList<Search> list;
    ViewSearchAdapter adapter;
    ImageView search;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchText = findViewById(R.id.searchText);
        myListView = findViewById(R.id.myListView);
        search = findViewById(R.id.search);
        search_parser = new Search_Parser();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                list = new ArrayList<>();

                adapter = null;

                if(searchText.getText().toString().length() != 0)
                    new KakaoAsync_Search().execute("127.06283102249932","37.514322572335935",searchText.getText().toString());


            }
        });
    }

    //통신을 위한 백그라운드 작업 설정 - 검색
    class KakaoAsync_Search extends AsyncTask<String, String, ArrayList<Search>> {

        @Override
        protected ArrayList<Search> doInBackground(String... strings) {
            return search_parser.connectKakao(strings); // x,y 좌표, 검색어
        }

        @Override
        protected void onPostExecute(ArrayList<Search> list) {
            //doinBackground를 통해 완료된 작업 결과 처리
            super.onPostExecute(list);
            //로그 기록
            if(adapter == null){
                adapter = new ViewSearchAdapter( SearchActivity.this, R.layout.search_item, list);

                myListView.setAdapter(adapter);
            }
        }
    }
}

