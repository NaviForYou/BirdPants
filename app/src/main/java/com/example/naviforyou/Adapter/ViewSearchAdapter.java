package com.example.naviforyou.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.example.naviforyou.API.Search;
import com.example.naviforyou.R;

import java.util.ArrayList;

public class ViewSearchAdapter extends ArrayAdapter<Search> {

    Context context;
    ArrayList<Search> list;
    Search search;
    int resource;

    public ViewSearchAdapter(@NonNull Context context, int resource, ArrayList<Search> list) {
        super(context, resource, list);

        this.list = list;
        this.context = context;
        this.resource = resource;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(resource,null);

        //리스트뷰에 표현할 정보 객체
        search = list.get(position);

        double X = search.getLongitude_X();
        double Y = search.getLatitude_Y();        TextView placeName = convertView.findViewById(R.id.search_placeName);
        TextView Address = convertView.findViewById(R.id.search_roadAddress);
        TextView phoneNumber = convertView.findViewById(R.id.search_phoneNumber);

        placeName.setText(search.getPlaceName());
        if(!search.getRoadAddress().isEmpty()) Address.setText(search.getRoadAddress());
        else Address.setText(search.getBulidAddress());
        phoneNumber.setText(search.getPhone_number());

        return convertView;
    }
}
