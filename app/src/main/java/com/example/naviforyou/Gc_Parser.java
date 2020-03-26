package com.example.naviforyou;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class Gc_Parser {
    Gc gc;
    String json = null;

   private String coords = "";

   private String clientId = "shgy3jthid";
   private String clientSecret = "zeKTFiOJPucpVrYU6Fi1EZpJKm6wrLRTWwm9cfrD";


   //통신을 위한 메서드
    public void connectNaver(String[] coords) //위경도
    {

        try{
            Log.d("coords",coords[0]);
            this.coords = URLEncoder.encode(coords[0],"UTF-8");
            String apiURL = "https://naveropenapi.apigw.ntruss.com/map-reversegeocode/v2/gc?coords="+this.coords+"&sourcecrs=epsg:4326&output=json&orders=legalcode,addr,admcode,roadaddr";

            URL url = new URL(apiURL); // URL클래스를 생성하여 접근할 경로
            HttpURLConnection con = (HttpURLConnection)url.openConnection(); //url 클래스의 연결 정보를 connectiopn에 전달
            con.setRequestMethod("GET"); //네이버 api는 get방식을 지원
            con.setRequestProperty("X-NCP-APIGW-API-KEY-ID",clientId); //id 인증
            con.setRequestProperty("X-NCP-APIGW-API-KEY",clientSecret); // secret 인증

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

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
