package com.example.storyapp.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "messages")
public class Message {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    
    private String sender;
    private String receiver;
    private String messagecontent;
    private String messagetime;
    private String messagetimeinmilli;

    
    public int getId() {
        return id;
    }
    public Message() {
    }
    public void setId(int id) {
        this.id = id;
    }
    
    public Message(int id, String sender, String receiver, String messagecontent, String messagetime,String messagetimeinmilli) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.messagecontent = messagecontent;
        this.messagetime = messagetime;
        this.messagetimeinmilli = messagetimeinmilli;
    }

    public String getSender() {
        return sender;
    }
    public void setSender(String sender) {
        this.sender = sender;
    }
    public String getReceiver() {
        return receiver;
    }
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
    public String getMessagetimeinmilli() {
        return messagetimeinmilli;
    }
    public void setMessagetimeinmilli(String messagetimeinmilli) {
        this.messagetimeinmilli = messagetimeinmilli;
    }
    public String getMessagecontent() {
        return messagecontent;
    }
    public void setMessagecontent(String messagecontent) {
        this.messagecontent = messagecontent;
    }
    public String getMessagetime() {
        return messagetime;
    }
    public void setMessagetime(String messagetime) {
        this.messagetime = messagetime;
    }


}
