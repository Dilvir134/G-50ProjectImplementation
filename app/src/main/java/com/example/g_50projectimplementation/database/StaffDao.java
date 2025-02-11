package com.example.g_50projectimplementation.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.g_50projectimplementation.database.entity.Staff;

import java.util.List;


@Dao
public interface StaffDao {
    @Insert
    void insert(Staff staff);

    @Update
    void update(Staff staff);

    @Delete
    void delete(Staff staff);

    @Query("SELECT * FROM staff")
    List<Staff> getAllStaff();

    @Query("SELECT * FROM staff WHERE id = :staffId")
    Staff getStaffById(int staffId);
}