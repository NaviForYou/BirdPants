package com.example.naviforyou.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.naviforyou.Activity.RouteMenuActivity;
import com.example.naviforyou.Data.IsSubwayStop;
import com.example.naviforyou.R;
import com.odsay.odsayandroidsdk.API;
import com.odsay.odsayandroidsdk.ODsayData;
import com.odsay.odsayandroidsdk.ODsayService;
import com.odsay.odsayandroidsdk.OnResultCallbackListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SubwayInformationFragment extends Fragment {
    TextView place_name;
    ImageView start;
    ImageView end;

    IsSubwayStop isSubwayStop;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.fragment_subway_information, container, false);

        Bundle bundle = getArguments();
        isSubwayStop = (IsSubwayStop)bundle.getSerializable("subway");

        place_name = layout.findViewById(R.id.place_name);
        start = layout.findViewById(R.id.start_subway);
        end = layout.findViewById(R.id.end_subway);

        // ODsay Api
        ODsayService odsayService = ODsayService.init(getContext(), "3BeWEymToCezTng4oHpttVpNpcq+3Qdn0WoQc/S9R+c");
        odsayService.setReadTimeout(5000);
        odsayService.setConnectionTimeout(5000);

        OnResultCallbackListener onResultCallbackListener = new OnResultCallbackListener() {
            @Override
            public void onSuccess(ODsayData oDsayData, API api) {
                JSONObject json = oDsayData.getJson();
                ODsay_FindSubway(json);
                place_name.setText(isSubwayStop.getStation() + "역 " + isSubwayStop.getLaneType() + "호선");
            }

            @Override
            public void onError(int i, String s, API api) {

            }
        };

        odsayService.requestPointSearch(
                String.valueOf(isSubwayStop.getX()),
                String.valueOf(isSubwayStop.getY()),
                "10","2",onResultCallbackListener
        );

        start.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), RouteMenuActivity.class);
            intent.putExtra("type","start");
            intent.putExtra("start", isSubwayStop.getStation() + "역 " + isSubwayStop.getLaneType() + "호선");
            intent.putExtra("startX", String.valueOf(isSubwayStop.getX()));
            intent.putExtra("startY", String.valueOf(isSubwayStop.getY()));
            startActivity(intent);
        });

        end.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), RouteMenuActivity.class);
            intent.putExtra("type","end");
            intent.putExtra("end", isSubwayStop.getStation()+"역 " + isSubwayStop.getLaneType() + "호선");
            intent.putExtra("endX", String.valueOf(isSubwayStop.getX()));
            intent.putExtra("endY", String.valueOf(isSubwayStop.getY()));
            startActivity(intent);
        });

        return layout;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    void ODsay_FindSubway(JSONObject json){
        try {
            JSONArray jsonArray = json.getJSONObject("result").getJSONArray("station");
            for (int i =0;i <jsonArray.length();i++){
                int stationClass = jsonArray.getJSONObject(i).getInt("stationClass");
                if (stationClass == 2){
                    int laneType = jsonArray.getJSONObject(i).getInt("type");
                    String stationName = jsonArray.getJSONObject(i).getString("stationName");
                    isSubwayStop.setLaneType(laneType);
                    isSubwayStop.setStation(stationName);
                    break;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
