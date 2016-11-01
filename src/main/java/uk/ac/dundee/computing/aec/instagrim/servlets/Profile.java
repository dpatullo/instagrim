/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.dundee.computing.aec.instagrim.servlets;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
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
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import uk.ac.dundee.computing.aec.instagrim.lib.CassandraHosts;
import uk.ac.dundee.computing.aec.instagrim.lib.Convertors;
import uk.ac.dundee.computing.aec.instagrim.models.PicModel;
import uk.ac.dundee.computing.aec.instagrim.stores.LoggedIn;
import uk.ac.dundee.computing.aec.instagrim.stores.Pic;
import uk.ac.dundee.computing.aec.instagrim.stores.*;


/**
 *
 * @author Declan
 */

@WebServlet(urlPatterns = {
    "/Profile",
    "/Profile/*"
})
@MultipartConfig

public class Profile extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private Cluster cluster;
    private HashMap CommandsMap = new HashMap();
    
    

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Profile() {
        super();
        // TODO Auto-generated constructor stub
            CommandsMap.put("Profile", 2);
           // CommandsMap.put("Profile", 2);
          //  CommandsMap.put("Profile", 3);

    }

    public void init(ServletConfig config) throws ServletException {
        try {
            // TODO Auto-generated method stub
            cluster = CassandraHosts.getCluster();
        } catch (IOException ex) {
            Logger.getLogger(Profile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
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
                DisplayImage(Convertors.DISPLAY_PROCESSED,args[2], response);
                break;
            case 2:
                DisplayImageList(args[2], request, response);
                break;
            case 3:
                DisplayImage(Convertors.DISPLAY_THUMB,args[2],  response);
                break;
            default:
                error("Bad Operator", response);
        }
    }

    private void DisplayImageList(String User, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PicModel tm = new PicModel();
        tm.setCluster(cluster);
        java.util.LinkedList<Pic> lsPics = tm.getPicsForUser(User);
        RequestDispatcher rd = request.getRequestDispatcher("/Profile.jsp");
        request.setAttribute("Pics", lsPics);
        
        HttpSession session=request.getSession();
        LoggedIn lg= (LoggedIn)session.getAttribute("LoggedIn");
        UUID profilePicUUID = null;
                              if ((lg != null) && lg.getLoggedIn()) {
                                  profilePicUUID = lg.getProfile();
                              }
        Pic profpic = tm.getPic(0, profilePicUUID);
        request.setAttribute("ProfilePic", profpic);
        
        rd.forward(request, response);

    }

    private void DisplayImage(int type,String Image, HttpServletResponse response) throws ServletException, IOException {
        PicModel tm = new PicModel();
        tm.setCluster(cluster);
  
        
        Pic p = tm.getPic(type,java.util.UUID.fromString(Image));
        
        OutputStream out = response.getOutputStream();

        response.setContentType(p.getType());
        response.setContentLength(p.getLength());
        //out.write(Image);
        InputStream is = new ByteArrayInputStream(p.getBytes());
        BufferedInputStream input = new BufferedInputStream(is);
        byte[] buffer = new byte[8192];
        for (int length = 0; (length = input.read(buffer)) > 0;) {
            out.write(buffer, 0, length);
        }
        out.close();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
        for (Part part : request.getParts()) {
            System.out.println("Part Name " + part.getName());

            String type = part.getContentType();
            String filename = part.getSubmittedFileName();
            
            
            InputStream is = request.getPart(part.getName()).getInputStream();
            int i = is.available();
            HttpSession session=request.getSession();
            Session sessionInsta = cluster.connect("instagrim");
            LoggedIn lg= (LoggedIn)session.getAttribute("LoggedIn");
            String username="majed";
            if (lg.getLoggedIn()){
                username=lg.getUsername();
            }
            if (i > 0) {
                byte[] b = new byte[i + 1];
                is.read(b);
                System.out.println("Length : " + b.length);
                PicModel tm = new PicModel();
                tm.setCluster(cluster);
                tm.insertProfilePic(b, type, filename, username);
                
                PreparedStatement ps1 = sessionInsta.prepare("select profile_pic from userprofiles where login =?");
            BoundStatement boundStatement1 = new BoundStatement(ps1);
            ResultSet rs1 = null;
            rs1 = sessionInsta.execute( // this is where the query is executed
            boundStatement1.bind( // here you are binding the 'boundStatement'
                            username));
        
           UUID profileID = null;
           
      
            for (Row row : rs1) {
                  profileID = row.getUUID("profile_pic");
            }
            
            if(profileID == null)
            {
                     ResultSet rs2 = null;
                    PreparedStatement ps2 = sessionInsta.prepare("select picid from Pics where user =? AND title =? ALLOW FILTERING");
                    BoundStatement boundStatement2 = new BoundStatement(ps2);
                    rs2 = sessionInsta.execute( // this is where the query is executed
                    boundStatement2.bind( // here you are binding the 'boundStatement'
                            "majed","default.png"));
                    
                    for (Row row2 : rs2) {
                    profileID = row2.getUUID("picid");
                    }
            }
                lg.setProfile(profileID);
                //request.setAttribute("LoggedIn", lg);

                is.close();
            }
            
            PicModel tm = new PicModel();
            tm.setCluster(cluster);
            java.util.LinkedList<Pic> lsPics = tm.getPicsForUser(username);
            request.setAttribute("Pics", lsPics);
            
            RequestDispatcher rd = request.getRequestDispatcher("/Profile.jsp");
             rd.forward(request, response);
        }

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