package com.example.naviforyou.DB.DataBase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.naviforyou.DB.Data.Transfer;

import java.util.List;

@Dao
public interface TransferDao {
    @Query("SELECT * FROM Transfer")
    List<Transfer> getAll();

    @Query("SELECT * FROM Transfer WHERE line LIKE :line AND station LIKE :station")
    List<Transfer> findTransfer(int line, int station);

    @Insert
    void insert(Transfer transfer);

    @Update
    void update(Transfer transfer);

    @Delete
    void  delete(Transfer transfer);
}
