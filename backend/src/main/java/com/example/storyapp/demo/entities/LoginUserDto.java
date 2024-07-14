package com.example.storyapp.demo.entities;

public class LoginUserDto {
    
    private User user;
    private String message;

    
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public String getMessage() {
        return message;
    }
    public LoginUserDto() {
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public LoginUserDto(User user, String message) {
        this.user = user;
        this.message = message;
    }
    


}
