/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.example.model;

import com.example.servlets.AddMessageServlet;
import com.example.utils.ConnectionManager;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dmitry
 */
public class MessageManager {
    static Logger logger = Logger.getLogger(AddMessageServlet.class.getName());
    
    private static final String TABLE_NAME = "messages";
    
    private static final String ID_COLUMN_NAME = "id";
    private static final String AUTHOR_COLUMN_NAME = "author";
    private static final String CONTENT_COLUMN_NAME = "content";
    private static final String TIME_COLUMN_NAME = "timelog";
    
    private static final String SELECT_MESSAGES_QUERY = 
            "SELECT * FROM messages";
    private static final String DELETE_MESSAGE_QUERY = 
            "DELETE FROM messages WHERE " + ID_COLUMN_NAME + " = ?";
    private static final String INSERT_MESSAGE_QUERY = 
            "INSERT INTO " + TABLE_NAME + " (" + AUTHOR_COLUMN_NAME + ", " + 
                         CONTENT_COLUMN_NAME + ", " + 
                         TIME_COLUMN_NAME + ") VALUES (?, ?, ?)";
   
    static public List<Message> getAllMessages() {
        Connection connection = ConnectionManager.getConnection();
        if (connection == null) {
            logger.log(Level.SEVERE, "Couln't establish a connection to database");
            return null;
        }
        
        try {
            Statement statement = connection.createStatement();
            statement.execute(SELECT_MESSAGES_QUERY);
            ResultSet resultSet = statement.getResultSet();
            
            List<Message> messages = new ArrayList<>();
            while (resultSet.next()) {
                String author = resultSet.getString(AUTHOR_COLUMN_NAME);
                String content = resultSet.getString(CONTENT_COLUMN_NAME);
                Timestamp timestamp = resultSet.getTimestamp(TIME_COLUMN_NAME);
                Integer id = resultSet.getInt(ID_COLUMN_NAME);
                messages.add(new Message(author, content, timestamp, id));
            }
            
            return messages;
        } catch (SQLException ex) {
            Logger.getLogger(MessageManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    static public void addNewMessage(Message message) {
        Connection connection = ConnectionManager.getConnection();
        if (connection == null) {
            logger.log(Level.SEVERE, "Couln't establish a connection to database");
            return;
        }
        
        try {
            PreparedStatement ps = connection.prepareStatement(INSERT_MESSAGE_QUERY); 
            ps.setString(1, message.getAuthor());
            ps.setString(2, message.getContent());
            ps.setTimestamp(3, message.getDate());
            ps.execute();
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }
    
    static public void deleteMessage(int id) {
        Connection connection = ConnectionManager.getConnection();
        if (connection == null) {
            logger.log(Level.SEVERE, "Couln't establish a connection to database");
            return;
        }
        
        try {
            PreparedStatement ps = connection.prepareStatement(DELETE_MESSAGE_QUERY); 
            ps.setInt(1, id);
            ps.execute();
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }
}
