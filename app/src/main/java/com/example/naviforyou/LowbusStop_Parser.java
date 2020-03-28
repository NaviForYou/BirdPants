package com.example.naviforyou;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class LowbusStop_Parser {
    private String serviceKey = "6379794b6b636b6439354363704c51";

    private String stid = "34550";//정류장 id

    private String Xml = "";

    public void connectSeoul(){
        try {
            stid = URLEncoder.encode(stid,"UTF-8");

            String apiURL = "http://ws.bus.go.kr/api/rest/arrive/getLowArrInfoByStId?stId=34550";

            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
           con.setRequestProperty("reqStr",serviceKey);

            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else { // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }

            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            Xml = response.toString();
            Log.d("TEST2", "xml => " + Xml);

            /*
            //자원을 파싱할 객체 준비
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();

            //각 요소 반복 수행처리
            int parserEvent = parser.getEventType();

            while (parserEvent != parser.END_DOCUMENT) {//xml파일의 끝을 읽기까지 반복
                switch (parserEvent) {
                    case XmlPullParser.START_DOCUMENT: //다큐먼트 시작일 때
                        break;
                    case XmlPullParser.START_TAG:
                        Log.i("TAG", parser.getName());
                        if (parser.getName().equals("document")) {
                            int count = parser.getAttributeCount(); //"documnet"하위의 속성? 의 갯수를 반환한다.
                            Log.i("TAG", "parser.getAttributeCount() : " + count);
                            for (int i = 0; i < count; i++) {
                                Log.i("TAG", parser.getAttributeName(i));
                                Log.i("TAG", parser.getAttributeValue(i));
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        Log.i("TAG", "END_TAG : " + parser.getName());
                        break;
                    case XmlPullParser.TEXT:
                        Log.i("TAG", parser.getText()); //읽다가 TEXT가 나올 경우 TEXT 쓰기
                        break;
                }
                parserEvent = parser.next(); //parser가 다음을 가르키게 하기
            }*/

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
