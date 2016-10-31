/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.dundee.computing.aec.instagrim.models;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import uk.ac.dundee.computing.aec.instagrim.lib.AeSimpleSHA1;
import uk.ac.dundee.computing.aec.instagrim.stores.Pic;

/**
 *
 * @author Administrator
 */
public class User {
    Cluster cluster;
    public User(){
        
    }
    
    public boolean RegisterUser(String username, String Password, String firstname, String lastname, String email, String address, String City, String Postcode){
        AeSimpleSHA1 sha1handler=  new AeSimpleSHA1();
        String EncodedPassword=null;
        try {
            EncodedPassword= sha1handler.SHA1(Password);
        }catch (UnsupportedEncodingException | NoSuchAlgorithmException et){
            System.out.println("Can't check your password");
            return false;
        }
        Session session = cluster.connect("instagrim");
        PreparedStatement ps = session.prepare("insert into userprofiles (login,password,first_name,last_name,email) Values(?,?,?,?,?)");
        //PreparedStatement psAddress = session.prepare("insert into address (street,city,zip) Value(?,?,?) WHERE ");
       
        BoundStatement boundStatement = new BoundStatement(ps);
        session.execute( // this is where the query is executed
                boundStatement.bind( // here you are binding the 'boundStatement'
                        username,EncodedPassword,firstname,lastname,email));
        //We are assuming this always works.  Also a transaction would be good here !
        
        //BoundStatement boundStatementAddress = new BoundStatement(psAddress);
        //session.execute( // this is where the query is executed
               // boundStatement.bind( // here you are binding the 'boundStatement'
                    //    address,City,Postcode));
        return true;
    }
    
    public boolean IsValidUser(String username, String Password){
        AeSimpleSHA1 sha1handler=  new AeSimpleSHA1();
        String EncodedPassword=null;
        try {
            EncodedPassword= sha1handler.SHA1(Password);
        }catch (UnsupportedEncodingException | NoSuchAlgorithmException et){
            System.out.println("Can't check your password");
            return false;
        }
        Session session = cluster.connect("instagrim");
        PreparedStatement ps = session.prepare("select password from userprofiles where login =?");
        ResultSet rs = null;
        BoundStatement boundStatement = new BoundStatement(ps);
        rs = session.execute( // this is where the query is executed
                boundStatement.bind( // here you are binding the 'boundStatement'
                        username));
        if (rs.isExhausted()) {
            System.out.println("No Images returned");
            return false;
        } else {
            for (Row row : rs) {
               
                String StoredPass = row.getString("password");
                if (StoredPass.compareTo(EncodedPassword) == 0)
                    return true;
            }
        }
   
        
    
        return false;  
        }
    
    
       public void setCluster(Cluster cluster) {
        this.cluster = cluster;
         }
       
       
          public String getFirstName(String user) {
            String name = null;
            Session session = cluster.connect("instagrim");
            PreparedStatement ps = session.prepare("select first_name from userprofiles where login =?");
            ResultSet rs = null;
            BoundStatement boundStatement = new BoundStatement(ps);
            rs = session.execute(boundStatement.bind(user));
            if (rs.isExhausted()) {
                System.out.println("No first name");
                return null;
            } else {
                for (Row row : rs) {
                    String nameFirst = row.getString("first_name");      
                    name = nameFirst;
                }
            }
            return name;
          }
          
           public String getLastName(String user) {
            String name = null;
            Session session = cluster.connect("instagrim");
            PreparedStatement ps = session.prepare("select last_name from userprofiles where login =?");
            ResultSet rs = null;
            BoundStatement boundStatement = new BoundStatement(ps);
            rs = session.execute(boundStatement.bind(user));
            if (rs.isExhausted()) {
                System.out.println("No last name");
                return null;
            } else {
                for (Row row : rs) {
                    String nameLast = row.getString("last_name");      
                    name = nameLast;
                }
            }
            return name;
          }
           
            public String getEmail(String user) {
            String email = null;
            Session session = cluster.connect("instagrim");
            PreparedStatement ps = session.prepare("select email from userprofiles where login =?");
            ResultSet rs = null;
            BoundStatement boundStatement = new BoundStatement(ps);
            rs = session.execute(boundStatement.bind(user));
            if (rs.isExhausted()) {
                System.out.println("No last name");
                return null;
            } else {
                for (Row row : rs) {
                    String emailRow = row.getString("email");      
                    email = emailRow;
                }
            }
            return email;
          }
            
        public UUID getProfile(String username) {
            
            Session session = cluster.connect("instagrim");
            PreparedStatement ps1 = session.prepare("select profile_pic from userprofiles where login =?");
            BoundStatement boundStatement1 = new BoundStatement(ps1);
            ResultSet rs1 = null;
            rs1 = session.execute( // this is where the query is executed
            boundStatement1.bind( // here you are binding the 'boundStatement'
                            username));

            UUID profileID = null;


            for (Row row : rs1) {
                  profileID = row.getUUID("profile_pic");
            }
            
            return profileID;
        } 
    
}
