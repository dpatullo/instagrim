/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.dundee.computing.aec.instagrim.servlets;

import com.datastax.driver.core.Cluster;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import uk.ac.dundee.computing.aec.instagrim.lib.CassandraHosts;
import uk.ac.dundee.computing.aec.instagrim.lib.Convertors;
import uk.ac.dundee.computing.aec.instagrim.models.PicModel;
import uk.ac.dundee.computing.aec.instagrim.models.User;
import uk.ac.dundee.computing.aec.instagrim.stores.Comment;
import uk.ac.dundee.computing.aec.instagrim.stores.LoggedIn;
import uk.ac.dundee.computing.aec.instagrim.stores.Pic;

/**
 *
 * @author Declan
 */

@WebServlet(urlPatterns = {
    "/Comments/*"
            
})
public class Comments extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private Cluster cluster;
    private HashMap CommandsMap = new HashMap();
    
    

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Comments() {
        super();
        // TODO Auto-generated constructor stub
        CommandsMap.put("Comments", 1);
    }

    public void init(ServletConfig config) throws ServletException {
        try {
            // TODO Auto-generated method stub
            cluster = CassandraHosts.getCluster();
        } catch (IOException ex) {
            Logger.getLogger(Comments.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        
        HttpSession session=request.getSession();
        String ID = (String) request.getParameter("ID");
         
        String args[] = Convertors.SplitRequestPath(request);
        int command;
        try {
            command = (Integer) CommandsMap.get(args[1]);
        } catch (Exception et) {
            error("Bad Operator", response);
            return;
        }
        switch (command) {
            case 1:  
                if((args[2] == null) || (args[2].equals("Comments")))
                        {
                             DisplayImageForComments(ID,request,response);
                        }
                DisplayImageForComments(args[2],request,response);
                break;
            default:
                error("Bad Operator", response);
        }
    }

     
     private void DisplayImageForComments(String image, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PicModel tm = new PicModel();
        tm.setCluster(cluster);
        //Pic pic = tm.getPic(0,UUID.fromString(image));
        java.util.LinkedList<Comment> comments = tm.getComments(image);
        RequestDispatcher rd = request.getRequestDispatcher("/comments.jsp");
        request.setAttribute("Comments", comments);
        request.setAttribute("ID", image);
        rd.forward(request, response);

    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
         String args[] = Convertors.SplitRequestPath(request);
         HttpSession session=request.getSession();
         System.out.println("Session in servlet "+session);
         LoggedIn lg=(LoggedIn) session.getAttribute("LoggedIn");
         String contents = (String) request.getParameter("comment");
         String ID = (String) request.getParameter("ID");
         String user = lg.getUsername();
         
         
         PicModel tm = new PicModel();
         tm.setCluster(cluster);
         tm.insertComment(contents, UUID.fromString(ID), user);

        //Pic pic = tm.getPic(0,UUID.fromString(image));
        java.util.LinkedList<Comment> comments = tm.getComments(ID);
        RequestDispatcher rd = request.getRequestDispatcher("/comments.jsp");
        request.setAttribute("Comments", comments);
        request.setAttribute("ID", ID);
        rd.forward(request, response);
        

    }

    private void error(String mess, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out = null;
        out = new PrintWriter(response.getOutputStream());
        out.println("<h1>You have a na error in your input</h1>");
        out.println("<h2>" + mess + "</h2>");
        out.close();
        return;
    }
}