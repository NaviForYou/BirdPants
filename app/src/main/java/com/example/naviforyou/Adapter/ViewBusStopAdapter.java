package com.example.naviforyou.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.naviforyou.API.BusStop;
import com.example.naviforyou.ODsay.Traffic;
import com.example.naviforyou.R;

import java.util.ArrayList;

public class ViewBusStopAdapter extends ArrayAdapter<BusStop>{
    Context context;
    int resource;
    ArrayList<BusStop> list;
    BusStop busStop;

    public ViewBusStopAdapter(@NonNull Context context, int resource, ArrayList<BusStop> list) {
        super(context, resource, list);

        this.list = list;
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(resource, null);

        busStop = list.get(position);
        TextView type = convertView.findViewById(R.id.bus_type);
        TextView num = convertView.findViewById(R.id.bus_no);
        TextView time = convertView.findViewById(R.id.bus_time);

        String typeText="";
        switch (busStop.getRouteType()){
            case "1":
                typeText = "공항";
                break;
            case "2":
                typeText = "마을";
                break;
            case "3":
                typeText = "간선";
                break;
            case "4":
                typeText = "지선";
                break;
            case "5":
                typeText = "순환";
                break;
            case "6":
                typeText = "광역";
                break;
            case "7":
                typeText = "인천";
                break;
            case "8":
                typeText = "경기";
                break;
            case "9":
                typeText = "폐지";
                break;
            case "0":
                typeText = "공용";
                break;
        }
        String busTypeText="";
        switch (busStop.getBusType()){
            case "0":
                busTypeText="일반버스";
                break;
            case "1":
                busTypeText="저상버스";
                break;
            case "2":
                busTypeText="굴절버스";
                break;
        }
        type.setText(typeText);
        num.setText(busStop.getRtName() + "("+busTypeText+")");
        time.setText(busStop.getTime1());

        return convertView;
    }
}
