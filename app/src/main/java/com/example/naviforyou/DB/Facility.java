package com.example.naviforyou.DB;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Facility implements Serializable {
    @PrimaryKey(autoGenerate = true)
    int id;



    String address;

    int toilet;
    int parking_lot;
    int entrance;
    int height;
    int elevator;

    public Facility(String address, int toilet, int parking_lot, int entrance, int height, int elevator) {
        this.address = address;
        this.toilet = toilet;
        this.parking_lot = parking_lot;
        this.entrance = entrance;
        this.height = height;
        this.elevator = elevator;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getHeight() { return height; }

    public void setHeight(int height) { this.height = height; }

    public int getElevator() { return elevator; }

    public void setElevator(int elevator) { this.elevator = elevator; }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getToilet() {
        return toilet;
    }

    public void setToilet(int toilet) {
        this.toilet = toilet;
    }

    public int getParking_lot() {
        return parking_lot;
    }

    public void setParking_lot(int parking_lot) {
        this.parking_lot = parking_lot;
    }

    public int getEntrance() {
        return entrance;
    }

    public void setEntrance(int entrance) {
        this.entrance = entrance;
    }

    @Override
    public String toString() {
        return "Facility{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", toilet=" + toilet +
                ", parking_lot=" + parking_lot +
                ", entrance=" + entrance +
                ", height=" + height +
                ", elevator=" + elevator +
                '}';
    }
}
