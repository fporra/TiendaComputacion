package com.example.tiendacomputacion.model;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Producto.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ProductoDao productoDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "database-tienda")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
