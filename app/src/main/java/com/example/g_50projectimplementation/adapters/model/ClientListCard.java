package com.example.g_50projectimplementation.adapters.model;

import android.net.Uri;

public class ClientListCard {

    private String title;
    private String location;
    private Uri imageUri;

    public ClientListCard(String title, String location, Uri imageUri) {
        this.title = title;
        this.location = location;
        this.imageUri = imageUri;
    }

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

    public Uri getImageUri() { return imageUri; }


}
