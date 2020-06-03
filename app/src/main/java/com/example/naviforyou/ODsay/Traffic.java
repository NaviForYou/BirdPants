package com.example.naviforyou.ODsay;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Traffic {
    private String[] content =new String[3];;

    private int I;

    public Traffic(int i) {
        I = i;
    }

    String json = "";

    public String what(JSONObject jsonObject) {
        try {
            JSONArray path = jsonObject.getJSONObject("result").getJSONArray("path");
            JSONObject info = path.getJSONObject(I).getJSONObject("info");
            content[0]= path.getJSONObject(I).getString("pathType");
            content[1] = info.getString("totalTime");
            content[2] = info.getString("payment");


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    public String[] getContent() {
        return content;
    }
}