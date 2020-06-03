package com.example.naviforyou;

public class SearchData {
    //검색 데이터
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

    public void setSearchData(String placeName, double x, double y) {
        this.placeName = placeName;
        X = x;
        Y = y;
        this.isData = true;
    }

    public void setSearchData(String placeName, String x, String y) {
        this.placeName = placeName;
        X = Double.parseDouble(x);
        Y = Double.parseDouble(y);
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

    public String getX_toString() {
        return String.valueOf(X);
    }

    public double getY() {
        return Y;
    }

    public String getY_toString() {
        return String.valueOf(Y);
    }

    public String getPlaceName() {
        return placeName;
    }

    public void clear(){
        this.isData = false;
        placeName = "";
        X = 0;
        Y = 0;
    }

}
