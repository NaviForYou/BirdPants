package com.example.naviforyou.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.naviforyou.ODsay.Bus;
import com.example.naviforyou.ODsay.Subway;
import com.example.naviforyou.ODsay.Traffic;
import com.example.naviforyou.ODsay.Walk;
import com.example.naviforyou.R;

import java.util.ArrayList;

public class ViewRouteAdapter extends ArrayAdapter<ArrayList<Object>> {

    Context context;
    int resource;
    ArrayList<ArrayList<Object>> list;
    ArrayList<Object> route;
    ArrayList<Traffic> traffic;
    Traffic totalInfo;

    public ViewRouteAdapter(@NonNull Context context, int resource, ArrayList<ArrayList<Object>> list, ArrayList<Traffic> traffic) {
        super(context, resource, list);

        this.list = list;
        this.context = context;
        this.resource = resource;
        this.traffic = traffic;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
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
        LinearLayout bar = convertView.findViewById(R.id.bar);

        //시간 돈 정보
        int timehour=Integer.parseInt(totalInfo.getHour());
        if(timehour == 0){
            TextView hourText = convertView.findViewById(R.id.hourText);
            hourText.setVisibility(View.GONE);
            hour.setVisibility(View.GONE);
        }else{
            hour.setText(totalInfo.getHour());
        }

        min.setText(totalInfo.getMin());
        money.setText(totalInfo.getContent()[2]);

        //bar 보여주기
        for (int i=0;i<route.size(); i++){
            String text = "";
            String color="#000000";
            Drawable img=null;
            int time=0;
            if (route.get(i) instanceof Bus){
                Bus bus = (Bus)route.get(i);
                time=Integer.parseInt(bus.getBusInfo_1()[1]);
                color="#4b89dc";
                img = getContext().getResources().getDrawable(R.drawable.customise_bus);
            }
            else if(route.get(i) instanceof Subway){
                Subway subway = (Subway)route.get(i);
                time=Integer.parseInt(subway.getSubwayInfo_1()[1]);
                img = getContext().getResources().getDrawable(R.drawable.customise_subway);
                int lane = Integer.parseInt(subway.getSubwayInfo_2()[0][1]);
                switch (lane){
                    case 1:
                        color="#0d3692";
                        break;
                    case 2:
                        color="#33a23d";
                        break;
                    case 3:
                        color="#fe5d10";
                        break;
                    case 4:
                        color="#00a2d1";
                        break;
                    case 5:
                        color="#8b50a4";
                        break;
                    case 6:
                        color="#c55c1d";
                        break;
                    case 7:
                        color="#54640d";
                        break;
                    case 8:
                        color="#f14c82";
                        break;
                    case 9:
                        color="#aa9872";
                        break;
                }
            }
            else if(route.get(i) instanceof Walk) {
                Walk walk = (Walk) route.get(i);
                time = Integer.parseInt(walk.getWalkInfo()[1]);
                img = getContext().getResources().getDrawable(R.drawable.customise_wheelchair);
                color="#d3d3d3";
            }


            float weight = (float) ((float)time / 10.0);

            TextView view = new TextView(context);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, weight);
            if (time != 0) {
                text=""+ time +"분";
                view.setText(text);
            }

            view.setGravity(Gravity.CENTER_HORIZONTAL);
            view.setMinWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            view.setBackgroundColor(Color.parseColor(color));
            view.setTextColor(Color.parseColor("#E3E9ED"));
            view.setLayoutParams(lp);
            view.setCompoundDrawablesRelativeWithIntrinsicBounds(img,null,null,null);
            bar.addView(view);


        }

        return convertView;
    }
}
