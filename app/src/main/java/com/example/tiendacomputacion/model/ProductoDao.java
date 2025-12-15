package com.example.tiendacomputacion.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ProductoDao {
    @Query("SELECT * FROM producto")
    List<Producto> getAll();

    @Insert
    void insertAll(Producto... productos);

    @Update
    void update(Producto producto);

    @Delete
    void delete(Producto producto);
}
