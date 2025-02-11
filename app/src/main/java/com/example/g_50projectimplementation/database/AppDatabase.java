package com.example.g_50projectimplementation.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.example.g_50projectimplementation.database.entity.Client;
import com.example.g_50projectimplementation.database.entity.Staff;

@Database(entities = {Client.class, Staff.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ClientDao clientDao();
    public abstract StaffDao staffDao();

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
