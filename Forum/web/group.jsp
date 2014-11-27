<%-- 
    Document   : group
    Created on : 12.10.2014, 15:43:20
    Author     : egor
--%>

<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="egor.forum.modules.GroupManager"%>
<%@page import="egor.forum.modules.TopicManager"%>
<!DOCTYPE html>
<%!
private final int groupHomeId = 1;
%>

<%
GroupManager groupManager = new GroupManager();
//Get id of current group
int groupId;
try { 
    groupId = Integer.parseInt(request.getParameter("id"));    
    if (groupManager.checkExistance(groupId) == false || groupId == 0) { //Tried to get unexisting page, should get home page
        groupId = groupHomeId;
    }  
} catch (NumberFormatException e) {
    groupId = groupHomeId;
}
//Get information about current group    
    GroupManager.Group currentGroup = groupManager.getGroup(groupId);
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>
            <%=currentGroup.getName()%>
        </title>
        <link rel="stylesheet" href="group.css">
    </head>
    
    <body>
        <div class="navigation">
            <li>
                <ul>
                    <a href="group.jsp?id=1">Home</a>
                </ul>
                <ul>
                    <a href="group.jsp?id=<%=String.valueOf(currentGroup.getParentGroupId())%>">Up</a>
                </ul>
            </li>
        </div>
        <div class="group_creation_form">
            <form action="AddGroupServlet" method="POST">
                Create new group with name !!!:
                <br/>
                <textarea name="name"></textarea>           
                <br/>
                <input type="hidden" name="id" value="<%=String.valueOf(groupId)%>">                
                <input type="submit"/>
            </form>                
        </div>
        <div class="groups">
            <li>Groups :)
                <%
                List<GroupManager.Group> groupList = groupManager.getAllChildrenGroup(groupId);
                for (GroupManager.Group group : groupList) {%>
                    <ul>
                        <a href="group.jsp?id=<%=group.getId()%>"><%=group.getName()%></a>
                        <br/>
                        <a href="DeleteGroupServlet?id=<%=currentGroup.getId()%>&victim_id=<%=group.getId()%>">delete</a>
                    </ul>
                <%
                }
                %>
            </li>
        </div>
        <div class="topic_creation_form">
            <form action="AddTopicServlet" method="POST">
                Create new topic with name:
                <br/>
                <textarea name="name"></textarea>           
                <br/>
                <input type="hidden" name="id" value="<%=String.valueOf(groupId)%>">                
                <input type="submit"/>
            </form>                
        </div>
        <div class="topics">            
            <li>Topics
                <%
                List<TopicManager.Topic> topicList = new TopicManager().getAllChildrenTopics(groupId);
                for (TopicManager.Topic topic : topicList) {%>
                    <ul>
                        <a href="topic.jsp?id=<%=topic.getId()%>"><%=topic.getName()%></a>
                        <br/>
                        <a href="DeleteTopicServlet?id=<%=currentGroup.getId()%>&victim_id=<%=topic.getId()%>">delete</a>
                    </ul>
                <%
                }
                %>
            </li>
        </div>
    </body>
</html>
