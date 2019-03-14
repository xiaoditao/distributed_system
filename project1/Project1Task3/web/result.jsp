<%-- 
    Document   : result
    Created on : Feb 7, 2019, 11:07:50 AM
    Author     : xiaoditao
--%>
<!--Set the doc type to make it adjust to mobile phones-->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!--Set all the elements in jsp files, including buttons, text fields-->
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>task3result</title>
    </head>
    <body> 
        <h1>Distributed Systems Class Clicker</h1>
         <br>
         <% if (!request.getAttribute("answerA").equals("0") || !request.getAttribute("answerB").equals("0") || !request.getAttribute("answerC").equals("0") || !request.getAttribute("answerD").equals("0")) {%>
         <p> The results from the survey are as follows: </p>
         <% } %>
         <br>
         <% if (!request.getAttribute("answerA").equals("0")) {%>
         <p> A: <%=request.getAttribute("answerA")%> </p>
         <% } %>
         <% if (!request.getAttribute("answerB").equals("0")) {%>
         <p> B: <%=request.getAttribute("answerB")%> </p>
         <% } %>
         <% if (!request.getAttribute("answerC").equals("0")) {%>
         <p> C: <%=request.getAttribute("answerC")%> </p>
         <% } %>
         <% if (!request.getAttribute("answerD").equals("0")) {%>
         <p> D: <%=request.getAttribute("answerD")%> </p>
         <% } %>
         <% if (request.getAttribute("answerA").equals("0") && request.getAttribute("answerB").equals("0") && request.getAttribute("answerC").equals("0") && request.getAttribute("answerD").equals("0")) {%>
         <p> There are currently no results. </p>
         <% } %>
         <% if (!request.getAttribute("answerA").equals("0") || !request.getAttribute("answerB").equals("0") || !request.getAttribute("answerC").equals("0") || !request.getAttribute("answerD").equals("0")) {%>
         <p>These results have now been cleared.</p>
         <% } %>
    </body>
    
</html>
