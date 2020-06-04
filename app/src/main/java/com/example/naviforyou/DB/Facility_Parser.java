package com.example.naviforyou.DB;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Facility_Parser {
    public String getJsonString(Context context)
    {
        String json = "";

        try {
            InputStream is = context.getAssets().open("Wolgye1.json");
            int fileSize = is.available();

            byte[] buffer = new byte[fileSize];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }

        return json;
    }

    public ArrayList<Facility> Parser(String json){
        ArrayList<Facility> facilityArrayList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for(int i=0;i<jsonArray.length();i++){
                Facility facility;
                String address;
                int toilet;
                int parking_lot;
                int entrance;
                int height;
                int elevator;

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                address = jsonObject.getString("Address");
                toilet = jsonObject.getInt("Hwajang");
                parking_lot = jsonObject.getInt("Joocha");
                entrance = jsonObject.getInt("JubGun");
                height = jsonObject.getInt("Nopi");
                elevator = jsonObject.getInt("Elevator");

                facility = new Facility(address,toilet,parking_lot,entrance,height,elevator);
                facilityArrayList.add(facility);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return facilityArrayList;

    }

}
