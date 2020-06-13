package com.example.naviforyou.Adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.naviforyou.ODsay.Traffic;
import com.example.naviforyou.R;

import java.util.ArrayList;

public class listViewAdapter extends ArrayAdapter<ArrayList<Object>> {

    Context context;
    int resource;
    ArrayList<ArrayList<Object>> list;
    ArrayList<Object> route;
    ArrayList<Traffic> traffic;
    Traffic totalInfo;

    public listViewAdapter(@NonNull Context context, int resource, ArrayList<ArrayList<Object>> list, ArrayList<Traffic> traffic) {
        super(context, resource, list);

        this.list = list;
        this.context = context;
        this.resource = resource;
        this.traffic = traffic;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(resource,null);

        //리스트뷰에 표현할 정보 객체
        route = list.get(position);
        totalInfo = traffic.get(position);

        TextView hour = convertView.findViewById(R.id.hour);
        TextView min = convertView.findViewById(R.id.min);
        TextView money = convertView.findViewById(R.id.money);
        TextView object = convertView.findViewById(R.id.object);
        LinearLayout bar = convertView.findViewById(R.id.bar);

        hour.setText(totalInfo.getHour());
        min.setText(totalInfo.getMin());
        money.setText(totalInfo.getContent()[2]);

        for (int i=0;i<route.size(); i++){
            if(i==0){
                object.setText(route.get(i).getClass().toString());
            }else{
                TextView view = new TextView(context);
                view.setText(route.get(i).getClass().toString());
                view.setGravity(Gravity.CENTER_HORIZONTAL);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0,18,20);
                view.setLayoutParams(lp);
                bar.addView(view);

            }

        }

        return convertView;
    }
}
