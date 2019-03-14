<%-- 
    Document   : welcome
    Created on : Feb 2, 2019, 6:11:14 PM
    Author     : xiaoditao
--%>
<!--Set the doc type to make it adjust to mobile phones-->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%= request.getAttribute("doctype") %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>BUTTERFLY Page</title>
    </head>
    <body>
        <form action="butterfly" method="GET">
            <label for="letter">Species</label>
            <input type="text" name="searchedSpecies" value="" /><br>
            <p> Region: </p>
            <select name="region">
                <option  value="all">All</option>
                <option  value="45172">Barbados</option>
                <option  value="45159">Belize</option>
                <option  value="45157">Bermuda</option>
                <option  value="45170">Bonaire</option>
                <option  value="45153">Canada</option>
                <option  value="45167">Cayman_Islands</option>
                <option  value="45156">Costa_Rica</option>
                <option  value="45155">Cuba</option>
                <option  value="45161">Dominican_Republic</option>
                <option  value="45160">El_Salvador</option>
                <option  value="45169">Guatemala</option>
                <option  value="45162">Haiti</option>
                <option  value="45165">Honduras</option>
                <option  value="45171">Jamaica</option>
                <option  value="45154">Mexico</option>
                <option  value="45163">Nicaragua</option>
                <option  value="45158">Panama</option>
                <option  value="45166">Puerto_Rico</option>
                <option  value="45164">St._Kitts</option>
                <option  value="45168">The_Bahamas</option>
                <option  value="45152">United_States</option>
            </select><br>
            <p> Stage: </p>
            <input type="radio" name="stage" value="adult" /> adult
            <input type="radio" name="stage" value="egg" /> egg
            <input type="radio" name="stage" value="caterpillar" /> caterpillar
            <input type="radio" name="stage" value="pupa" /> pupa
            <input type="radio" name="stage" value="all" checked/> All
            <br><br>
            <input type="submit" value="Click Here" />
        </form>
    </body>
</html>
