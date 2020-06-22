package com.example.naviforyou.DB.Data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Runway {
    @PrimaryKey(autoGenerate = true)
    int id;

    double startX;
    double startY;
    double endX;
    double endY;

    public double getStartX() {
        return startX;
    }

    public void setStartX(double startX) {
        this.startX = startX;
    }

    public double getStartY() {
        return startY;
    }

    public void setStartY(double startY) {
        this.startY = startY;
    }

    public double getEndX() {
        return endX;
    }

    public void setEndX(double endX) {
        this.endX = endX;
    }

    public double getEndY() {
        return endY;
    }

    public void setEndY(double endY) {
        this.endY = endY;
    }

    @Override
    public String toString() {
        return "Runway{" +
                "id=" + id +
                ", startX=" + startX +
                ", startY=" + startY +
                ", endX=" + endX +
                ", endY=" + endY +
                '}';
    }
}
