package com.example.naviforyou.DB;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Favorite.class}, version = 1)
public abstract class AppDatabase_Favorite extends RoomDatabase {
    public abstract FavoriteDao favoriteDao();
}
