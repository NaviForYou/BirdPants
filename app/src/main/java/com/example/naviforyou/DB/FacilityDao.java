package com.example.naviforyou.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FacilityDao {
    @Query("SELECT * FROM Facility")
    List<Facility> getAll();

    @Query("SELECT * FROM Facility WHERE address LIKE :search")
    public Facility findBuildWithAddress(String search);

    @Insert
    void insert(Facility facility);

    @Update
    void update(Facility facility);

    @Delete
    void Delete(Facility facility);
}
