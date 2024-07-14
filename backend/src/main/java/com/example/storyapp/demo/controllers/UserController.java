package com.example.storyapp.demo.controllers;

import java.util.List;
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

import com.example.storyapp.demo.entities.Message;
import com.example.storyapp.demo.entities.PublicUser;
import com.example.storyapp.demo.services.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {
    
    @Autowired
    private UserService userService;


    @PostMapping("/sendMessage")
    public ResponseEntity<Message> sendMessage(@RequestBody Map<String, String> requestData) {
       ResponseEntity<Message> message = userService.sendMessage(requestData.get("sender"),requestData.get("receiver"),requestData.get("messagecontent"));
       return message;
    }

    @GetMapping("/getAllUsers")
    public List<PublicUser> getAllUsers(){
        
        List<PublicUser> list = userService.getAllUsers();
        return list;


    }

    @GetMapping("/getSendersAndReceiverMessages")
    public List<Message> getSendersAndReceiverMessages(@RequestParam String sender,@RequestParam String receiver){
        List<Message> list = userService.getSendersAndReceiverMessages(sender,receiver);
        return list;
    }

  


}
