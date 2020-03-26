package com.example.naviforyou;

//심벌 클릭시 해당 주소값 저장
public class Gc {
    private String roadAdress;
    private String bulidAdress;

    public Gc(String roadAdress, String bulidAdress) {
        this.roadAdress = roadAdress;
        this.bulidAdress = bulidAdress;
    }

    public String getBulidAdress() {
        return bulidAdress;
    }

    public void setBulidAdress(String bulidAdress) {
        this.bulidAdress = bulidAdress;
    }

    public String getRoadAdress() {
        return roadAdress;
    }

    public void setRoadAdress(String roadAdress) {
        this.roadAdress = roadAdress;
    }
}
