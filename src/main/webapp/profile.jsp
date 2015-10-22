<%-- 
    Document   : profile
    Created on : 15-Oct-2015, 20:54:36
    Author     : Think
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="uk.ac.dundee.computing.aec.instagrim.stores.*" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Instagrim</title>
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
                <div class="col-xs-12">
                    <h1>InstaGrim ! </h1>
                    <h2>Ya Profile Fam!! Safe!!!!!</h2>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-10">
                    <ul>
                        <li><label>Username: </label>${username}</li>
                        <li><label>First Name: </label>${firstName}</li>
                        <li><label>Second Name: </label>${secondName}</li>
                        <li><label>Email: </label>${email}</li>
                        <li><label>Street: </label>${street}</li>
                        <li><label>City: </label>${city}</li>
                        <li><label>Postcode: </label>${postCode}</li>
                    </ul>
                </div>
                <div class="col-xs-2">
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
                            <li><a href="register.jsp">Register</a></li>
                            <li><a href="login.jsp">Login</a></li>
                                <%
                                    }%>
                        </ul>
                    </nav>
                </div>
            </div>
            <footer>
                <ul>
                    <li class="footer"><a href="/Instagrim">Home</a></li>
                    <li>&COPY; Andy C</li>
                </ul>
            </footer>
        </div>
    </body>
</html>
