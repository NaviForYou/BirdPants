package com.example.naviforyou.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FavoriteDao {
    @Query("SELECT * FROM Favorite")
    List<Favorite> getAll();

    @Query("SELECT * FROM favorite WHERE title LIKE :search")
    List<Favorite> findWithName(String search);

    @Insert
    void insert(Favorite favorite);

    @Update
    void update(Favorite favorite);

    @Delete
    void Delete(Favorite favorite);
}
