/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Dmitry
 */
public class VisitCounterServlet extends HttpServlet {

    static Logger logger = Logger.getLogger(VisitCounterServlet.class.getName());
 
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection connection;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/visits_counter?"
                    + "user=root&password=");
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
            return;
        }
        
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<h3>Hi there! Your visit was logged.</h3><br/>"); 
            out.println("Here are the visits log:<br/>");
            addLogEntry(connection);
            printVisitsLog(getResultSet(connection), out);
        }
    }
    
    ResultSet getResultSet(Connection connect) {
        try {
            Statement statement = connect.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM visits");
            return resultSet;      
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
            return null;
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void printVisitsLog(ResultSet resultSet, PrintWriter out) {
        if (resultSet == null)
            return;
        try {
            while (resultSet.next()) {
                out.println(resultSet.getTime("date_log").toString() + "<br/>");
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }
    
    private void addLogEntry(Connection connection) {         
        Timestamp date = new Timestamp(new java.util.Date().getTime());  
        try {
            PreparedStatement ps = connection.prepareStatement("insert into visits (date_log) values (?)"); 
            logger.log(Level.INFO, "an entry was added");
            ps.setTimestamp(1, date);
            ps.execute();
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

}
