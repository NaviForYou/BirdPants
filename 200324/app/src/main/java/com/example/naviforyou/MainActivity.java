package com.example.naviforyou;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.odsay.odsayandroidsdk.API;
import com.odsay.odsayandroidsdk.ODsayData;
import com.odsay.odsayandroidsdk.ODsayService;
import com.odsay.odsayandroidsdk.OnResultCallbackListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ODsayService odsayService = ODsayService.init(this, "3BeWEymToCezTng4oHpttVpNpcq+3Qdn0WoQc/S9R+c");
        odsayService.setReadTimeout(5000);
        odsayService.setConnectionTimeout(5000);
        OnResultCallbackListener onResultCallbackListener = new OnResultCallbackListener() {
            @Override
            public void onSuccess(ODsayData oDsayData, API api) {
                {
                    try {if (api == API.SEARCH_PUB_TRANS_PATH) {
                        JSONObject json = oDsayData.getJson();
                        Traffic traffic= new Traffic();
                        traffic.what(json);
                        ArrayList<ArrayList<Object>>LIST=new ArrayList<ArrayList<Object>>();
                        JSONArray path = json.getJSONObject("result").getJSONArray("path");
                        for(int i=0;i<path.length();i++)
                        {
                            String pathType=path.getJSONObject(i).getString("pathType");
                            JSONArray subPath=path.getJSONObject(i).getJSONArray("subPath");
                            ArrayList<Object> list = new ArrayList<>();
                            for(int j=0;j<subPath.length();j++) {
                                String trafficType=subPath.getJSONObject(j).getString("trafficType");
                                int T = Integer.parseInt(trafficType);
                                if(T==1){
                                    Subway subway=new Subway(i,j);
                                    subway.what(json);
                                   // list.add(subway);
                                  //  Subway S=(Subway)list.get(j);
                                   // String[] AAA=S.getSubwayInfo_1();
                                    //System.out.println(AAA[2]);
                                }
                                else if(T==2){
                                    Bus bus=new Bus(i,j);
                                    bus.what(json);
                                  //  list.add(j,bus);
                                }
                                else if(T==3){
                                    Walk walk=new Walk(i,j);
                                    walk.what(json);
                                    list.add(walk);
                                    //Walk W=(Walk)list.get(j);
                                    //String[] S=W.getWalkInfo();
                                    //System.out.println(S[0]);
                                    //  Subway S=(Subway)list.get(j);
                                    // String[] AAA=S.getSubwayInfo_1();
                                    //System.out.println(AAA[2]);
                                }
                            }
                            LIST.add(i,list);
                        }
                    }
                    } catch (JSONException e) {
                        e.printStackTrace();
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



