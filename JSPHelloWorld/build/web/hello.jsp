<%-- 
    Document   : hello
    Created on : Oct 9, 2014, 1:10:02 PM
    Author     : Dmitry
--%>

<%@page import="com.example.MyClass"%>
<%@page import="java.util.Date"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title> my page </title>
    </head>
    <body>
        <% String text = "Hello world!"; %>
        <h1><%=text%></h1>
        <h2>Today is: <%=new Date()%>
        <% if (Math.random() > 0.5) { %>
            <h3>You're a loser!</h3>
        <% } else { %>
            <h3>You're cool!</h3>
        <% } %>
        
        Calling a Java method: 
        <h3><%= new MyClass().myMethod() %></h3>
    </body>
</html>
