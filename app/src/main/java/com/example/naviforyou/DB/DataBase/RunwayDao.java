package com.example.naviforyou.DB.DataBase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.naviforyou.DB.Data.Runway;

import java.util.List;

@Dao
public interface RunwayDao {
    @Query("SELECT * FROM Runway")
    List<Runway> getAll();

    @Insert
    void insert(Runway runway);

    @Update
    void update(Runway runway);

    @Delete
    void delete(Runway runway);
}
