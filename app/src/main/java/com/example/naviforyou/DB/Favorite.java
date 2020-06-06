package com.example.naviforyou.DB;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Favorite {
    @PrimaryKey
    @NonNull
    String title;

    String buildName;
    String address;
    double X;
    double Y;

    public Favorite(@NonNull String title, String buildName, String address, double x, double y) {
        this.title = title;
        this.buildName = buildName;
        this.address = address;
        X = x;
        Y = y;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    public String getBuildName() {
        return buildName;
    }

    public void setBuildName(String buildName) {
        this.buildName = buildName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    @Override
    public String toString() {
        return "Favorite{" +
                "title='" + title + '\'' +
                ", buildName='" + buildName + '\'' +
                ", address='" + address + '\'' +
                ", X=" + X +
                ", Y=" + Y +
                '}';
    }
}

