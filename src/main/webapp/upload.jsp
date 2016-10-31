<%-- 
    Document   : upload
    Created on : Sep 22, 2014, 6:31:50 PM
    Author     : Administrator
--%>

<%@page import="uk.ac.dundee.computing.aec.instagrim.stores.LoggedIn"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Instagrim</title>
        <link rel="stylesheet" type="text/css" href="Styles.css" />
    </head>
    <body>
        <div class="wrapper">
           <ul class="topNav" id="topNav">  
            <li class="left"><a href="/Instagrim"> InstaGrim </a></li>
                    <% LoggedIn lg = (LoggedIn) session.getAttribute("LoggedIn"); 
                              if ((lg != null) && lg.getlogedin()) {
                                  String UserName = lg.getUsername();
                          %>
               <li class="left"> Welcome back <%=UserName%></li>
    
               
                <li class="dropdown-right">
                     <a href="#" class="dropbtn"><img  <%if(lg.getProfile() == null){%> src="./Icons/default.png" <%}else{%> src="/Instagrim/Thumb/<%=lg.getProfile()%>" <%}%>style="width:50px;height:50px;border:5px"></a> 
                        <div class="dropdown-content">
                            <a href="/Instagrim/Images/<%=lg.getUsername()%>">Your Images</a>
                            <a href="upload.jsp">Upload</a>  
                            <a href="/Instagrim/Profile/<%=lg.getUsername()%>">Profile</a>
                            <form method="POST"  action="Logout"><input type="submit" value="Logout"></form>
                        </div>
                </li>
         
           <%}else{%>   
           
                <li class="dropdown-right">
                    <a href="#" class="dropbtn">Account</a>
                        <div class="dropdown-content">
                           <a href="login.jsp">Login</a>
                           <a href="register.jsp">Register</a>
                        </div>
                         
                </li>
                <%}%>
           </ul>
        </div>
 
        <article>
            <h3>File Upload</h3>
            <form method="POST" enctype="multipart/form-data" action="Image">
                File to upload: <input type="file" name="upfile"><br/>

                <br/>
                <input type="submit" value="Press"> to upload the file!
            </form>

        </article>
        <footer>
         
        </footer>
    </body>
</html>
