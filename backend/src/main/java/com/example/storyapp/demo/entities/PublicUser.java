package com.example.storyapp.demo.entities;

import jakarta.persistence.Entity;

public class PublicUser {

    private String username;

    public PublicUser() {
        
    }

    public PublicUser(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    
}
