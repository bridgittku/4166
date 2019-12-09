<%@page contentType="text/html" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header id="header">
    <div class="topnav">
        <a href="index.jsp" id="logo">Friends Who Food</a>
        <nav id="topmenu">
            <ul>
                <c:choose>
                    <c:when test="${theUser != null}">
                        <li><a href="newConnection.jsp">Start a new connection</a></li>
                        <li><a href="/FinalProject/ProfileControllerServlet?action=view">My Connections</a></li>
                        <li><a href="/FinalProject/ProfileControllerServlet?action=signout">Logout</a></li> 
                    </c:when>
                    <c:otherwise>
                        <li><a href="newConnection.jsp">Start a new connection</a></li>
                        <li><a href="/FinalProject/ProfileControllerServlet?action=login">My Connections</a></li>
                        <li><a href="/FinalProject/ProfileControllerServlet?action=login">Login</a></li>
                        <li><a href="/FinalProject/ProfileControllerServlet?action=register">Register</a></li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </nav>
    </div>
    <div class="generalnav">
        <nav id="generalmenu">
            <ul>
                <li><a href="/FinalProject/NavigationServlet?page=index">Home</a></li>
                <li><a href="/FinalProject/NavigationServlet?page=about">About</a></li>
                <li><a href="/FinalProject/ConnectionControllerServlet?connectionID=">Connections</a></li>
                <li><a href="/FinalProject/NavigationServlet?page=contact">Contact Us</a></li>
            </ul>
        </nav>
    </div>
</header>
