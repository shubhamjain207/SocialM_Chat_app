package com.example.storyapp.demo.entities;

public class PublicPhoto {
    
    
    private String username;
    private String photoUrl;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public PublicPhoto() {
    }

    public PublicPhoto(String username, String photoUrl) {
        this.username = username;
        this.photoUrl = photoUrl;
    }
    

}
