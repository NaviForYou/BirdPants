package com.example.naviforyou.ODsay;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Subway implements Serializable {//지하철 1 :구간

    //1번 {이동거리,이동소요시간,이동정거장수,승차정류장명,하차정류장명,방면정보,빠른환승위치,들어가는출구,나가는출구}
    private String[] SubwayInfo_1=new String[9];

    //2번 lane(교통수단정보)::이 경로를 갈때 탈 수 있는 종류들{지하철노선명,노선번호}
    private String[][] SubwayInfo_2;//=new String[10][2];

    //3번 stations(정류장정보그룹)::정류장 명 순서대로 A개
    private String[] SubwayInfo_3;//=new String[100];

    private int I;
    private int J;

    Subway(int i,int j){
        I=i;
        J=j;
    }
    String json = "";

    public String what(JSONObject jsonObject) {
        try {
            JSONArray subPath = jsonObject.getJSONObject("result").getJSONArray("path").getJSONObject(I).getJSONArray("subPath");

            String trafficType=subPath.getJSONObject(J).getString("trafficType");

            SubwayInfo_1[0]=subPath.getJSONObject(J).getString("distance");//이동거리
            SubwayInfo_1[1]=subPath.getJSONObject(J).getString("sectionTime");//이동소요시간
            SubwayInfo_1[2]=subPath.getJSONObject(J).getString("stationCount");//이동정거장수

            JSONArray lane=subPath.getJSONObject(J).getJSONArray("lane");//같은 경로인데 종류 여러개일 수도 있어서..?
            SubwayInfo_2=new String[lane.length()][2];
            for(int j=0;j<lane.length();j++) {
                SubwayInfo_2[j][0]=lane.getJSONObject(j).getString("name");//지하철 노선명
                SubwayInfo_2[j][1]=lane.getJSONObject(j).getString("subwayCode");//지하철 노선 번호
            }
            SubwayInfo_1[3]=subPath.getJSONObject(J).getString("startName");//승차정류장역명
            SubwayInfo_1[4]=subPath.getJSONObject(J).getString("endName");//하차정류장역명
            SubwayInfo_1[5]=subPath.getJSONObject(J).getString("way");//방면정보
            SubwayInfo_1[6]=subPath.getJSONObject(J).getString("door");//지하철빠른환승위치
            SubwayInfo_1[7]=subPath.getJSONObject(J).getString("startExitNo");//지하철들어가는출구번호(없을 수도 있음)
            SubwayInfo_1[8]=subPath.getJSONObject(J).getString("endExitNo");//지하철나가는출구번호(없을 수도 있음)

            JSONObject passStopList= subPath.getJSONObject(J).getJSONObject("passStopList");
            JSONArray stations=passStopList.getJSONArray("stations");
            SubwayInfo_3=new String[stations.length()];
            for(int k=0;k<stations.length();k++){
                SubwayInfo_3[k]=stations.getJSONObject(k).getString("stationName");
            }





        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    public String[] getSubwayInfo_1() {
        return SubwayInfo_1;
    }

    public void setSubwayInfo_1(String[] subwayInfo_1) {
        SubwayInfo_1 = subwayInfo_1;
    }

    public String[][] getSubwayInfo_2() {
        return SubwayInfo_2;
    }

    public void setSubwayInfo_2(String[][] subwayInfo_2) {
        SubwayInfo_2 = subwayInfo_2;
    }

    public String[] getSubwayInfo_3() {
        return SubwayInfo_3;
    }

    public void setSubwayInfo_3(String[] subwayInfo_3) {
        SubwayInfo_3 = subwayInfo_3;
    }
}