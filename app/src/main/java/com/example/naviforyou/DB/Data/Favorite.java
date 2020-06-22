package com.example.naviforyou.DB.Data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Favorite {
    @PrimaryKey(autoGenerate = true)
    int id;
    
    String buildName;
    String address;
    double X;
    double Y;

    public Favorite(String buildName, String address, double X, double Y) {
        this.buildName = buildName;
        this.address = address;
        this.X = X;
        this.Y = Y;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

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
                "id='" + id + '\'' +
                ", buildName='" + buildName + '\'' +
                ", address='" + address + '\'' +
                ", X=" + X +
                ", Y=" + Y +
                '}';
    }
}

