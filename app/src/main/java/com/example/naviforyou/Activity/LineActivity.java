package com.example.naviforyou.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.naviforyou.R;

public class LineActivity extends AppCompatActivity {
    String line;
    String station;
    TextView line_name;
    ListView line_list;
    LinearLayout title;
    ArrayAdapter<CharSequence> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line);

        Intent intent = getIntent();
        if( intent.hasExtra("line") ){
            line = intent.getStringExtra("line");
        }

        line_name=findViewById(R.id.line_name);
        line_list=findViewById(R.id.line_list);
        title=findViewById(R.id.title);

        switch (line){
            case "1" :
                line_name.setText("1호선");
                title.setBackgroundColor(Color.parseColor("#0d3692"));
                arrayAdapter = ArrayAdapter.createFromResource(this,
                        R.array.line1, android.R.layout.simple_list_item_1);
                line_list.setAdapter(arrayAdapter);
                break;
            case "2" :
                line_name.setText("2호선");
                title.setBackgroundColor(Color.parseColor("#33a23d"));
                arrayAdapter = ArrayAdapter.createFromResource(this,
                        R.array.line2, android.R.layout.simple_list_item_1);
                line_list.setAdapter(arrayAdapter);
                break;
            case "3" :
                line_name.setText("3호선");
                title.setBackgroundColor(Color.parseColor("#fe5d10"));
                arrayAdapter = ArrayAdapter.createFromResource(this,
                        R.array.line3, android.R.layout.simple_list_item_1);
                line_list.setAdapter(arrayAdapter);
                break;
            case "4" :
                line_name.setText("4호선");
                title.setBackgroundColor(Color.parseColor("#00a2d1"));
                arrayAdapter = ArrayAdapter.createFromResource(this,
                        R.array.line4, android.R.layout.simple_list_item_1);
                line_list.setAdapter(arrayAdapter);
                break;
            case "5" :
                line_name.setText("5호선");
                title.setBackgroundColor(Color.parseColor("#8b50a4"));
                arrayAdapter = ArrayAdapter.createFromResource(this,
                        R.array.line5, android.R.layout.simple_list_item_1);
                line_list.setAdapter(arrayAdapter);
                break;
            case "6" :
                line_name.setText("6호선");
                title.setBackgroundColor(Color.parseColor("#c55c1d"));
                arrayAdapter = ArrayAdapter.createFromResource(this,
                        R.array.line6, android.R.layout.simple_list_item_1);
                line_list.setAdapter(arrayAdapter);
                break;
            case "7" :
                line_name.setText("7호선");
                arrayAdapter = ArrayAdapter.createFromResource(this,
                        R.array.line7, android.R.layout.simple_list_item_1);
                title.setBackgroundColor(Color.parseColor("#54640d"));
                line_list.setAdapter(arrayAdapter);
                break;
            case "8" :
                line_name.setText("8호선");
                arrayAdapter = ArrayAdapter.createFromResource(this,
                        R.array.line8, android.R.layout.simple_list_item_1);
                title.setBackgroundColor(Color.parseColor("#f14c82"));
                line_list.setAdapter(arrayAdapter);
                break;
            case "9" :
                line_name.setText("9호선");
                arrayAdapter = ArrayAdapter.createFromResource(this,
                        R.array.line9, android.R.layout.simple_list_item_1);
                title.setBackgroundColor(Color.parseColor("#aa9872"));
                line_list.setAdapter(arrayAdapter);
                break;
        }

        line_list.setOnItemClickListener((parent, view, position, id) -> {
            station = String.valueOf(parent.getItemAtPosition(position));
            Intent sendIntent = new Intent(this, SubwayMapActivity.class);
            sendIntent.putExtra("line",line);
            sendIntent.putExtra("station",station);
            startActivity(sendIntent);

        });
    }
}
