package com.example.naviforyou.model;

import android.database.sqlite.SQLiteOpenHelper;

public class FavPlace {




    private String placeTable;
    private String key_id;

    public FavPlace() {
    }

    public FavPlace(String placeTable, String key_id) {
        this.placeTable = placeTable;
        this.key_id = key_id;
    }

    public String getPlaceTable() {
        return placeTable;
    }

    public void setPlaceTable(String placeTable) {
        this.placeTable = placeTable;
    }

    public String getKey_id() {
        return key_id;
    }

    public void setKey_id(String key_id) {
        this.key_id = key_id;
    }
}
