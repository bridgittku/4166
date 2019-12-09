<%-- 
    Document   : register
    Created on : Dec 4, 2019, 10:29:05 AM
    Author     : bridgittku
--%>

<%@page contentType="text/html" pageEncoding="utf-8" session="false"%>
<%@include file = "header.jsp" %>
<%@include file = "navigation.jsp" %>
<main>
        <h1 id="newUserTitle">Welcome to Friends Who Food!</h1>
        <h2 id="newUserSubTitle">Create an account here.</h2>
        <form action="/FinalProject/ProfileControllerServlet" name ="userRegistration" method="post" class="newLoginForm">
            <label class="labels">First Name:</label>
                <input type="text" name="firstName" required="required"/> <br/>
            <label class="labels">Last Name:</label>
                <input type="text" name="lastName" required="required"/> <br/>
            <label class="labels">Email:</label>
                <input type="text" name="email" required="required"/> <br/>
            <label class="labels">Username:</label>
                <input type="text" name="userName" required="required"/> <br/>
            <label class="labels">Password:</label>
                <input type="text" name="password" required="required"/> <br/>
            <input type="submit" name="task" value="Register" id="submitButton"/>
                <input type="hidden" name="action" value="registerUser">
        </form>
</main>
<%@include file = "footer.jsp" %>