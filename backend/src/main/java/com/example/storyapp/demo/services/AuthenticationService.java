package com.example.storyapp.demo.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.storyapp.demo.entities.LoginUserDto;
import com.example.storyapp.demo.entities.Role;
import com.example.storyapp.demo.entities.User;
import com.example.storyapp.demo.repos.UserRepo;

@Service
public class AuthenticationService {

  @Autowired
  private UserDetailsService userService;

  @Autowired
  private UserRepo userRepo;

  private LoginUserDto loginUser;

  public ResponseEntity<User> registerUser(String username, String email, String password) {

    Set<Role> authorities = new HashSet<>();

    authorities.add(new Role("USER"));

    User user = userRepo.save(new User(0, username, email, password, authorities));

    return new ResponseEntity<>(user, HttpStatus.OK);

  }

  public ResponseEntity<LoginUserDto> login(String username, String password) {

    try {

      User user = (User) userService.loadUserByUsername(username);

     
      if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
        loginUser = new LoginUserDto(user, "Login Successfull");
      }

      else if (!user.getPassword().equals(password)) {
        loginUser = new LoginUserDto(null, "Incorrect Password");
      }

    } catch (Exception e) {
        loginUser = new LoginUserDto(null, "User name not found.");
    }

    return new ResponseEntity<>(loginUser, HttpStatus.OK);

  }

}
