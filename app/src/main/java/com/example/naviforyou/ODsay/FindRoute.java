package com.example.naviforyou.ODsay;

import android.content.Context;
import android.content.Intent;

import com.example.naviforyou.Activity.RouteActivity;
import com.odsay.odsayandroidsdk.API;
import com.odsay.odsayandroidsdk.ODsayData;
import com.odsay.odsayandroidsdk.ODsayService;
import com.odsay.odsayandroidsdk.OnResultCallbackListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class FindRoute implements Serializable {

    public void execution(Context context){
        ODsayService odsayService = ODsayService.init(context, "3BeWEymToCezTng4oHpttVpNpcq+3Qdn0WoQc/S9R+c");
        odsayService.setReadTimeout(5000);
        odsayService.setConnectionTimeout(5000);

        OnResultCallbackListener onResultCallbackListener = new OnResultCallbackListener() {
            @Override
            public void onSuccess(ODsayData oDsayData, API api) {
                {
                    try {if (api == API.SEARCH_PUB_TRANS_PATH) {
                        JSONObject json = oDsayData.getJson();

                        ArrayList<ArrayList<Object>>LIST= new ArrayList<>();
                        JSONArray path = json.getJSONObject("result").getJSONArray("path");
                        ArrayList<Object> content=new ArrayList<>();
                        for(int i=0;i<path.length();i++) {
                            Traffic traffic=new Traffic(i);
                            traffic.what(json);
                            content.add(i,traffic);
                            JSONArray subPath = path.getJSONObject(i).getJSONArray("subPath");
                            ArrayList<Object> list = new ArrayList<>();
                            for (int j = 0; j < subPath.length(); j++) {
                                String trafficType = subPath.getJSONObject(j).getString("trafficType");
                                int T = Integer.parseInt(trafficType);
                                if (T == 1) {
                                    Subway subway = new Subway(i, j);
                                    subway.what(json);
                                    list.add(j, subway);
                                } else if (T == 2) {
                                    Bus bus = new Bus(i, j);
                                    bus.what(json);
                                    list.add(j, bus);
                                } else if (T == 3) {
                                    Walk walk = new Walk(i, j);
                                    walk.what(json);
                                    list.add(j, walk);
                                }
                            }
                            String  busCount= json.getJSONObject("result").getString("busCount");
                            String  subwayCount = json.getJSONObject("result").getString("subwayCount");
                            String  subwaybusCount = json.getJSONObject("result").getString("subwayBusCount");
                            LIST.add(i, list);

                            Intent intent = new Intent(context, RouteActivity.class);
                            intent.putExtra("LIST",LIST);
                            context.startActivity(intent);
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
