<%@page contentType="text/html" pageEncoding="utf-8" session="false"%>
<%@include file = "header.jsp" %>
<%@include file = "navigation.jsp" %>
    <main>
        <h2 id="newConnectionTitle"></h2>
        <form action = "/FinalProject/FormControllerServlet" name="newConnection" method="post" class="newConnectionForm">
           <label class="labels">Topic:</label>  
               <input type="radio" name="connectionTopic" class="topicChoices" value="Dining"/><span class="topicLabels">Dining</span> 
               <input type="radio" name="connectionTopic" class="topicChoices" value="Cooking"><span class="topicLabels">Cooking</span> <br />
           <label class="labels">Connection Name:</label>  
               <input type="text" name="connectionName"/>  <br />
           <label class="labels">Details:</label>  
               <input type="text" name="details"/>  <br />
           <label class="labels">Where:</label>  
               <input type="text" name="location"/>  <br />
           <label class="labels">When:</label>  
               <input type="text" name="dateTime"/>  <br />
           <input type="submit" name="task" value="submit" id="submitButton"/>
        </form>
    </main>
<%@include file = "footer.jsp" %>