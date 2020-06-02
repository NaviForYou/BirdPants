package com.example.naviforyou;

public class SearchData {
    String type;
    String placeName;
    double X;
    double Y;
    boolean isData;

    public SearchData(String type) {
        this.isData = false;
        this.type = type;
        placeName = "";
        X = 0;
        Y = 0;
    }

    public void searchData(String placeName, double x, double y) {
        this.placeName = placeName;
        X = x;
        Y = y;
        this.isData = true;
    }


    public boolean isData() {
        return isData;
    }

    public void setData(boolean data) {
        isData = data;
    }

    public double getX() {
        return X;
    }

    public double getY() {
        return Y;
    }

    public String getPlaceName() {
        return placeName;
    }
}
