package com.example.naviforyou.DB.Data;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Runway_Parser {
    public String getJsonString(Context context)
    {
        String json = "";

        try {
            InputStream is = context.getAssets().open("runway.json");
            int fileSize = is.available();

            byte[] buffer = new byte[fileSize];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");
            Log.d("Json","json : " + json);
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }

        return json;
    }

    public ArrayList<Runway> Parser(String json){
        ArrayList<Runway> runwayArrayList = new ArrayList<>();
        try{
            JSONArray jsonArray = new JSONArray(json);
            for(int i = 0; i < jsonArray.length(); i++) {
                Runway runway = new Runway();
                double startX;
                double startY;
                double endX;
                double endY;

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                startX = jsonObject.getDouble("start_x");
                startY = jsonObject.getDouble("start_y");
                endX = jsonObject.getDouble("end_x");
                endY = jsonObject.getDouble("end_y");

                runway.setStartX(startX);
                runway.setStartY(startY);
                runway.setEndX(endX);
                runway.setEndY(endY);
                runwayArrayList.add(runway);
            }

        }catch (JSONException e) {
            e.printStackTrace();
        }

        return runwayArrayList;
    }

}
