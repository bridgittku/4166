<%@page import="edu.uncc.nbad.Connection"%>
<%@page import="java.util.ArrayList"%>
<%@include file = "header.jsp" %>
<%@include file = "navigation.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <main>
        <div id="events">
            <c:forEach var="connection" items="${collection}">
                    <c:if test="${connection.connectionTopic == 'Cooking'}">
                        <h2>Cooking</h2>
                        <a href="/FinalProject/ConnectionControllerServlet?connectionID=${connection.connectionID}" ><p><c:out value='${connection.connectionName}'/></p></a>
                    </c:if>
                    <c:if test="${connection.connectionTopic == 'Dining'}">
                        <h2>Dining</h2>
                        <a href="/FinalProject/ConnectionControllerServlet?connectionID=${connection.connectionID}" ><p><c:out value='${connection.connectionName}'/></p></a>
                    </c:if>
            </c:forEach>
        </div>
    </main>
<%@include file = "footer.jsp" %>
