package com.example.naviforyou;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class s {
    private  String trafficType;
    public ArrayList<Object> list = new ArrayList<>();
    String json = "";

    public String what(JSONObject jsonObject) {
        try {
            JSONArray path = jsonObject.getJSONObject("result").getJSONArray("path");
            JSONArray subPath = path.getJSONArray(2);

            for(int i=0;i<subPath.length();i++)
            {
                String trafficType=subPath.getJSONObject(i).getString("trafficType");
                if(trafficType=="1"){
                    Subway subway=new Subway();
                    list.add(i,subway);
                }
                else if (trafficType=="2"){
                    Bus bus=new Bus();
                    list.add(i,bus);
                }
                else if(trafficType=="3"){
                    Walk walk=new Walk();
                    list.add(i,walk);
                }

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
}
