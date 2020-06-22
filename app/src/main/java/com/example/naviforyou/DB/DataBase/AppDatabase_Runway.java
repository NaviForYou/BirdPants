package com.example.naviforyou.DB.DataBase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.naviforyou.DB.Data.Runway;

@Database(entities = {Runway.class}, version = 1)
public abstract class AppDatabase_Runway extends RoomDatabase {
    public abstract RunwayDao runwayDao();
}
