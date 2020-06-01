package com.example.naviforyou.API;

import java.io.Serializable;

//심벌 클릭시 해당 주소값 저장
public class Gc implements Serializable {
    private String buildName;
    private String roadAdress;
    private String bulidAdress;
    private String legalCode; // 법정동
    private String admCode; //행정동
    private String zipCode; //우편번호



    private String X;
    private String Y;



    public Gc(String roadAdress, String bulidAdress, String legalCode, String admCode, String zipCode) {
        this.roadAdress = roadAdress;
        this.bulidAdress = bulidAdress;
        this.legalCode = legalCode;
        this.admCode = admCode;
        this.zipCode = zipCode;
    }

    public String getBuildName() { return buildName; }

    public void setBuildName(String buildName) {
        this.buildName = buildName;
    }

    public String getZipCode() { return zipCode; }

    public String getLegalCode() { return legalCode; }

    public String getAdmCode() { return admCode;  }

    public String getBulidAdress() {
        return bulidAdress;
    }

    public String getRoadAdress() {
        return roadAdress;
    }

    public String getX() { return X; }

    public void setX(String x) { X = x; }

    public String getY() { return Y; }

    public void setY(String y) {  Y = y; }
}
