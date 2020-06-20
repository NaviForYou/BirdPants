package com.example.naviforyou.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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

public class ViewFindingMapAdapter extends ArrayAdapter<Object> {
    Context context;
    int resource;
    ArrayList<Object> list;

    public ViewFindingMapAdapter(@NonNull Context context, int resource, ArrayList<Object> list) {
        super(context, resource, list);

        this.list = list;
        this.context = context;
        this.resource = resource;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(resource,null);



        LinearLayout transport = convertView.findViewById(R.id.transport);
        TextView line = convertView.findViewById(R.id.line);
        TextView startStation = convertView.findViewById(R.id.startStation);
        ImageView startIcon = convertView.findViewById(R.id.startIcon);
        TextView startInfo = convertView.findViewById(R.id.startInfo);
        TextView way = convertView.findViewById(R.id.way);
        TextView endStation = convertView.findViewById(R.id.endStation);
        TextView endInfo = convertView.findViewById(R.id.endInfo);

        String color="#000000";
        Drawable img=null;
        int time=0;

        if (list.get(position) instanceof String){
            String temp = (String)list.get(position);
            if (position == 0){
                color="#ff0000";
                line.setBackgroundColor(Color.parseColor(color));
                startInfo.setBackgroundColor(Color.parseColor(color));
                startInfo.setText("출발");
                startStation.setText(temp);
                startIcon.setVisibility(View.GONE);
                endInfo.setText("");
                endInfo.setBackgroundColor(Color.parseColor(color));
                endStation.setVisibility(View.GONE);
            }else{
                color="#0000ff";
                line.setBackgroundColor(Color.parseColor(color));
                startInfo.setBackgroundColor(Color.parseColor(color));
                startInfo.setText("");
                startStation.setVisibility(View.GONE);
                startIcon.setVisibility(View.GONE);
                endInfo.setText("도착");
                endInfo.setBackgroundColor(Color.parseColor(color));
                endStation.setText(temp);
            }

        }
        else if (list.get(position) instanceof Bus){
            Bus bus = (Bus)list.get(position);

            String startStationText = bus.getBusInfo_1()[3];
            String startIDText = bus.getBusInfo_1()[4];
            String endStationText = bus.getBusInfo_1()[5];
            String endIDText = bus.getBusInfo_1()[6];
            String[][] busInfo = bus.getBusInfo_2();
            time=Integer.parseInt(bus.getBusInfo_1()[1]);
            color="#4b89dc";
            img = getContext().getResources().getDrawable(R.drawable.customise_bus);

            line.setBackgroundColor(Color.parseColor(color));
            startInfo.setBackgroundColor(Color.parseColor(color));
            endInfo.setBackgroundColor(Color.parseColor(color));
            startIcon.setImageDrawable(img);
            startStation.setText(startStationText +"(" +startIDText+")"+ " 정류장 승차");
            endStation.setText(endStationText + "(" +endIDText+")"+" 정류장 하차");

            for (int i=0;i<busInfo.length;i++){
                int typeInt = Integer.parseInt(busInfo[i][1]);
                String noText = busInfo[i][0];
                Log.d("TEXT@","no : " + noText);

                LinearLayout layout = new LinearLayout(context);
                TextView type = new TextView(context);
                TextView no = new TextView(context);

                layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
                layout.setGravity(Gravity.CENTER_VERTICAL);
                layout.setOrientation(LinearLayout.HORIZONTAL);

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.rightMargin = 7;
                type.setLayoutParams(layoutParams);
                type.setBackgroundColor(Color.parseColor("#AAAAAA"));
                type.setTextSize(18);
                String typeText="";
                switch (typeInt){
                    case 1:
                        noText="일반";
                        break;
                    case 2:
                        noText="좌석";
                        break;
                    case 3:
                        noText="마을버스";
                        break;
                    case 4:
                        noText="직행좌석";
                        break;
                    case 5:
                        noText="간선급향";
                        break;
                    case 10:
                        noText="외곽";
                        break;
                    case 11:
                        noText="간선";
                        break;
                    case 12:
                        noText="지선";
                        break;
                    case 13:
                        noText="순환";
                        break;
                    case 14:
                        noText="광역";
                        break;
                    case 15:
                        noText="급행";
                        break;
                    case 20:
                        noText="농어촌버스";
                        break;
                    case 21:
                        noText="제주도 시외형버스";
                        break;
                    case 22:
                        noText="경기도 시외형버스";
                        break;
                    case 26:
                        noText="급행간선";
                        break;

                }
                //type.setText(typeText);

                no.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
                no.setTextSize(18);
                no.setText(noText);


                layout.addView(type);
                layout.addView(no);
                transport.addView(layout);


            }

        }
        else if(list.get(position) instanceof Subway){
            Subway subway = (Subway)list.get(position);
            time=Integer.parseInt(subway.getSubwayInfo_1()[1]);
            int lane = Integer.parseInt(subway.getSubwayInfo_2()[0][1]);
            String startStationText = subway.getSubwayInfo_1()[3];
            String startIDText = subway.getSubwayInfo_1()[4];
            String endStationText = subway.getSubwayInfo_1()[5];
            String endIDText = subway.getSubwayInfo_1()[6];
            String wayText = subway.getSubwayInfo_1()[7];

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

            line.setBackgroundColor(Color.parseColor(color));
            startInfo.setBackgroundColor(Color.parseColor(color));
            startInfo.setText(String.valueOf(lane));
            endInfo.setBackgroundColor(Color.parseColor(color));
            startIcon.setVisibility(View.GONE);
            startStation.setText(startStationText +"(" +startIDText+")"+ " 승차");
            endStation.setText(endStationText + "(" +endIDText+")"+" 하차");
            way.setText(wayText + "방면");
            way.setVisibility(View.VISIBLE);
            transport.setWeightSum(time);
        }
        else if(list.get(position) instanceof Walk) {
            Walk walk = (Walk) list.get(position);
            time = Integer.parseInt(walk.getWalkInfo()[1]);
            img = getContext().getResources().getDrawable(R.drawable.customise_wheelchair);
            color="#d3d3d3";

            line.setBackgroundColor(Color.parseColor(color));
            startInfo.setBackgroundColor(Color.parseColor(color));
            startIcon.setImageDrawable(img);
            startStation.setVisibility(View.GONE);
            endInfo.setText("");
            endInfo.setBackgroundColor(Color.parseColor(color));
            endStation.setVisibility(View.GONE);
            transport.setWeightSum(time);

        }

        return convertView;
    }
}
