package com.example.naviforyou;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


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
        Log.d("TAG", String.valueOf(resource));
        convertView = layoutInflater.inflate(resource,null);

        //리스트뷰에 표현할 정보 객체
        search = list.get(position);

        TextView placeName = convertView.findViewById(R.id.search_placeName);
        TextView roadAddress = convertView.findViewById(R.id.search_roadAddress);
        TextView phoneNumber = convertView.findViewById(R.id.search_phoneNumber);

        placeName.setText(search.placeName);
        roadAddress.setText(search.roadAddress);
        phoneNumber.setText(search.phone_number);

        return convertView;
    }
}
