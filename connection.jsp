<%@page contentType="text/html" pageEncoding="utf-8"%>
<%@include file = "header.jsp" %>
<%@include file = "navigation.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <main>
            <div id="cookingclass">
                <h2><c:out value = '${connection.connectionName}'/></h2>
                <img src="user.jpeg" alt="user">
                <p><br>Host: <c:out value = '${connection.hostName}'/></p>
                <p><br>Date and Time: <c:out value = '${connection.dateTime}'/></p>
                <p><br>Location: <c:out value = '${connection.location}'/></p>
                <p><br>Category: <c:out value = '${connection.connectionTopic}'/></p>
                <p><br> Details: <c:out value = '${connection.details}'/>
                  </p>
                <form action="/FinalProject/ProfileControllerServlet" method="post">
                    <input type="hidden" name="action" value="save">
                    <input type="hidden" name="rsvp" value="yes">
                    <input type="hidden" name="viewConnections" value=${connection.connectionID}>
                    <input type="hidden" name="connectionID" value=${connection.connectionID}>
                    <input type="submit" value="Yes" class="action">
                </form>
                <form action="/FinalProject/ProfileControllerServlet" method="post">
                    <input type="hidden" name="action" value="save">
                    <input type="hidden" name="rsvp" value="no">
                    <input type="hidden" name="viewConnections" value=${connection.connectionID}>
                    <input type="hidden" name="connectionID" value=${connection.connectionID}>
                    <input type="submit" value="No" class="action" id="no">
                </form>
                <form action="/FinalProject/ProfileControllerServlet" method="post">
                    <input type="hidden" name="action" value="save">
                    <input type="hidden" name="rsvp" value="maybe">
                    <input type="hidden" name="viewConnections" value=${connection.connectionID}>
                    <input type="hidden" name="connectionID" value=${connection.connectionID}>
                    <input type="submit" value="Maybe" class="action" id="maybe">
                </form>
            </div>
        </main>
<%@include file = "footer.jsp" %>