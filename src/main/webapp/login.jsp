<%-- 
    Document   : login.jsp
    Created on : Sep 28, 2014, 12:04:14 PM
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
                               <li class="left"><a href="/Instagrim/Images/main"> Explore </a></li>

               <li class="right"><a href="register.jsp">Register</a></li>
           
          </ul>
       
        <div class ="loginBox">
            <div class ="loginPanel">
                <h3>Login</h3><hr>
                <br>
                <br>
                <br>
                <div style="float: left; margin-left: 60px;">Username: <br><br>Password: </div>
                <div style="float: left">
                    <form method="POST"  action="Login">
                        <input type="text" name="username"><br><br>
                        <input type="password" name="password"><br><br>
                        <input type="submit" value="Login"> 
                    </form>

                </div>
                       <%String error = (String) session.getAttribute("Error");
                    if(error!=null)
                    {%>
                    
                    <h2 style="color: red; font-weight: bold"><%=error%></h2>
                    
                            
                            <%session.setAttribute("Error", null);}%>
        </div>
        
        
        <footer>
        </footer>
    </body>
</html>
