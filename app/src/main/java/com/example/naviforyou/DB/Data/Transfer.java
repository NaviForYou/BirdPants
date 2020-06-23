package com.example.naviforyou.DB.Data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity
public class Transfer {
    @PrimaryKey(autoGenerate = true)
    int id;

    int line;
    int station;
    int transferNum;
    ArrayList<String> transferLine;

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getStation() {
        return station;
    }

    public void setStation(int station) {
        this.station = station;
    }

    public int getId() { return id; }

    public void setId(int id) {  this.id = id;  }

    public int getTransferNum() {
        return transferNum;
    }

    public void setTransferNum(int transferNum) {
        this.transferNum = transferNum;
    }

    public ArrayList<String> getTransferLine() {
        return transferLine;
    }

    public void setTransferLine(ArrayList<String> transferLine) {
        this.transferLine = transferLine;
    }
}
