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
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author egor
 */
public class TopicManager {
    static public final class Topic {
        private final int id;
        private final String name;
        private final int groupId;

        public Topic(int id, String name, int groupId) {
            this.id = id;
            this.name = name;
            this.groupId = groupId;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getGroupId() {
            return groupId;
        }        
    }
    
    static final Logger logger = Logger.getLogger(TopicManager.class.getName());
        
    private static final String TABLE_NAME = "topics";
    
    private static final String ID_COLUMN_NAME = "id";
    private static final String NAME_COLUMN_NAME = "name";
    private static final String PARENT_ID_COLUMN_NAME = "group_id";
    
    private static final String SELECT_BY_PARENT_ID_QUERY =
            "SELECT * FROM " + TABLE_NAME + " WHERE " + PARENT_ID_COLUMN_NAME + "=? ORDER BY " + NAME_COLUMN_NAME;
    private static final String SELECT_BY_ID_QUERY = 
            "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_COLUMN_NAME + "=?";
    private static final String DELETE_TOPIC_BY_ID_QUERY = 
            "DELETE FROM " + TABLE_NAME + " WHERE " + ID_COLUMN_NAME + "=?";
    private static final String INSERT_TOPIC_QUERY = 
            "INSERT INTO " + TABLE_NAME + " (" + NAME_COLUMN_NAME + ", " + 
                         PARENT_ID_COLUMN_NAME + ") VALUES (?, ?)";
    
    public boolean deleteTopic(int id) {
        Connection connection = ConnectionManager.getConnection();
        if (connection == null) {
            logger.log(Level.SEVERE, "Couln't establish a connection to database");
            return false;
        }
        MessageManager messageManager = new MessageManager();
        List<MessageManager.Message> messageList = messageManager.getAllChildrenMessages(id);        
        for (MessageManager.Message message : messageList) {
            messageManager.deleteMessage(message.getId());
        }
        try {
            PreparedStatement ps = connection.prepareStatement(DELETE_TOPIC_BY_ID_QUERY);
            ps.setInt(1, id);           
            return ps.execute();            
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
            return false;
        }
    }
    /**
     * Tries to add new topic. If failes, returns false 
     * @param name
     * @param parentGroupId
     * @return 
     */
    public boolean addTopic(String name, int parentGroupId) {
        Connection connection = ConnectionManager.getConnection();
        if (connection == null) {
            logger.log(Level.SEVERE, "Couln't establish a connection to database");
            return false;
        }        
        try {
            PreparedStatement ps = connection.prepareStatement(INSERT_TOPIC_QUERY);
            ps.setString(1, name);
            ps.setInt(2, parentGroupId);
            return ps.execute();            
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
            return false;
        }
    }
    public Topic getTopic(int Id) {
        Connection connection = ConnectionManager.getConnection();
        if (connection == null) {
            logger.log(Level.SEVERE, "Couln't establish a connection to database");
            return null;
        }        
        try {
            PreparedStatement ps = connection.prepareStatement(SELECT_BY_ID_QUERY);
            ps.setInt(1, Id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Topic(
                        rs.getInt(ID_COLUMN_NAME),
                        rs.getString(NAME_COLUMN_NAME),
                        rs.getInt(PARENT_ID_COLUMN_NAME));
            }
            else {
                return null;
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
            return null;
        }
    }
    public List<Topic> getAllChildrenTopics(int parentId) {
        List<Topic> list = new LinkedList<>();
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
                Topic topic = new Topic(rs.getInt(ID_COLUMN_NAME), rs.getString(NAME_COLUMN_NAME), rs.getInt(PARENT_ID_COLUMN_NAME));
                list.add(topic);
            }
        } catch (SQLException ex) {                       
            logger.log(Level.SEVERE, null, ex);
            return list;
        }
        return list;
    }
}
