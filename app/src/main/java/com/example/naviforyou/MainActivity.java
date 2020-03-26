package com.example.naviforyou;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private TextView mTextViewResult;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextViewResult = findViewById(R.id.text_view_result);
        Button buttonParse = findViewById(R.id.button_parse);

        mQueue = Volley.newRequestQueue( this);

        buttonParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonParse();
            }
        });
    }

    private void jsonParse()  {
        String url = "https://api.myjson.com/bins/7piyk";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("DATA");

                            for (int i=0; i<jsonArray.length(); i++) {
                                JSONObject DATA = jsonArray.getJSONObject(i);

                                String course1 = DATA.getString("course1");
                                String course2 = DATA.getString("course2");
                                String station = DATA.getString("station");
                                int no = DATA.getInt("no");
                                String telno_info = DATA.getString("telno_info");
                                String station_image = DATA.getString("station_image");
                                String exit_info = DATA.getString("exit_info");
                                String line = DATA.getString("line");
                                String icon_path = DATA.getString("icon_path");
                                String elevater = DATA.getString("elevater");
                                String elevater_txt = DATA.getString("elevater_txt");
                                String time_info2 = DATA.getString("time_info2");
                                String time_info3 = DATA.getString("time_info3");
                                String line_name = DATA.getString("line_name");
                                String time_info1 = DATA.getString("time_info1");
                                String station_image2 = DATA.getString("station_image2");
                                String time_info4 = DATA.getString("time_info4");
                                String useyn  = DATA.getString("useyn");
                                mTextViewResult.append( "운행방향 1: "+course1 + "\n" + "운행방향2: "+course2 + "\n" + "역 명칭: "+station + "\n"
                                +"고유번호: "+ String.valueOf(no) + "\n" + "전화번호 안내: " + telno_info + "\n" + "역 이미지: "+ station_image + "\n" +
                                        "출구 정보: "+exit_info + "\n " + "노선: "+ line + "\n"+ "편의시설 아이콘 이미지 목록: "+icon_path + "\n" + "엘리베이터 동영상: "+elevater + "\n" +
                                        "엘리베이터 자막: "+ elevater_txt + "\n" + " 코스 1 막차 : "+ time_info2 + "\n" + "코스 2 첫차: " + time_info3 + "\n" + "노선 명칭: " + line_name +
                                        "\n " + "코스 1 첫차: "+ time_info1 + "\n" + "지하철 팝업 이미지: "+ station_image2 + "\n" + "코스 2 막차: " + time_info4 + "\n" + "사용여부: " + useyn + "\n\n\n");

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);

    }
}
