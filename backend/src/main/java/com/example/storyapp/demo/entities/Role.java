package com.example.storyapp.demo.entities;


import org.springframework.security.core.GrantedAuthority;

public class Role implements GrantedAuthority {

    private String authority;
    
    public Role(){
        super();
    }

    public Role(String authority){
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
       
       return this.authority;
    }
    
}
