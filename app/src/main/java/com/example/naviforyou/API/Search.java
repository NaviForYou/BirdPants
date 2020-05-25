package com.example.naviforyou.API;

public class Search {
    private String placeName;
    private String roadAddress;
    private String bulidAddress;
    private String phone_number;
    private String distancs;
    private double longitude_X;
    private double latitude_Y;



    public Search(String placeName, String roadAddress, String buildAddress, String phone_number, String distance) {
        this.placeName = placeName;
        this.roadAddress = roadAddress;
        this.bulidAddress = buildAddress;
        this.phone_number = phone_number;
        this.distancs = distance;
    }

    public String getPlaceName() {
        return placeName;
    }

    public String getRoadAddress() {
        return roadAddress;
    }

    public String getBulidAddress() {
        return bulidAddress;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getDistancs() {
        return distancs;
    }

    public double getLongitude_X() { return longitude_X; }

    public double getLatitude_Y() { return latitude_Y; }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public void setRoadAddress(String roadAddress) {
        this.roadAddress = roadAddress;
    }

    public void setBulidAddress(String bulidAddress) {
        this.bulidAddress = bulidAddress;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public void setDistancs(String distancs) {
        this.distancs = distancs;
    }

    public void setLongitude_X(double longitude_X) {  this.longitude_X = longitude_X; }

    public void setLatitude_Y(double latitude_Y) { this.latitude_Y = latitude_Y; }


}
