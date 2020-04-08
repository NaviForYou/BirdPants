package com.example.naviforyou;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Walk {
    public String distance;//이동거리
    public String secondTime;//이동소요시간

    String json = "";

    public String what(JSONObject jsonObject) {
        try {
            JSONArray subPath = jsonObject.getJSONObject("result").getJSONArray("path").getJSONArray(2);
            for(int i=0;i<subPath.length();i++){
                String trafficType=subPath.getJSONObject(i).getString("trafficType");
                if(trafficType=="3"){
                    distance=subPath.getJSONObject(i).getString("distance");
                    secondTime=subPath.getJSONObject(i).getString("secondTime");
                }

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
}
