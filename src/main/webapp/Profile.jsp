<%-- 
    Document   : Profile
    Created on : 20-Oct-2016, 18:40:17
    Author     : Declan
--%>

<%@page import="java.util.Iterator"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="uk.ac.dundee.computing.aec.instagrim.stores.*" %>
<!DOCTYPE html>
<html>
     <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Instagrim</title>
        <link rel="stylesheet" type="text/css" href="../Styles.css" />

    </head>
   <body>
        <div class="wrapper">
            <ul class="topNav" id="topNav">  
                <li class="left"><a href="/Instagrim"> InstaGrim </a></li>
                <li class="left"><a href="/Instagrim/Images/main"> Explore </a></li>
                     <% LoggedIn lg = (LoggedIn) session.getAttribute("LoggedIn"); 
                               if ((lg != null) && lg.getLoggedIn()) {
                                   String UserName = lg.getUsername();
                           %>

                 <li class="dropdown-right">
                    
                     <a href="#" class="dropbtn"><img  <%if(lg.getProfile() == null){%> src="../Icons/default.png" <%}else{%> src="/Instagrim/Thumb/<%=lg.getProfile()%>" <%}%>style="width:50px;height:50px;border:5px"></a> 
                     
                     
                         <div class="dropdown-content">
                             <a href="/Instagrim/Images/<%=lg.getUsername()%>">Your Images</a>
                             <a href="/Instagrim/upload.jsp">Upload</a>  
                             <a href="/Instagrim/Profile/<%=lg.getUsername()%>">Profile</a>
                             <form method="POST" action="Logout"><input type="submit" value="Logout"></form>
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
                            
                <div class="innerBox">
                    <div class="UpperProfile">
                        <div class="ProfilePicWrapperLeft">
                            <%
                               // java.util.LinkedList<Pic> lsPics = (java.util.LinkedList<Pic>) request.getAttribute("Pics");
                               if(lg.getProfile() == null){

                    %>
                    <img src="../Icons/default.png" style="width:350px;height:350px;border:5px; border-radius: 8px;">
                    <p>No profile picture yet, add one!</p>

                    <% }else{%>

                        
                  
                      
                       
                    
                    <a href="/Instagrim/Image/<%=lg.getProfile()%>" ><img alt="profile pic"
                    accesskey=""src="/Instagrim/Thumb/<%=lg.getProfile()%>" style="width:350px;height:350px;border:5px;border-radius: 8px;"></a><br/>
                    <%}%>  
                    <h3>File Upload</h3>
                    <form method="POST" enctype="multipart/form-data" action="ProfilePicUpload">
                        File to upload: <input type="file" name="upfile"><br/>

                        <br/>
                        <input type="submit" value="Upload File">
                    </form> 
                    </div>
                    
                    <div class="ProfilePicWrapperRight">
                        <h1 style="font:bold"> <%=lg.getUsername()%></h1>
                    Firstname :<%=lg.getFirstName()%> <br>
                    Lastname : <%=lg.getLastName()%>  <br>
                    Email :  <%=lg.getEmail()%> <br> 
                    </div>
                        
                    </div>
                    
                    
                    <div class="LowerProfile">
                    <br><br><hr>
                    <h1>Your Pics</h1>
                    <br>

                    <%
                        java.util.LinkedList<Pic> lsPics = (java.util.LinkedList<Pic>) request.getAttribute("Pics");
                        if (lsPics == null) {
                    %>
                    <p>No Pictures found</p>
                    <%
                    } else {
                        Iterator<Pic> iterator;
                        iterator = lsPics.iterator();
                        while (iterator.hasNext()) {
                                Pic p = (Pic) iterator.next();

                            %>

                            <div class="imageWrapper">
                            <a href="/Instagrim/Comments/<%=p.getSUUID()%>"><img src="/Instagrim/Thumb/<%=p.getSUUID()%>" width="300" height="200" style=" border-radius: 8px;"></a>
                            </div>
                        <%

                            }
                            }
                        %>


                    </div>
                        
                 </div>                                        
        </div>
        
    </body>
</html>
