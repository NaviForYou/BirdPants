package com.example.naviforyou.API;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;

public class IsBusStop_Parser {
    private String serviceKey = "0yQdWiRaG7nL%2F5nzw48SVBhy3N3YdLiD%2Bfm2YeCwzHPJLr013WaNHcRQk4i2clUzsr4VbIAkROY%2FNl60Fi2JXg%3D%3D";
    private String X,Y;

    public IsBusStop connectSeoul(String[] coords){
        try {
            this.X = URLEncoder.encode(coords[1],"UTF-8");
            this.Y = URLEncoder.encode(coords[0],"UTF-8");
            Log.d("TEXT2", "X : " + coords[0] + "Y : " + coords[1]);
            String apiURL = "http://ws.bus.go.kr/api/rest/stationinfo/getStationByPos?ServiceKey="+serviceKey+"&tmX="+ X +"&tmY=" + Y +"&radius=10";

            URL url = new URL(apiURL);

            return parser(url.openStream());

        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public IsBusStop parser(InputStream xml) {
        IsBusStop isBusStop = null;

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
                            isBusStop = new IsBusStop();
                        }else if(tagName.equalsIgnoreCase("arsId") ){
                            String id = parser.nextText(); // 값
                            isBusStop.setId(id);
                        }else if (tagName.equalsIgnoreCase("stationNm")) {
                            String name = parser.nextText(); // 값
                            isBusStop.setName(name);
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        String lasttagName = parser.getName();
                        if(lasttagName.equalsIgnoreCase("itemList")){
                            break;
                        }
                        break;

                }
                parserEvent = parser.next(); //parser가 다음을 가르키게 하기
            }

            return isBusStop;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}