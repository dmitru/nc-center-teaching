/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package egor.forum.modules;

import egor.forum.utils.ConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author egor
 */
public class MessageManager {
    static public final class Message {
        private final int id;
        private final String text;
        private final String author;
        private final Timestamp time;
        private final int parentId;

        public Message(int id, String text, String author, Timestamp time, int parentId) {
            this.id = id;
            this.text = text;
            this.author = author;
            this.time = time;
            this.parentId = parentId;
        }

        public int getId() {
            return id;
        }

        public String getText() {
            return text;
        }

        public String getAuthor() {
            return author;
        }

        public Timestamp getTime() {
            return time;
        }

        public int getParentId() {
            return parentId;
        }
        
    }
    static final Logger logger = Logger.getLogger(MessageManager.class.getName());
        
    private static final String TABLE_NAME = "messages";
    
    private static final String ID_COLUMN_NAME = "id";
    private static final String TEXT_COLUMN_NAME = "text";    
    private static final String AUTHOR_COLUMN_NAME = "author";
    private static final String DATE_COLUMN_NAME = "date";
    private static final String PARENT_ID_COLUMN_NAME = "topic_id";
    
    private static final String SELECT_BY_PARENT_ID_QUERY =
            "SELECT * FROM " + TABLE_NAME + " WHERE " + PARENT_ID_COLUMN_NAME + "=? ORDER BY " + DATE_COLUMN_NAME;
    private static final String DELETE_MESSAGE_BY_ID_QUERY = 
            "DELETE FROM " + TABLE_NAME + " WHERE " + ID_COLUMN_NAME + "=?";
    private static final String INSERT_MESSAGE_QUERY = 
            "INSERT INTO " + TABLE_NAME + " (" + TEXT_COLUMN_NAME + ", " + AUTHOR_COLUMN_NAME + ", " + DATE_COLUMN_NAME + ", " + 
                         PARENT_ID_COLUMN_NAME + ") VALUES (?, ?, ?, ?)";
    
    public boolean deleteMessage(int id) {
        Connection connection = ConnectionManager.getConnection();
        if (connection == null) {
            logger.log(Level.SEVERE, "Couln't establish a connection to database");
            return false;
        }        
        try {
            PreparedStatement ps = connection.prepareStatement(DELETE_MESSAGE_BY_ID_QUERY);
            ps.setInt(1, id);           
            return ps.execute();            
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
            return false;
        }
    }
    public boolean addMessage(String text, String author, int parentId) {
        Connection connection = ConnectionManager.getConnection();
        if (connection == null) {
            logger.log(Level.SEVERE, "Couln't establish a connection to database");
            return false;
        }        
        try {
            PreparedStatement ps = connection.prepareStatement(INSERT_MESSAGE_QUERY);
            ps.setString(1, text);
            ps.setString(2, author);
            ps.setTimestamp(3, new Timestamp(new java.util.Date().getTime()));
            ps.setInt(4, parentId);           
            return ps.execute();            
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
            return false;
        }
    }
    public List<Message> getAllChildrenMessages(int parentId) {
        List<Message> list = new LinkedList<>();
        Connection connection = ConnectionManager.getConnection();
        if (connection == null) {
            logger.log(Level.SEVERE, "Couln't establish a connection to database");
            return list;
        }        
        try {
            PreparedStatement ps = connection.prepareStatement(SELECT_BY_PARENT_ID_QUERY); 
            ps.setInt(1, parentId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Message message = new Message(
                        rs.getInt(ID_COLUMN_NAME),
                        rs.getString(TEXT_COLUMN_NAME),
                        rs.getString(AUTHOR_COLUMN_NAME),
                        rs.getTimestamp(DATE_COLUMN_NAME),
                        rs.getInt(PARENT_ID_COLUMN_NAME)
                );
                list.add(message);
            }
        } catch (SQLException ex) {                       
            logger.log(Level.SEVERE, null, ex);
            return list;
        }
        return list;
    }
}
