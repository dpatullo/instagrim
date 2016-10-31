/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.dundee.computing.aec.instagrim.stores;

import static com.datastax.driver.core.DataType.timestamp;
import java.util.Date;
import java.util.UUID;

/**
 *
 * @author Declan
 */
public class Comment {
    private String contents=null;
    private String user=null;
    private UUID userID=null;
    private UUID PicID = null;
    private String date = null;
    
    public void setContents(String contents)
    {
        this.contents = contents;
    }
    
    public String getContents()
    {
        return contents;
    }
    
    public void setPicID(UUID ID){
        this.PicID = ID;
    }
    
    public UUID getPicID()
    {
        return PicID;
    }
    
    public void setDate(String date)
    {
        this.date = date;
    }
    
    public String getDate()
    {
        return date;
    }
    
    public void setUser(String user)
    {
        this.user = user;
    }
    
    public String getUser()
    {
        return user;
    }
    
    public void setUserID(UUID ID){
        this.userID = ID;
    }
    
    public UUID getUSerID()
    {
        return userID;
    }
}
