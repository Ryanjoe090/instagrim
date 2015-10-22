<%-- 
    Document   : index
    Created on : Sep 28, 2014, 7:01:44 PM
    Author     : Administrator
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="uk.ac.dundee.computing.aec.instagrim.stores.*" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Instaderp</title>
        <meta charset="utf-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css"/>
        <link rel="stylesheet" type="text/css" href="/Instagrim/StylesV.css" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <div class="container">
            <div class="row">
                <header>
                    <h1>InstaGrim ! </h1>
                    <h2>Your world in Black and White</h2>
                </header>
            </div>
            <nav>
                <ul>


                    <li><a href="upload.jsp">Upload</a></li>
                        <%
                            LoggedIn lg = (LoggedIn) session.getAttribute("LoggedIn");
                            if (lg != null) {
                                String UserName = lg.getUsername();
                                if (lg.getlogedin()) {
                        %>

                    <li><a href="/Instagrim/Images/<%=lg.getUsername()%>">Your Images</a></li>
                    <li><a href="/Instagrim/Logout">Logout</a></li>
                        <%}
                        } else {
                        %>
                    <li><a href="/Instagrim/Register">Register</a></li>
                    <li><a href="/Instagrim/Login">Login</a></li>
                        <%
                    }%>
                </ul>
            </nav>
            <footer>
                <ul>
                    <li class="footer"><a href="/Instagrim">Home</a></li>
                    <li>&COPY; Andy C</li>
                </ul>
            </footer>
        </div>
    </body>
</html>
