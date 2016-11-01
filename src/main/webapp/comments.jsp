<%-- 
    Document   : Image
    Created on : 27-Oct-2016, 02:44:21
    Author     : Declan
--%>
<%@page import="uk.ac.dundee.computing.aec.instagrim.models.User"%>
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
               <li class="right"><a href="../upload.jsp">Upload</a></li>

          </ul>
        <div class ="innerBox">
            <% 
                LoggedIn lg = (LoggedIn) session.getAttribute("LoggedIn"); 
                Cluster cluster = CassandraHosts.getCluster();
                PicModel pm= new PicModel();
                pm.setCluster(cluster);
               String ID = (String) request.getAttribute("ID");
               LinkedList<Comment> Comments = (LinkedList<Comment>) request.getAttribute("Comments");
            %>
            <a href="/Instagrim/Image/<%=ID%>"><img src="/Instagrim/Thumb/<%=ID%>" width="300" height="200"></a>
            
            <% if(Comments == null){
              %>
              <p>No comments yet, add one!</p>
              <%
            }else{Iterator<Comment> iterator;
                iterator = Comments.iterator();
                while (iterator.hasNext()) {
                        Comment c = (Comment) iterator.next();
                        UUID userThumb = null;
                        userThumb = pm.getUserProfile(c.getUser());
                   
            %>
            <div>
                <%if(userThumb == null)
                {
                    
                %>
                <img src="../Icons/default.png" width="40" height="40">
                <%}else{
                String comID = userThumb.toString();
                %>
                <a href="/Instagrim/Profile/<%=c.getUser()%>"><img src="/Instagrim/Thumb/<%=comID%>" width="40" height="40"></a>
                <%}%>
                
                  <div class="dialogbox" style="display: inline-block">
                    <div class="body">
                      <span class="tip tip-left"></span>
                      <div class="message">
                        <span> <%=c.getContents()%></span>
                      </div>
                      <span><%=c.getUser()%></span>
                    </div>
                  </div>
                
               
              
                on  <%=c.getDate()%>  
            </div>
            <%}}%>
            
            <% if(!lg.getLoggedIn()){%>
                Please log in to leave a comment!
            <%}else{%>
            <form method="POST"  action="Comments">
            <div>
		<textarea rows="10" name="comment" id="comment" maxlength="100" placeholder="Comment"></textarea>
                 <div style="display: none"><input type="text"  id="ID" name="ID" value="<%=ID%>"></div>
            </div>
            <div>
            	<input type="submit" name="submit" value="Add Comment">
            </div>
            </form>
            <%}%>
        </div>
    </body>
</html>
