package com.example.naviforyou.data;

//심벌 클릭시 해당 주소값 저장
public class Gc {
    private String roadAddress;
    private String buildAddress;
    private String legalCode; // 법정동
    private String admCode; //행정동
    private String zipCode; //우편번호
    private String buildName; //건물이름

    public Gc(String roadAddress, String buildAddress, String legalCode, String admCode, String zipCode) {
        this.roadAddress = roadAddress;
        this.buildAddress = buildAddress;
        this.legalCode = legalCode;
        this.admCode = admCode;
        this.zipCode = zipCode;
    }

    public String getBuildName() {
        return buildName;
    }

    public void setBuildName(String bulidName) {
        this.buildName = bulidName;
    }

    public String getZipCode() { return zipCode; }

    public String getLegalCode() { return legalCode; }

    public String getAdmCode() { return admCode;  }

    public String getBuildAddress() {
        return buildAddress;
    }

    public String getRoadAddress() {
        return roadAddress;
    }
}
