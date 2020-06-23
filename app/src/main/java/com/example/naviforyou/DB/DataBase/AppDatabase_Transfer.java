package com.example.naviforyou.DB.DataBase;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.naviforyou.DB.Data.Converters;
import com.example.naviforyou.DB.Data.Transfer;

@Database(entities = {Transfer.class},version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase_Transfer extends RoomDatabase {
    public abstract TransferDao transferDao();
}
