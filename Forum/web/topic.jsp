<%-- 
    Document   : topic
    Created on : 12.10.2014, 20:43:20
    Author     : egor
--%>

<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="egor.forum.modules.MessageManager"%>
<%@page import="egor.forum.modules.TopicManager"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%
        TopicManager topicManager = new TopicManager();   
        int topicId = 0;
        TopicManager.Topic currentTopic = null;
        try { 
            topicId = Integer.parseInt(request.getParameter("id"));    
            currentTopic = topicManager.getTopic(topicId);
        } catch (NumberFormatException e) {
        %>
            <meta http-equiv="refresh" content="0; url=group.jsp">
        <%    
        }
        %>
        <title>
            <%=currentTopic.getName()%>
        </title>
        <link rel="stylesheet" href="topic.css">
    </head>
    
    <body>
        <div class="navigation">
            <li>
                <ul>
                    <a href="group.jsp?id=1">Home</a>
                </ul>
                <ul>
                    <a href="group.jsp?id=<%=currentTopic.getGroupId()%>">Up</a>
                </ul>
            </li>
        </div>

        <div class="messages">
            <li>Messages
                <%
                List<MessageManager.Message> messageList = new MessageManager().getAllChildrenMessages(topicId);
                for (MessageManager.Message message : messageList) {%>
                    <ul>
                        <%=message.getAuthor()%>
                        <br/>
                        <%=message.getText()%>
                        <br/>
                        <%=message.getTime().toString()%>
                        <br/>
                        <a href="DeleteMessageServlet?id=<%=currentTopic.getId()%>&victim_id=<%=message.getId()%>">delete</a>
                    </ul>
                <%
                }
                %>
            </li>
        </div>
        <div class="message_creation_form">
            <form action="AddMessageServlet" method="POST">
                Create new message
                <br/>
                Author
                <textarea name="author"></textarea>
                <br/>
                Your text
                <textarea name="text"></textarea>
                <input type="hidden" name="id" value="<%=String.valueOf(topicId)%>">                
                <input type="submit"/>
            </form>                
        </div>
    </body>
</html>
