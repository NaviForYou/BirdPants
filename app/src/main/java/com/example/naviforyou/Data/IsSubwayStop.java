package com.example.naviforyou.Data;

import java.io.Serializable;

public class IsSubwayStop implements Serializable {
    String station;
    int laneType;
    double X;
    double Y;

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public double getX() {
        return X;
    }

    public void setX(double x) {
        X = x;
    }

    public double getY() {
        return Y;
    }

    public void setY(double y) {
        Y = y;
    }

    public int getLaneType() {
        return laneType;
    }

    public void setLaneType(int laneType) {
        this.laneType = laneType;
    }
}
