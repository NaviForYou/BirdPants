package com.example.naviforyou.DB.DataBase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.naviforyou.DB.Data.Favorite;

@Database(entities = {Favorite.class}, version = 1)
public abstract class AppDatabase_Favorite extends RoomDatabase {
    public abstract FavoriteDao favoriteDao();
}
