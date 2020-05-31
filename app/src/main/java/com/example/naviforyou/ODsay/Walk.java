package com.example.naviforyou.ODsay;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Walk implements Serializable {

    private String[] WalkInfo=new String[2];//walk에 대한 정보를 담는 배열 {이동거리,이동소요시간}
    private int I;
    private int J;

    Walk(int i,int j){
        I=i;
        J=j;
    }

    String json = "";

    public String what(JSONObject jsonObject) {
        try {
            JSONArray subPath = jsonObject.getJSONObject("result").getJSONArray("path").getJSONObject(I).getJSONArray("subPath");
            String distance = subPath.getJSONObject(J).getString("distance");
            WalkInfo[0]=distance;
            String sectionTime = subPath.getJSONObject(J).getString("sectionTime");
            WalkInfo[1]=sectionTime;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    public String[] getWalkInfo() {
        return WalkInfo;
    }

    public void setWalkInfo(String[] walkInfo) {
        WalkInfo = walkInfo;
    }
}