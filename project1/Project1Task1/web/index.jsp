<%-- 
    Document   : welcome
    Created on : Feb 2, 2019, 6:11:14 PM
    Author     : xiaoditao
--%>

<!--Set the doc type to make it adjust to mobile phones-->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%= request.getAttribute("doctype") %>
<!--Set all the elements in jsp files, including buttons, text fields-->
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>convert string</title>
    </head>
    <body>
        <p><b>Give me a String, choose from MD5 or SHA-256 and I'll give you the hash value.</b></p>
        <form action="ComputeHashes" method="GET">
            <label for="letter">Type the string.</label>
            <input type="text" name="convertString" value="" /><br>
            <br/>
            <input type="radio" name="convertMethod" value="MD5" checked/> MD5
            <input type="radio" name="convertMethod" value="SHA-256" /> SHA-256
            <br/><br/>
            <input type="submit" value="Click Here" />
        </form>
    </body>
</html>

