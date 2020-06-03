package com.example.naviforyou.DB;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Facility.class}, version = 1)
public abstract class AppDatebase extends RoomDatabase {
    public abstract FacilityDao facilityDao();
}
