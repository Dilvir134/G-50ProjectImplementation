package com.example.g_50projectimplementation.database.entity;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "clients")
public class Client {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String location;
    private String contactName;
    private String contactNumber;
    private String logoUrl;
    /*private String category;*/

    // Constructor
    public Client(String name, String location, String contactName, String contactNumber, String logoUrl) {
        this.name = name;
        this.location = location;
        this.contactName = contactName;
        this.contactNumber = contactNumber;
        this.logoUrl = logoUrl;
    }

    /*public Client(String name, String location, String contactName, String contactNumber, String logoUrl, String category) {
        this(name, location, contactName, contactNumber, logoUrl);
        this.category = category;
    }*/

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getContactName() { return contactName; }
    public void setContactName(String contactName) { this.contactName = contactName; }
    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }
    public String getLogoUrl() { return logoUrl; }
    public void setLogoUrl(String logoUrl) { this.logoUrl = logoUrl; }
/*    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }*/
}

