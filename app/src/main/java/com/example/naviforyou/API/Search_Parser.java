package com.example.naviforyou.API;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;


//키워드를 통한 검색
public class Search_Parser {
    String json = null;

    private String coordsX = "";
    private String coordsY = "";
    private String search = "";

    private String radius = "20000";
    private String sort = "accuracy"; //정렬 기준 : distance or accuracy
    private String restApi = "719ec8dad17c5585c9e25ff8a79fcd96";


    //public ArrayList<Search> connectNaver(ArrayList<Search> list, String[] coords){
    public ArrayList<Search> connectKakao(String[] temp){
        try{

            //현위치 좌표
            coordsX = URLEncoder.encode(temp[0],"UTF-8");
            coordsY = URLEncoder.encode(temp[1],"UTF-8");

            //검색정보
            search = URLEncoder.encode(temp[2],"UTF-8");

            //String apiURL = "https://dapi.kakao.com/v2/local/search/keyword.json?query="+ search +"&x="+coordsX +"&y="+ coordsY +"&radius=" + radius + "&sort="+sort;
            String apiURL = "https://dapi.kakao.com/v2/local/search/keyword.json?query="+ search +"&x="+coordsX +"&y="+ coordsY +"&sort="+sort;

            URL url = new URL(apiURL); // URL클래스를 생성하여 접근할 경로
            HttpURLConnection con = (HttpURLConnection)url.openConnection(); //url 클래스의 연결 정보를 connectiopn에 전달
            con.setRequestMethod("GET"); //카카오 api는 get방식을 지원
            con.setRequestProperty("Authorization", "KakaoAK "+restApi); //Rest api 키 인증


            //정상호출여부 확인
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else { // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }

            //json 파일 생성
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            json = response.toString();
            Log.d("TEST2", "json => " + json);

            return kakaoParser(json);

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<Search> kakaoParser(String json){
        ArrayList<Search> searches = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.optJSONArray("documents");
            for(int i =0; i < jsonArray.length(); i++){
                jsonObject = jsonArray.getJSONObject(i);
                //장소이름, 도로명, 지번, 전화번호, 거리

                Search temp = new Search(
                        jsonObject.getString("place_name"),
                        jsonObject.getString("road_address_name"),
                        jsonObject.getString("address_name"),
                        jsonObject.getString("phone"),
                        jsonObject.getString("distance")+"m");
                temp.setLatitude_Y(jsonObject.getDouble("y"));
                temp.setLongitude_X(jsonObject.getDouble("x"));
                searches.add(temp);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }

        return searches;
    }

}
