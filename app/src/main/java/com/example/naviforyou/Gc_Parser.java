package com.example.naviforyou;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class Gc_Parser {
   String json = null;

   private String coords = "";

   private String clientId = "shgy3jthid";
   private String clientSecret = "zeKTFiOJPucpVrYU6Fi1EZpJKm6wrLRTWwm9cfrD";


   //통신을 위한 메서드
    public Gc connectNaver(String[] coords) //위경도
    {

        try{
            //심벌 좌표
            Log.d("coords",coords[0]);
            this.coords = URLEncoder.encode(coords[0],"UTF-8");
            //출력 순서 : 법정동, 주소, 행정동, 도로명 주소
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
            return paserNAvert(json);


        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //파서
    private Gc paserNAvert(String json) {
        String roadAdress = null; //도로명
        String bulidAdress = null; //지번
        String legalcode = null; //법정동
        String admcode = null; //행정동
        String zipCode = null; //우편번호

        //출력 순서 : 법정동, 주소, 행정동, 도로명 주소
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("results");

            for (int i = 0; i < jsonArray.length(); i++){
                jsonObject = jsonArray.getJSONObject(i);
                Log.d("TEST2",jsonObject.toString());

                //각각 주소값 파싱
                switch (i){
                    case 0: // 법정동
                        legalcode = jsonObject.getJSONObject("region").getJSONObject("area1").getString("name") + " "
                                +jsonObject.getJSONObject("region").getJSONObject("area2").getString("name") + " "
                                +jsonObject.getJSONObject("region").getJSONObject("area3").getString("name") ;
                        break;
                    case 1: //지번
                         bulidAdress = jsonObject.getJSONObject("region").getJSONObject("area1").getString("name") + " "
                                +jsonObject.getJSONObject("region").getJSONObject("area2").getString("name") + " "
                                +jsonObject.getJSONObject("region").getJSONObject("area3").getString("name") + " "
                                +jsonObject.getJSONObject("land").getString("number1");


                         if( !(jsonObject.getJSONObject("land").getString("number2").equals("")) ){ // 토지 부번호가 존재할 경우
                             bulidAdress += "-" + jsonObject.getJSONObject("land").getString("number2");
                         }
                         break;
                    case 2: // 행정동
                        admcode = jsonObject.getJSONObject("region").getJSONObject("area1").getString("name") + " "
                                +jsonObject.getJSONObject("region").getJSONObject("area2").getString("name") + " "
                                +jsonObject.getJSONObject("region").getJSONObject("area3").getString("name") ;
                        break;
                    case 3:
                        roadAdress = jsonObject.getJSONObject("region").getJSONObject("area1").getString("name") + " "
                                +jsonObject.getJSONObject("region").getJSONObject("area2").getString("name") + " "
                                +jsonObject.getJSONObject("region").getJSONObject("area3").getString("name") + " "
                                +jsonObject.getJSONObject("land").getString("number1");

                        if( !(jsonObject.getJSONObject("land").getString("number2").equals("")) ){ // 토지 부번호가 존재할 경우
                            roadAdress += "-" + jsonObject.getJSONObject("land").getString("number2");
                        }
                        zipCode = jsonObject.getJSONObject("land").getJSONObject("addition1").getString("value");
                        break;
                    default:
                        break;
                }
            }
            Gc gc = new Gc(roadAdress,bulidAdress,legalcode,admcode,zipCode);
            return gc;
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }
}
