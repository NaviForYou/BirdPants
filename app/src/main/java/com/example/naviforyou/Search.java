package com.example.naviforyou;

public class Search {
    String placeName;
    String roadAddress;
    String bulidAddress;
    String phone_number;
    String distancs;



    public Search(String placeName, String roadAddress, String bulidAddress, String phone_number, String distancs) {
        this.placeName = placeName;
        this.roadAddress = roadAddress;
        this.bulidAddress = bulidAddress;
        this.phone_number = phone_number;
        this.distancs = distancs;
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



}
