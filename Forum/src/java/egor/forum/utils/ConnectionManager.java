package egor.forum.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionManager {
    static final Logger logger = Logger.getLogger(ConnectionManager.class.getName());
    
    static final String DB_NAME = "forum";
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
            logger.log(Level.SEVERE, null, ex);
            return null;
        }
    }
}