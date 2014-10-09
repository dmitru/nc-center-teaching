<%-- 
    Document   : MainPage
    Created on : Oct 9, 2014, 1:27:02 PM
    Author     : Dmitry
--%>

<%@page import="com.mysql.jdbc.StringUtils"%>
<%@page import="com.example.model.Message"%>
<%@page import="java.util.List"%>
<%@page import="com.example.model.MessageManager"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>GuestBook</title>
        <style>
            div.wrapper {
                width: 500px;
                background-color: lightgreen;
                padding: 20px;
                margin: 20px;
            }
            
            div.message {
                background-color: lightcyan;
                margin: 10px;
                padding: 10px;
            }
            
            a.delete {
                color: red;
                font-weight: bold;
            }
        </style>
    </head>
    <body>
        <div class="wrapper">
            <h3>Leave new message</h3>
            <form action="AddMessageServlet" method="POST">
                Message:
                <br/>
                <textarea name="content"></textarea>
                <br/>
                author: <br/>
                <input type="text" name="author"/>
                <br/>
                <input type="submit"/>
            </form>

            <br/>
            <h3>List of messages:</h3>
            <%
            List<Message> messages = MessageManager.getAllMessages();
            for (Message message : messages) {
                %>
                <div class="message">
                    <div>
                        <%= message.getContent() %>
                    </div>
                    <div>
                        <i><%= message.getAuthor() %></i>
                    </div>
                    <div>
                        <a class="delete" href="DeleteMessageServlet?id=<%=message.getId()%>">Delete</a>
                    </div>
                </div>
                <%
            }
            %>
        </div>
    </body>
</html>
