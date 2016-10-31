<%-- 
    Document   : register.jsp
    Created on : Sep 28, 2014, 6:29:51 PM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Instagrim</title>
        <link rel="stylesheet" type="text/css" href="Styles.css" />
    </head>
    <body>
        <ul class="topNav" id="topNav">  
               <li class="left"><a href="/Instagrim"> InstaGrim </a></li>
               <li class="right"><a href="login.jsp">Login</a></li>
               <li class="right">Already got an account?</li>
          </ul>
        
       
        <div class ="RegBox">
            <div class ="RegPanel">
                
            <h3>Register as user</h3>
           <hr>
                <div style="float: left; margin-left: 10px;">
                                

                   <br><br> Username <br><br>
                    Password <br><br>
                    First Name <br><br>
                    Last Name <br><br>
                    Email Address <br><br>
                    First line of Address <br><br>
                    City <br><br>
                    Post Code <br><br>
                </div>
                <div style="float: left;">
                    <form method="POST"  action="Register"><br><br>
                    <input type="text" name="username"><br><br>
                    <input type="password" name="password"><br><br>
                    <input type="text" name="firstname"><br><br>
                    <input type="text" name="lastname"><br><br>
                    <input type="text" name="email"><br><br>
                    <input type="text" name="street"><br><br>              
                    <input type="text" name="city"><br><br>
                    <input type="text" name="postcode"><br><br>
                    <input type="submit" value="Register"> <br><br>
                                </form>

                </div>
                
                <br/>
                
            </div>
        </div>

        <footer>
        </footer>
    </body>
</html>
