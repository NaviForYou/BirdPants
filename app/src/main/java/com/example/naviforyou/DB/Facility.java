package com.example.naviforyou.DB;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Facility {
    @PrimaryKey
    @NonNull
    String address;

    int toilet;
    int parking_lot;
    int entrance;

    public Facility(String address, int toilet, int parking_lot, int entrance) {
        this.address = address;
        this.toilet = toilet;
        this.parking_lot = parking_lot;
        this.entrance = entrance;
    }

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
}
