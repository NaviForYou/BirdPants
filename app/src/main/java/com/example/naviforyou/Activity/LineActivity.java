package com.example.naviforyou.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.naviforyou.R;

public class LineActivity extends AppCompatActivity {
    String line;
    String station;
    TextView line_name;
    ListView line_list;
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

        switch (line){
            case "1" :
                line_name.setText("1호선");
                arrayAdapter.createFromResource(this,
                        R.array.line1, android.R.layout.simple_list_item_1);
                line_list.setAdapter(arrayAdapter);
                break;
            case "2" :
                line_name.setText("2호선");
                arrayAdapter.createFromResource(this,
                        R.array.line2, android.R.layout.simple_list_item_1);
                line_list.setAdapter(arrayAdapter);
                break;
            case "3" :
                line_name.setText("3호선");
                arrayAdapter.createFromResource(this,
                        R.array.line3, android.R.layout.simple_list_item_1);
                line_list.setAdapter(arrayAdapter);
                break;
            case "4" :
                line_name.setText("4호선");
                arrayAdapter.createFromResource(this,
                        R.array.line4, android.R.layout.simple_list_item_1);
                line_list.setAdapter(arrayAdapter);
                break;
            case "5" :
                line_name.setText("5호선");
                arrayAdapter.createFromResource(this,
                        R.array.line5, android.R.layout.simple_list_item_1);
                line_list.setAdapter(arrayAdapter);
                break;
            case "6" :
                line_name.setText("6호선");
                arrayAdapter.createFromResource(this,
                        R.array.line6, android.R.layout.simple_list_item_1);
                line_list.setAdapter(arrayAdapter);
                break;
            case "7" :
                line_name.setText("7호선");
                arrayAdapter.createFromResource(this,
                        R.array.line7, android.R.layout.simple_list_item_1);
                line_list.setAdapter(arrayAdapter);
                break;
            case "8" :
                line_name.setText("8호선");
                arrayAdapter.createFromResource(this,
                        R.array.line8, android.R.layout.simple_list_item_1);
                line_list.setAdapter(arrayAdapter);
                break;
            case "9" :
                line_name.setText("9호선");
                arrayAdapter.createFromResource(this,
                        R.array.line9, android.R.layout.simple_list_item_1);
                line_list.setAdapter(arrayAdapter);
                break;
        }

        line_list.setOnItemClickListener((parent, view, position, id) -> {
            station = String.valueOf(parent.getItemIdAtPosition(position));
        });
    }
}
