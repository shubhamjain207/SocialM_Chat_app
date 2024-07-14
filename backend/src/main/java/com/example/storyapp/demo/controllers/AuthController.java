package com.example.storyapp.demo.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.storyapp.demo.entities.LoginUserDto;
import com.example.storyapp.demo.entities.User;
import com.example.storyapp.demo.services.AuthenticationService;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

  @Autowired
  private AuthenticationService service;


    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody Map<String, String> requestData) {
        
       ResponseEntity<User> user1 = service.registerUser(requestData.get("username"), requestData.get("email"),requestData.get("password"));
       return user1 ;

     

    }

    @PostMapping("/login")
    public ResponseEntity<LoginUserDto> loginUser(@RequestBody Map<String, String> requestData) {

         ResponseEntity<LoginUserDto> user = service.login(requestData.get("username"),requestData.get("password"));
         return user;
      
    }

    
}
