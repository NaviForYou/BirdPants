package com.example.naviforyou.ODsay;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Bus implements Serializable {
    //1번 {이동거리,이동소요시간,이동정거장수,승차정류장역명,승차정류장ID,하차정류장역명,하차정류장ID}
    private String[] BusInfo_1=new String[7];

    //2번 lane(교통수단정보)::이 경로를 갈때 탈 수 있는 종류들{버스명,버스타입}
    private String[][] BusInfo_2;

    //3번 stations(정류장정보그룹)::정류장 명 정류장 ID 순서대로 A개
    private String[][] BusInfo_3;

    // 승차지 하차지 XY
    private double[][] XY = new double[2][2];

    private int I;
    private int J;

    public Bus(int i, int j){
        I=i;
        J=j;
    }

    String json = "";

    public String what(JSONObject jsonObject) {
        try {
            JSONArray subPath = jsonObject.getJSONObject("result").getJSONArray("path").getJSONObject(I).getJSONArray("subPath");

            String trafficType = subPath.getJSONObject(J).getString("trafficType");

            BusInfo_1[0] = subPath.getJSONObject(J).getString("distance");//이동거리
            BusInfo_1[1] = subPath.getJSONObject(J).getString("sectionTime");//이동소요시간
            BusInfo_1[2] = subPath.getJSONObject(J).getString("stationCount");//이동정거장수

            JSONArray lane = subPath.getJSONObject(J).getJSONArray("lane");//같은 경로인데 종류 여러개일 수도 있어서..?
            BusInfo_2 = new String[lane.length()][2];
            for (int j = 0; j < lane.length(); j++) {
                BusInfo_2[j][0] = lane.getJSONObject(j).getString("busNo");//버스 번호
                BusInfo_2[j][1] = lane.getJSONObject(j).getString("type");//버스 타입
                //이 때 lane 갯수도 받아야 할까??
            }
            BusInfo_1[3] = subPath.getJSONObject(J).getString("startName");//승차정류장역명
            BusInfo_1[4] = subPath.getJSONObject(J).getString("startID");//승차정류장ID
            BusInfo_1[5] = subPath.getJSONObject(J).getString("endName");//하차정류장역명
            BusInfo_1[6] = subPath.getJSONObject(J).getString("endID");//하차정류장ID

            XY[0][0] = subPath.getJSONObject(J).getDouble("startX"); //승차정류장 X
            XY[0][1] = subPath.getJSONObject(J).getDouble("startY"); //승차정류장 Y
            XY[1][0] = subPath.getJSONObject(J).getDouble("endX"); //하차정류장 X
            XY[1][1] = subPath.getJSONObject(J).getDouble("endY"); //하차정류장 Y

            JSONObject passStopList = subPath.getJSONObject(J).getJSONObject("passStopList");
            JSONArray stations = passStopList.getJSONArray("stations");
            BusInfo_3=new String[stations.length()][2];
            for (int k = 0; k < stations.length(); k++) {
                BusInfo_3[k][0] = stations.getJSONObject(k).getString("stationName");
                BusInfo_3[k][1] = stations.getJSONObject(k).getString("stationID");
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    public String[] getBusInfo_1() {
        return BusInfo_1;
    }

    public void setBusInfo_1(String[] busInfo_1) {
        BusInfo_1 = busInfo_1;
    }

    public String[][] getBusInfo_2() {
        return BusInfo_2;
    }

    public void setBusInfo_2(String[][] busInfo_2) {
        BusInfo_2 = busInfo_2;
    }

    public String[][] getBusInfo_3() {
        return BusInfo_3;
    }

    public void setBusInfo_3(String[][] busInfo_3) {
        BusInfo_3 = busInfo_3;
    }
}
