package com.example.storyapp.demo.services;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.aspectj.weaver.patterns.TypePatternQuestions.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.storyapp.demo.entities.Message;
import com.example.storyapp.demo.entities.PublicUser;
import com.example.storyapp.demo.entities.Role;
import com.example.storyapp.demo.entities.User;
import com.example.storyapp.demo.repos.MessageRepo;
import com.example.storyapp.demo.repos.UserRepo;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    MessageRepo messageRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User name not found"));
        ;
        return user;

    }

    public ResponseEntity<Message> sendMessage(String sender,String receiver,String messagecontent) {

        String timeStamp = new SimpleDateFormat("dd/MM/yyyy++HHmm").format(Calendar.getInstance().getTime());
        String messagetimeinmilli = String.valueOf(System.currentTimeMillis());

        Message message = messageRepo.save(new Message(0,sender,receiver,messagecontent,timeStamp,messagetimeinmilli));

        return new ResponseEntity<>(message, HttpStatus.OK);

    }

    public List<PublicUser> getAllUsers(){
       
        return userRepo.findPublicUsers();
    
    }

    public List<Message> getSendersAndReceiverMessages(String sender,String receiver){
        List<Message> list1 = messageRepo.findBySenderAndReceiver(sender,receiver);
        List<Message> list2 = messageRepo.findBySenderAndReceiver(receiver,sender);

        List<Message> list3 = new ArrayList<>();

        list3.addAll(list1);
         list3.addAll(list2);

         Collections.sort(list3, new Comparator<Message>() {
            @Override
            public int compare(Message person1, Message person2) {
                return Long.compare(Long.parseLong(person1.getMessagetimeinmilli()), Long.parseLong(person2.getMessagetimeinmilli()));
            }
        });

        return list3;
    

    }

    

}

