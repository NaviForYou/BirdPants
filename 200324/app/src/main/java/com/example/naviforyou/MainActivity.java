package com.example.naviforyou;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.odsay.odsayandroidsdk.API;
import com.odsay.odsayandroidsdk.ODsayData;
import com.odsay.odsayandroidsdk.ODsayService;
import com.odsay.odsayandroidsdk.OnResultCallbackListener;

import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ODsayService odsayService = ODsayService.init(this, "3BeWEymToCezTng4oHpttVpNpcq+3Qdn0WoQc/S9R+c");
        odsayService.setReadTimeout(5000);
        odsayService.setConnectionTimeout(5000);
        // Log.d("Station name: %s", "된다aaaaaaaaaaaaaaaㅣ나둔");
        OnResultCallbackListener onResultCallbackListener = new OnResultCallbackListener() {
            @Override
            public void onSuccess(ODsayData oDsayData, API api) {
                {
                    if (api == API.SEARCH_PUB_TRANS_PATH) {
                        JSONObject json = oDsayData.getJson();
                        Traffic traffic= new Traffic();
                        traffic.what(json);
                        System.out.println(traffic.getPayment());

                    }

                }
            }
                @Override
                public void onError(int i, String s, API api) {
                    if (api == API.SEARCH_PUB_TRANS_PATH) {
                    }
                }


        };
        odsayService.requestSearchPubTransPath("126.97839260101318","37.56660635021524","127.05842971801758","37.61979786831449","1","0","0",onResultCallbackListener);
    }
}



