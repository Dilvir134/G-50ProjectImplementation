package com.example.g_50projectimplementation.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "staff")
public class Staff {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private String position;
    private String phone;
    private String imageUrl;
    private String emergencyPhone;
    private String emergencyContactName;

    public Staff(String name, String position, String phone, String imageUrl, String emergencyContactName, String emergencyPhone) {
        this.name = name;
        this.position = position;
        this.phone = phone;
        this.imageUrl = imageUrl;
        this.emergencyContactName = emergencyContactName;
        this.emergencyPhone = emergencyPhone;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public String getPhone() {
        return phone;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getEmergencyPhone() {
        return emergencyPhone;
    }

    public String getEmergencyContactName() {
        return emergencyContactName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setEmergencyPhone(String emergencyPhone) {
        this.emergencyPhone = emergencyPhone;
    }

    public void setEmergencyContactName(String emergencyContactName) {
        this.emergencyContactName = emergencyContactName;
    }
}
