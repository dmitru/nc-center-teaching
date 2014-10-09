/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.example.utils;

import com.example.servlets.AddMessageServlet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dmitry
 */
public class ConnectionManager {
    static Logger logger = Logger.getLogger(ConnectionManager.class.getName());
    
    static final String DB_NAME = "guestbook";
    static final String DB_LOGIN = "root";
    static final String DB_PASSWORD = "";
    
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }
    
    static public Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost/" + DB_NAME + "?"
                    + "user=" + DB_LOGIN + "&password=" + DB_PASSWORD);
            return connection;
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
