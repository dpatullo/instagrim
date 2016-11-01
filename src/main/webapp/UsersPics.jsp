<%-- 
    Document   : UsersPics
    Created on : Sep 24, 2014, 2:52:48 PM
    Author     : Administrator
--%>

<%@page import="com.datastax.driver.core.Cluster"%>
<%@page import="uk.ac.dundee.computing.aec.instagrim.lib.CassandraHosts"%>
<%@page import="uk.ac.dundee.computing.aec.instagrim.models.PicModel"%>
<%@page import="java.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="uk.ac.dundee.computing.aec.instagrim.stores.*" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Instagrim</title>
        <link rel="stylesheet" type="text/css" href="/Instagrim/Styles.css" />
    </head>
    <body>
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
           
        
            <h1>Your Pics</h1>

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
                      

                    <a href="/Instagrim/Comments/<%=p.getSUUID()%>"><img src="/Instagrim/Thumb/<%=p.getSUUID()%>" width="300" height="200"></a>
              
                <%

                    }
                    }
                %>
            

        <footer>  
        </footer>
    </body>
</html>
