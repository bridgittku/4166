<%@page import="edu.uncc.nbad.Connection"%>
<%@page import="edu.uncc.nbad.User"%>
<%@page contentType="text/html" pageEncoding="utf-8"%>
<%@include file = "header.jsp" %>
<%@include file = "navigation.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <main>
            <div id="savedEvents">
                <h2>My Saved Connections</h2>
                <table style="width:100%">
                    <tr id="tableTitles">
                        <th>Connection</th>
                        <th>Type</th>
                        <th>Status</th>
                        <th></th>
                    </tr>
                    <c:forEach var="userConnection" items="${userProfile.userConnectionList}">
                    <tr>
                        <th><c:out value='${userConnection.connectionName}'/></th>
                        <th><c:out value='${userConnection.connectionType}'/></th>
                        <th><c:out value='${userConnection.rsvp}'/></th>
                    <form action="" method="post">
                        <input type="hidden" name="viewConnections" value="${userConnection.connectionID}"/>
                    </form>
                    <th><form action="/FinalProject/ProfileControllerServlet" method="post">
                            <input type="hidden" name="action" value="updateProfile"/>
                            <input type="hidden" name="viewConnections" value=${userConnection.connectionID}>
                            <input type="hidden" name="connectionID" value=${userConnection.connectionID}>
                            <input type="submit" value="Update" class="action"/>
                        </form>
                        <form action="/FinalProject/ProfileControllerServlet" method="post">
                            <input type="hidden" name="action" value="delete"/>
                            <input type="hidden" name="viewConnections" value=${userConnection.connectionID}>
                            <input type="hidden" name="connectionID" value=${userConnection.connectionID}>
                            <input type="submit" value="Delete" class="action"/>
                        </form>
                    </tr>
                    </c:forEach>
                </table>
            </div>
            <div id="savedEvents">
                <h2>My Created Connections</h2>
                <table style="width:100%">
                    <tr id="tableTitles">
                        <th>Connection</th>
                        <th></th>
                    </tr>
                    <c:forEach var="connection" items="${collection}">
                        <tr>           
                        <c:if test="${connection.userID == theUser.userID}">
                            <th><c:out value='${connection.connectionName}'/></th>
                            <form action="" method="post">
                                <input type="hidden" name="viewConnections" value="${connection.connectionID}"/>
                            </form>
                            <th><form action="/FinalProject/FormControllerServlet" method="post">
                                <input type="hidden" name="task" value="editConnection"/>
                                <input type="hidden" name="viewConnections" value=${connection.connectionID}>
                                <input type="hidden" name="connectionID" value=${connection.connectionID}>
                                <input type="submit" value="Edit" class="action"/>
                            </form>
                            <form action="/FinalProject/ProfileControllerServlet" method="post">
                                <input type="hidden" name="action" value="remove"/>
                                <input type="hidden" name="viewConnections" value=${connection.connectionID}>
                                <input type="hidden" name="connectionID" value=${connection.connectionID}>
                                <input type="submit" value="Remove" class="action"/>
                            </form>
                        </c:if>
                    </tr>
                    </c:forEach>
                </table>
            </div>
        </main>
<%@include file = "footer.jsp"%>