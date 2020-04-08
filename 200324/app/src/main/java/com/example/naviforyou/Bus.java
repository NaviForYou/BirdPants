package com.example.naviforyou;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Bus {
    public String distance;//이동거리
    public String secondTime;//이동소요시간
    public String stationCount;//이동정거장 수

    //lane
    public String[]busNo;
    public String []type;

    public  String startName;
    public String endName;
    public String way;
    public String door;
    public String startExitNo;
    public String endExitNo;

    public String[][] stationName;//[][]앞은 같은 경로 다른 지하철..근데 아마 인덱스 대부분 0일거 같아..!/뒤는 정류장 순서


    String json = "";

    public String what(JSONObject jsonObject) {
        try {
            JSONArray subPath = jsonObject.getJSONObject("result").getJSONArray("path").getJSONArray(2);
            for(int i=0;i<subPath.length();i++){
                String trafficType=subPath.getJSONObject(i).getString("trafficType");
                if(trafficType=="2") {
                    distance=subPath.getJSONObject(i).getString("distance");
                    secondTime=subPath.getJSONObject(i).getString("secondTime");
                    stationCount=subPath.getJSONObject(i).getString("stationCount");

                    JSONArray lane=subPath.getJSONObject(i).getJSONArray("lane");//같은 경로인데 종류 여러개일 수도 있어서..?
                    for(int j=0;j<lane.length();j++) {
                        busNo[j]=lane.getJSONObject(j).getString("busNo");
                        type[j]=lane.getJSONObject(j).getString("type");
                        //이 때 lane 갯수도 받아야 할까??
                    }
                    startName=subPath.getJSONObject(i).getString("startName");
                    endName=subPath.getJSONObject(i).getString("endName");
                    way=subPath.getJSONObject(i).getString("way");
                    door=subPath.getJSONObject(i).getString("door");
                    startExitNo=subPath.getJSONObject(i).getString("startExitNo");
                    endExitNo=subPath.getJSONObject(i).getString("endExitNo");

                    JSONObject passStopList= subPath.getJSONObject(i).getJSONObject("passStopList");
                    JSONArray stations=passStopList.getJSONArray("stations");
                    for(int k=0;k<stations.length();k++){
                        for(int l=0;l<stations.length();l++){
                            stationName[k][i]=stations.getJSONArray(k).getJSONObject(l).getString("stationName");
                        }
                    }
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
}
