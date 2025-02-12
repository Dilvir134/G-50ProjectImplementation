package com.example.g_50projectimplementation.adapters.model;

import android.net.Uri;

public class StaffListCard {

    private int id;
    private String title;
    private String position;
    private Uri imageUri;

    public StaffListCard(int id, String title, String position, Uri imageUri) {
        this.id = id;
        this.title = title;
        this.position = position;
        this.imageUri = imageUri;
    }

    public String getTitle() {
        return title;
    }

    public String getPosition() {
        return position;
    }

    public Uri getImageUri() { return imageUri; }

    public int getId() { return id; }
}
