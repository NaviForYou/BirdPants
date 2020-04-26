package com.example.naviforyou.data;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/*
정류소 id를 통한 저상버스 도착 시간
 */

public class LowbusStop_Parser {
    private String serviceKey = "0yQdWiRaG7nL%2F5nzw48SVBhy3N3YdLiD%2Bfm2YeCwzHPJLr013WaNHcRQk4i2clUzsr4VbIAkROY%2FNl60Fi2JXg%3D%3D";

    private String stid = "22167";//정류장 id

    public ArrayList<LowbusStop> connectSeoul(String[] stids){
        try {
            this.stid = URLEncoder.encode(stids[0],"UTF-8");

            String apiURL = "http://ws.bus.go.kr/api/rest/stationinfo/getLowStationByUid?ServiceKey="+serviceKey+"&arsId="+stid;

            URL url = new URL(apiURL);

           return parser(url.openStream());

        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<LowbusStop> parser(InputStream xml) {
        LowbusStop lo = null;
        ArrayList<LowbusStop> list = new ArrayList<>();

        try {
            //자원을 파싱할 객체 준비
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();

            parser.setInput(xml,null);
            //각 요소 반복 수행처리
            int parserEvent = parser.getEventType();

            while (parserEvent != parser.END_DOCUMENT) {//xml파일의 끝을 읽기까지 반복
                switch (parserEvent) {
                    case XmlPullParser.START_DOCUMENT: //다큐먼트 시작일 때
                        break;
                    case XmlPullParser.START_TAG:
                        Log.i("TAG", parser.getName());
                        // 시작 태그 이름을 가져옴
                        String tagName = parser.getName();

                       if(tagName.equalsIgnoreCase("itemList") ){
                           lo = new LowbusStop();
                       }else if(tagName.equalsIgnoreCase("arrmsg1") ){
                           String time1 = parser.nextText(); // 값
                           lo.setTime1(time1);
                       }else if (tagName.equalsIgnoreCase("arrmsg2")) {
                           String time2 = parser.nextText(); // 값
                           lo.setTime2(time2);
                       }else if(tagName.equalsIgnoreCase("rtNm")){
                           String rtName = parser.nextText();
                           lo.setRtName(rtName);
                       }
                        break;
                    case XmlPullParser.END_TAG:
                        String lasttagName = parser.getName();
                        if(lasttagName.equalsIgnoreCase("itemList")){
                            list.add(lo);
                        }
                        break;
                }
                parserEvent = parser.next(); //parser가 다음을 가르키게 하기
            }

            return list;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}

