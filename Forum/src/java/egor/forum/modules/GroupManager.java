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
public class GroupManager {
    static final Logger logger = Logger.getLogger(GroupManager.class.getName());
    
    static public final class Group {
        private final int id;
        private final String name;      
        private final int parentGroupId;

        public Group(int id, String name, int parentId) {
            this.id = id;
            this.name = name;
            this.parentGroupId = parentId;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getParentGroupId() {
            return parentGroupId;
        }        
    }
    
    private static final String TABLE_NAME = "groups";
    
    private static final String ID_COLUMN_NAME = "id";
    private static final String NAME_COLUMN_NAME = "name";
    private static final String PARENT_ID_COLUMN_NAME = "parent_id";
    
    private static final String SELECT_BY_ID_QUERY = 
            "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_COLUMN_NAME + "=?";
    private static final String SELECT_BY_PARENT_ID_QUERY =
            "SELECT * FROM " + TABLE_NAME + " WHERE " + PARENT_ID_COLUMN_NAME + "=? ORDER BY " + NAME_COLUMN_NAME;
    private static final String DELETE_GROUP_BY_ID_QUERY = 
            "DELETE FROM " + TABLE_NAME + " WHERE " + ID_COLUMN_NAME + "=?";
    private static final String INSERT_GROUP_QUERY = 
            "INSERT INTO " + TABLE_NAME + " (" + NAME_COLUMN_NAME + ", " + 
                         PARENT_ID_COLUMN_NAME + ") VALUES (?, ?)";
    
    public boolean deleteGroup(int groupId) {
        Connection connection = ConnectionManager.getConnection();
        if (connection == null) {
            logger.log(Level.SEVERE, "Couln't establish a connection to database");
            return false;
        }
        TopicManager topicManager = new TopicManager();
        List<TopicManager.Topic> topicList = topicManager.getAllChildrenTopics(groupId);
        List<Group> groupList = getAllChildrenGroup(groupId);
        for (TopicManager.Topic topic : topicList) {
            topicManager.deleteTopic(topic.getId());
        }
        for (Group group : groupList) {
            deleteGroup(group.getId());
        }
        try {
            PreparedStatement ps = connection.prepareStatement(DELETE_GROUP_BY_ID_QUERY);
            ps.setInt(1, groupId);
            return ps.execute();            
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
            return false;
        }
    }
    /**
     * Tries to add new group. If failes, returns false 
     * @param name
     * @param parentGroupId
     * @return 
     */
    public boolean addGroup(String name, int parentGroupId) {
        Connection connection = ConnectionManager.getConnection();
        if (connection == null) {
            logger.log(Level.SEVERE, "Couln't establish a connection to database");
            return false;
        }        
        try {
            PreparedStatement ps = connection.prepareStatement(INSERT_GROUP_QUERY);
            ps.setString(1, name);
            ps.setInt(2, parentGroupId);
            return ps.execute();            
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
            return false;
        }
    }
    /**
     * Can return null if group wasn't found
     * @param Id
     * @return 
     */
    public Group getGroup(int Id) {
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
                return new Group(rs.getInt(ID_COLUMN_NAME), rs.getString(NAME_COLUMN_NAME), rs.getInt(PARENT_ID_COLUMN_NAME));
            }
            else {
                return null;
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
            return null;
        }
    }
    /**
     * Check whether such group exists in table or not
     * @param groupId
     * @return 
     */
    public boolean checkExistance(int groupId) {
        Connection connection = ConnectionManager.getConnection();
        if (connection == null) {
            logger.log(Level.SEVERE, "Couln't establish a connection to database");
            return false;
        }
        
        try {
            PreparedStatement ps = connection.prepareStatement(SELECT_BY_ID_QUERY); 
            ps.setInt(1, groupId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException ex) {                       
            logger.log(Level.SEVERE, null, ex);
            return false;
        }
    }
    public List<Group> getAllChildrenGroup(int parentId) {
        List<Group> list = new LinkedList<>();
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
                Group group = new Group(rs.getInt(ID_COLUMN_NAME), rs.getString(NAME_COLUMN_NAME), rs.getInt(PARENT_ID_COLUMN_NAME));
                list.add(group);
            }
        } catch (SQLException ex) {                       
            logger.log(Level.SEVERE, null, ex);
            return list;
        }
        return list;
    }
}
