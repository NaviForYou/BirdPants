package com.example.naviforyou.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.naviforyou.API.BusStop;
import com.example.naviforyou.API.BusStop_Parser;
import com.example.naviforyou.API.IsBusStop;
import com.example.naviforyou.API.LowbusStop_Parser;
import com.example.naviforyou.Adapter.ViewBusStopAdapter;
import com.example.naviforyou.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Fragment_Bus_Information extends Fragment {

    ViewBusStopAdapter viewBusStopAdapter;
    LowbusStop_Parser lowbusStop_parser;
    BusStop_Parser busStop_parser;
    ArrayList<BusStop> busStop;
    IsBusStop isBusStop;

    TextView station_name;
    TextView station_id;
    TextView station_way;
    ListView busList;
    Button low_bus;
    Button general_bus;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        isBusStop = (IsBusStop)bundle.getSerializable("bus");
        lowbusStop_parser = new LowbusStop_Parser();
        busStop_parser = new BusStop_Parser();
        try {
            busStop = new SeoulAsync_BusStop().execute(isBusStop.getId()).get();//Nxt 존재 X
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.fragment_bus_information, container, false);
        station_name = layout.findViewById(R.id.bus_station_name);
        station_id = layout.findViewById(R.id.bus_station_code);
        station_way = layout.findViewById(R.id.bus_station_way);
        busList = layout.findViewById(R.id.bus_list);
        low_bus = layout.findViewById(R.id.low_bus);
        general_bus = layout.findViewById(R.id.general_bus);

        station_name.setText(isBusStop.getName());
        station_id.setText(isBusStop.getId());
        station_way.setText(busStop.get(0).getNextStn() + " 방면");

        if(busStop.size() == 1){
            busList.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        viewBusStopAdapter = new ViewBusStopAdapter(getContext(),R.layout.item_bus_infomation,busStop);
        busList.setAdapter(viewBusStopAdapter);

        general_bus.setOnClickListener(v -> {
            try {
                busStop = new SeoulAsync_BusStop().execute(isBusStop.getId()).get();
                if(busStop.size() <= 1){
                    busList.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                }
                viewBusStopAdapter = new ViewBusStopAdapter(getContext(),R.layout.item_bus_infomation,busStop);
                busList.setAdapter(viewBusStopAdapter);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });

       low_bus.setOnClickListener(v -> {
           try {
               busStop = new SeoulAsync_LowBusStop().execute(isBusStop.getId()).get();//Nxt 존재 X
               if(busStop.size() <= 1){
                   busList.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
               }
               viewBusStopAdapter = new ViewBusStopAdapter(getContext(),R.layout.item_bus_infomation,busStop);
               busList.setAdapter(viewBusStopAdapter);
           } catch (ExecutionException e) {
               e.printStackTrace();
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
       });

        return layout;
    }

    class SeoulAsync_LowBusStop extends AsyncTask<String, String, ArrayList<BusStop>> {

        @Override
        protected ArrayList<BusStop> doInBackground(String... strings) {
            return lowbusStop_parser.connectSeoul(strings);
        }

        @Override
        protected void onPostExecute(ArrayList<BusStop> s) {
            //doinBackground를 통해 완료된 작업 결과 처리
            super.onPostExecute(s);
            //로그 기록
            for (int i = 0; i < s.size(); i++) {
                Log.d("Lowbus", "rtName : " + s.get(i).getRtName());
                Log.d("Lowbus", "arrmsg1 : " + s.get(i).getTime1());
            }
        }

    }

    class SeoulAsync_BusStop extends AsyncTask<String, String, ArrayList<BusStop>> {

        @Override
        protected ArrayList<BusStop> doInBackground(String... strings) {
            return busStop_parser.connectSeoul(strings);
        }

        @Override
        protected void onPostExecute(ArrayList<BusStop> s) {
            //doinBackground를 통해 완료된 작업 결과 처리
            super.onPostExecute(s);
            //로그 기록
            for (int i = 0; i < s.size(); i++) {
                Log.d("Lowbus", "rtName : " + s.get(i).getRtName());
                Log.d("Lowbus", "arrmsg1 : " + s.get(i).getTime1());
            }
        }

    }
}
