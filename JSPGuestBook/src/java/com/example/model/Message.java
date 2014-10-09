/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.example.model;

import java.sql.Timestamp;

/**
 *
 * @author Dmitry
 */
public class Message {

    public Message() {
    }

    public Message(String author, String content) {
        this.author = author;
        this.content = content;
    }

    public Message(String author, String content, Timestamp date) {
        this.author = author;
        this.content = content;
        this.date = date;
    }
    
    public Message(String author, String content, Timestamp date, Integer id) {
        this.author = author;
        this.content = content;
        this.date = date;
        this.id = id;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public Integer getId() {
        return id;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    private String author;
    private String content;
    private Timestamp date;
    private Integer id;
}
