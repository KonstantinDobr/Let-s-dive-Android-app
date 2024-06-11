package com.example.letsdive.authorization.data.dto;

public class Container {
    private final String email;
    private final String information;
    private final String photoUrl;

    public Container(String email, String information, String photoUrl) {
        this.email = email;
        this.information = information;
        this.photoUrl = photoUrl;
    }

    public String getEmail() {
        return email;
    }

    public String getInformation() {
        return information;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }
}
