package com.example.naviforyou.DB.DataBase;

import androidx.room.Database;

import com.example.naviforyou.DB.Data.Transfer;

@Database(entities = {Transfer.class},version = 1)
public abstract class AppDatabase_Transfer {
    public abstract TransferDao transferDao();
}
