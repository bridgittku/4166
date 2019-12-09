<%-- 
    Document   : login
    Created on : Nov 19, 2019, 7:35:21 PM
    Author     : bridgittku
--%>
<%@page contentType="text/html" pageEncoding="utf-8" session="false"%>
<%@include file = "header.jsp" %>
<%@include file = "navigation.jsp" %>
<main>
        <h1 id="newUserTitle">Welcome to Friends Who Food!</h1>
        <h2 id="newUserSubTitle">Please enter your username and password to sign in.</h2>
        <form action="/FinalProject/ProfileControllerServlet" name ="userLogin" method="post" class="newLoginForm">
            <label class="labels">Username:</label>
                <input type="text" name="userName" required="required"/> <br/>
            <label class="labels">Password:</label>
                <input type="text" name="password" required="required"/> <br/>
            <input type="submit" name="task" value="Login" id="submitButton"/>
                <input type="hidden" name="action" value="signIn">
        </form>
</main>
<%@include file = "footer.jsp" %>