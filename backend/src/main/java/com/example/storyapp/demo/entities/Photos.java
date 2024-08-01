package com.example.storyapp.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "photos")
public class Photos {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String username;
    private String photoUrl;
    
    public Photos() {
    }
    private String timeUploaded;
   

    public Photos(int photoId, String username, String photoUrl, String timeUploaded) {
        id = photoId;
        this.username = username;
        this.photoUrl = photoUrl;
        this.timeUploaded = timeUploaded;
    }

     
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
    public String getTimeUploaded() {
        return timeUploaded;
    }
    public void setTimeUploaded(String timeUploaded) {
        this.timeUploaded = timeUploaded;
    }

    

}
