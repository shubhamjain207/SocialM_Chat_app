package com.example.storyapp.demo.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.storyapp.demo.entities.Message;

public interface MessageRepo extends JpaRepository<Message,Integer>{

    public List<Message> findBySenderAndReceiver(String sender,String receiver);
    

}
