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
               <li class="left"> Your world in Black and White </li>
               <li class="right"><a href="../upload.jsp">Upload</a></li>
               <li class="right"><a href="/Instagrim/Images/majed">Sample Images</a></li>


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
