package com.example.naviforyou.DB.DataBase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.naviforyou.DB.Data.Facility;

import java.util.List;

@Dao
public interface FacilityDao {
    @Query("SELECT * FROM Facility")
    List<Facility> getAll();

    @Query("SELECT * FROM Facility WHERE address LIKE :search")
    List<Facility> findBuildWithAddress(String search);

    @Query("SELECT * FROM Facility WHERE X LIKE :X AND Y LIKE :Y")
    List<Facility> findWithXY(double X, double Y);

    @Insert
    void insert(Facility facility);

    @Update
    void update(Facility facility);

    @Delete
    void Delete(Facility facility);
}
