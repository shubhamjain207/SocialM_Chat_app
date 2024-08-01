package com.example.storyapp.demo.entities;

public class PublicPhoto {
    
    private String photoUrl;

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public PublicPhoto() {
    }

    public PublicPhoto(String photoUrl) {
        this.photoUrl = photoUrl;
    }
    

}
