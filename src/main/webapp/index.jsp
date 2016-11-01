<%-- 
    Document   : index
    Created on : Sep 28, 2014, 7:01:44 PM
    Author     : Administrator
--%>


<%@page import="uk.ac.dundee.computing.aec.instagrim.models.PicModel"%>
<%@page import="uk.ac.dundee.computing.aec.instagrim.lib.CassandraHosts"%>
<%@page import="com.datastax.driver.core.Cluster"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="uk.ac.dundee.computing.aec.instagrim.stores.*" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Instagrim</title>
        <link rel="stylesheet" type="text/css" href="Styles.css" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
           </ul>
        </div>
         <%}
         
                Cluster cluster = CassandraHosts.getCluster();
                PicModel pm= new PicModel();
                pm.setCluster(cluster);
                Pic p = pm.getRandomPic();

                if(p!=null){
         %>      
        
        
         
         <img src="/Instagrim/Image/<%=p.getSUUID()%>" style =" min-height: 100%;min-width: 1024px;width: 100%;height: auto; 
              position: fixed; top: 0; left: 0;z-index: -1;filter: blur(3px)">
        
         <%}%>
         <div class="IntroTextWrapper">
             <div class="IntroText">
                 <p>INSTAGRIM<br><div style="font-size: 35px">Your world in Black and White</div>
             </div>
         </div>
    </body>
</html>
