package com.example.storyapp.demo.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.storyapp.demo.entities.Message;
import com.example.storyapp.demo.entities.PublicUser;
import com.example.storyapp.demo.entities.User;

public interface UserRepo extends JpaRepository<User,Integer>{

    public Optional<User> findByUsername(String username);

    @Query("SELECT new com.example.storyapp.demo.entities.PublicUser(u.username) FROM User u")
    public List<PublicUser> findPublicUsers();


    
}
