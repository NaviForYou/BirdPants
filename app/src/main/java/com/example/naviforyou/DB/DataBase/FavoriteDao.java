package com.example.naviforyou.DB.DataBase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.naviforyou.DB.Data.Favorite;

import java.util.List;

@Dao
public interface FavoriteDao {
    @Query("SELECT * FROM Favorite")
    LiveData<List<Favorite>> getAll();

    @Query("SELECT * FROM Favorite WHERE address LIKE :search")
    List<Favorite> findWithAddress(String search);

    @Query("SELECT * FROM Favorite WHERE X LIKE :X AND Y LIKE :Y")
    List<Favorite> findWithXY(double X, double Y);

    @Insert
    void insert(Favorite favorite);

    @Update
    void update(Favorite favorite);

    @Delete
    void Delete(Favorite favorite);
}
