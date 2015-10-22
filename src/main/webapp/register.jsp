<%-- 
    Document   : register.jsp
    Created on : Sep 28, 2014, 6:29:51 PM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
        <header>
        <h1>InstaGrim ! </h1>
        <h2>Your world in Black and White</h2>
        </header>
        <nav>
            <ul>
                
                <li><a href="/Instagrim/Images/majed">Sample Images</a></li>
            </ul>
        </nav>
       
        <article>
            <h3>Register as user</h3>
            <form method="POST"  action="Register">
                <ul>
                    <li>First Name <input type="text" name="firstname"></li>
                    <li>Second Name <input type="text" name="secondname"></li>
                    <li>User Name <input type="text" name="username"></li>
                    <li>Email <input type="text" name="email"></li>
                    <li>Password <input type="password" name="password"></li>
                    <li>Retype Password <input type="password" name="rePassword"></li>
                    
                    <li>Street <input type="text" name="street"></li>
                    <li>Postcode <input type="text" name="postCode"></li>
                    <li>City <input type="text" name="city"></li>
                </ul>
                <br/>
                <input type="submit" value="Regidter"> 
            </form>

        </article>
        <footer>
            <ul>
                <li class="footer"><a href="/Instagrim">Home</a></li>
            </ul>
        </footer>
    </body>
</html>
