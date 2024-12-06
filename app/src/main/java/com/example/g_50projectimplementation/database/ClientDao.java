package com.example.g_50projectimplementation.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.g_50projectimplementation.database.entity.Client;

import java.util.List;

@Dao
public interface ClientDao {
    @Insert
    void insert(Client client);

    @Update
    void update(Client client);

    @Delete
    void delete(Client client);

    @Query("SELECT * FROM clients")
    List<Client> getAllClients();

    @Query("SELECT * FROM clients WHERE id = :clientId")
    Client getClientById(int clientId);
}
