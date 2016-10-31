/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.dundee.computing.aec.instagrim.stores;

import java.util.UUID;

/**
 *
 * @author Administrator
 */
public class LoggedIn {
    boolean logedin=false;
    String Username=null;
    UUID profilePic=null;
    String first=null;
    String last=null;
    String email=null;
    
    
    public void LogedIn(){
        
    }
    
    public void setUsername(String name){
        this.Username=name;
    }
    public String getUsername(){
        return Username;
    }
    
    public void setFirstName(String name){
        this.first=name;
    }
    public String getFirstName(){
        return first;
    }
    
    public void setLastName(String name){
        this.last=name;
    }
    public String getLastName(){
        return last;
    }
    
    public void setEmail(String email){
        this.email=email;
    }
    public String getEmail(){
        return email;
    }
    
    public void setLogedin(){
        logedin=true;
    }
    public void setLogedout(){
        logedin=false;
    }
    public void setProfile(UUID profile){
        profilePic=profile;
    }
    
    public UUID getProfile()
    {
       return profilePic; 
    }
    
    public void setLoginState(boolean logedin){
        this.logedin=logedin;
    }
    public boolean getlogedin(){
        return logedin;
    }
    
    
}
