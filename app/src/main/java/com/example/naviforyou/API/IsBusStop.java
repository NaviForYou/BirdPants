package com.example.naviforyou.API;

import java.io.Serializable;

public class IsBusStop implements Serializable {
    String id;
    String name;
    double X;
    double Y;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getX() { return X; }

    public void setX(double x) { X = x; }

    public double getY() { return Y; }

    public void setY(double y) { Y = y; }
}
