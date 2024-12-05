package com.example.g_50projectimplementation.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.example.g_50projectimplementation.database.entity.Client;

@Database(entities = {Client.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ClientDao clientDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "client_database"
                    ).build();
                }
            }
        }
        return INSTANCE;
    }
}
