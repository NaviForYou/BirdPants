package com.example.naviforyou;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Traffic {

    private  String totalTime;
    private   String payment;

    String json = "";

    public String what(JSONObject jsonObject) {
        try {
            JSONArray path = jsonObject.getJSONObject("result").getJSONArray("path");

            JSONObject info = path.getJSONObject(0).getJSONObject("info");
            totalTime = info.getString("totalTime");
            payment = info.getString("payment");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
    public String getTotaltime() {
        return totalTime;
    }

    public void setTotaltime(String totaltime) {
        totalTime = totaltime;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

}
