<%-- 
    Document   : editConnection
    Created on : Dec 5, 2019, 9:59:27 AM
    Author     : bridgittku
--%>
<%@page import="edu.uncc.nbad.Connection"%>
<%@page import="edu.uncc.nbad.User"%>
<%@page contentType="text/html" pageEncoding="utf-8" session="false"%>
<%@include file = "header.jsp" %>
<%@include file = "navigation.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <main>
        <h2 id="newConnectionTitle">Edit Connection</h2>
        <form action = "/FinalProject/FormControllerServlet" method="post" class="newConnectionForm">
           <input type="hidden" name="action" value="editConnection">
           <label class="labels">Topic:</label>  
               <input type="radio" name="connectionTopic" class="topicChoices" value="Dining"/><span class="topicLabels">Dining</span> 
               <input type="radio" name="connectionTopic" class="topicChoices" value="Cooking"><span class="topicLabels">Cooking</span> <br />
           <label class="labels">Connection Name:</label>  
               <input type="text" name="connectionName" value="${currentConnection.connectionName}"/>  <br />
           <label class="labels">Details:</label>  
               <input type="text" name="details" value="${currentConnection.details}"/>  <br />
           <label class="labels">Where:</label>  
               <input type="text" name="location" value="${currentConnection.location}"/>  <br />
           <label class="labels">When:</label>  
               <input type="text" name="dateTime" value="${currentConnection.dateTime}"/>  <br />
           <input type="hidden" name="connectionID" value="${currentConnection.connectionID}">
           <input type="submit" name="task" value="update" id="submitButton"/>
        </form>
    </main>
<%@include file = "footer.jsp" %>