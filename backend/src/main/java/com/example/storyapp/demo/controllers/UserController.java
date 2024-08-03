package com.example.storyapp.demo.controllers;

import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.storyapp.demo.entities.Message;
import com.example.storyapp.demo.entities.Photos;
import com.example.storyapp.demo.entities.PublicPhoto;
import com.example.storyapp.demo.entities.PublicUser;
import com.example.storyapp.demo.services.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {
    
    @Autowired
    private UserService userService;

    @GetMapping("/currentUsername")
    public String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    @PostMapping("/sendMessage")
    public ResponseEntity<Message> sendMessage(@RequestBody Map<String, String> requestData) {
       ResponseEntity<Message> message = userService.sendMessage(requestData.get("sender"),requestData.get("receiver"),requestData.get("messagecontent"));
       return message;
    }

    @PostMapping("/uploadPhoto")
    public ResponseEntity<Photos> uploadPhoto(@RequestBody Map<String, String> requestData) {
       ResponseEntity<Photos> photo = userService.uploadPhoto(requestData.get("username"),requestData.get("photoUrl"));
       return photo;
    }



    @GetMapping("/getAllUsers")
    public List<PublicUser> getAllUsers(){
        
        List<PublicUser> list = userService.getAllUsers();
        return list;


    }


    @GetMapping("/getAllPhotos")
    public List<PublicPhoto> getAllPhotos(){
        
        List<PublicPhoto> list = userService.getAllPhotos();
        return list;


    }


    @GetMapping("/getSendersAndReceiverMessages")
    public List<Message> getSendersAndReceiverMessages(@RequestParam String sender,@RequestParam String receiver){
        List<Message> list = userService.getSendersAndReceiverMessages(sender,receiver);
        return list;
    }

  


}
