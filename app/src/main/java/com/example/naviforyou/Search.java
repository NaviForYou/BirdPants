package com.example.naviforyou;

public class Search {
    private String placeName;
    private String roadAddress;
    private String bulidAddress;
    private String phone_number;
    private String distancs;



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
