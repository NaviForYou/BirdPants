package com.example.naviforyou;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.odsay.odsayandroidsdk.API;
import com.odsay.odsayandroidsdk.ODsayData;
import com.odsay.odsayandroidsdk.ODsayService;
import com.odsay.odsayandroidsdk.OnResultCallbackListener;

import org.json.JSONException;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ODsayService odsayService=ODsayService.init(this,"3BeWEymToCezTng4oHpttVpNpcq+3Qdn0WoQc/S9R+c");
        odsayService.setReadTimeout(5000);
        odsayService.setConnectionTimeout(5000);

        OnResultCallbackListener onResultCallbackListener = new OnResultCallbackListener() {
            @Override
            public void onSuccess(ODsayData oDsayData, API api) {
                try {
                    if (api == API.BUS_STATION_INFO) {
                        String lane = oDsayData.getJson().getJSONObject("result").getString("lane");
                        Log.d("Station name: %s", lane);
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int i, String s, API api) {
                    if(api==API.BUS_STATION_INFO){}
            }
        };
        odsayService.requestBusStationInfo("107475",onResultCallbackListener);
    }

}


