<%-- 
    Document   : question
    Created on : Feb 2, 2019, 10:33:24 PM
    Author     : xiaoditao
--%>
<!--Set the doc type to make it adjust to mobile phones-->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!--Set all the elements in jsp files, including buttons, text fields-->
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>task3</title>
    </head>
    <body>
        <form action="aaa" method="POST">
            <h1> Distributed Systems Class Clicker </h1>
            <br>
            <p> Submit your answer to the current question: </p>
            <br>
            <input type="radio" name="choice" value="A" /> A
            <br>
            <input type="radio" name="choice" value="B" /> B
            <br>
            <input type="radio" name="choice" value="C" /> C
            <br>
            <input type="radio" name="choice" value="D" /> D
            <br><br>
            <input type="submit" value="submit" />
        </form>
    </body>
</html>
