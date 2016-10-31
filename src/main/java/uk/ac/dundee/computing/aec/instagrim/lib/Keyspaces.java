package uk.ac.dundee.computing.aec.instagrim.lib;

import java.util.ArrayList;
import java.util.List;

import com.datastax.driver.core.*;
import com.sun.javafx.iio.ImageLoader;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import uk.ac.dundee.computing.aec.instagrim.models.PicModel;
import uk.ac.dundee.computing.aec.instagrim.stores.LoggedIn;
import java.net.URL;
import org.apache.commons.io.IOUtils;

public final class Keyspaces {

    public Keyspaces() {

    }

    public static void SetUpKeySpaces(Cluster c) {
        try {
            //Add some keyspaces here
            //String dropKeyspace = "DROP keyspace instagrim";
            String createkeyspace = "create keyspace if not exists instagrim  WITH replication = {'class':'SimpleStrategy', 'replication_factor':1}";
            String CreatePicTable = "CREATE TABLE if not exists instagrim.Pics ("
                    + " user varchar,"
                    + " picid uuid, "
                    + " interaction_time timestamp,"
                    + " title varchar,"
                    + " image blob,"
                    + " thumb blob,"
                    + " processed blob,"
                    + " imagelength int,"
                    + " thumblength int,"
                    + "  processedlength int,"
                    + " type  varchar,"
                    + " name  varchar,"
                    + " PRIMARY KEY (picid)"
                    + ")";
            String Createuserpiclist = "CREATE TABLE if not exists instagrim.userpiclist (\n"
                    + "picid uuid,\n"
                    + "user varchar,\n"
                    + "pic_added timestamp,\n"
                    + "PRIMARY KEY (user,pic_added)\n"
                    + ") WITH CLUSTERING ORDER BY (pic_added desc);";
            String CreateAddressType = "CREATE TYPE if not exists instagrim.address (\n"
                    + "street text,\n"
                    + "city text,\n"
                    + "zip text\n"
                    + ");";
            String CreateUserProfile = "CREATE TABLE if not exists instagrim.userprofiles (\n"
                    + "login text PRIMARY KEY,\n"
                    + "password text,\n"
                    + "first_name text,\n"
                    + "last_name text,\n"
                    + "email text,\n"
                    + "addresses  map<text, frozen <address>>,\n"
                    + "profile_pic uuid\n"
                    + ");";
             String CreateComments = "CREATE TABLE if not exists instagrim.comments (\n"
                    + "picid uuid,\n"
                    + "content text,\n"
                    + "user varchar,\n"
                    + "commentID uuid,\n"
                    + "time timestamp,\n"
                    + "PRIMARY KEY (commentID, time)\n"
                    + ") WITH CLUSTERING ORDER BY (time desc);";
            Session session = c.connect();
            try {
                
                
                
                
                
               /*
                PreparedStatement statementDROP = session
                        .prepare(dropKeyspace);
                BoundStatement boundStatementDROP = new BoundStatement(
                        statementDROP);
                ResultSet rsDROP = session
                        .execute(boundStatementDROP);
             */
               
               
               
               
                PreparedStatement statement = session
                        .prepare(createkeyspace);
                BoundStatement boundStatement = new BoundStatement(
                        statement);
                ResultSet rs = session
                        .execute(boundStatement);
                System.out.println("created instagrim ");
            } catch (Exception et) {
                System.out.println("Can't create instagrim " + et);
            }

            //now add some column families 
            System.out.println("" + CreatePicTable);

            try {
                SimpleStatement cqlQuery = new SimpleStatement(CreatePicTable);
                session.execute(cqlQuery);
            } catch (Exception et) {
                System.out.println("Can't create tweet table " + et);
            }
            System.out.println("" + Createuserpiclist);

            try {
                SimpleStatement cqlQuery = new SimpleStatement(CreateComments);
                session.execute(cqlQuery);
            } catch (Exception et) {
                System.out.println("Can't create comments table " + et);
            }
            System.out.println("" + CreateComments);
            
            try {
                SimpleStatement cqlQuery = new SimpleStatement(Createuserpiclist);
                session.execute(cqlQuery);
            } catch (Exception et) {
                System.out.println("Can't create user pic list table " + et);
            }
            System.out.println("" + CreateAddressType);
            try {
                SimpleStatement cqlQuery = new SimpleStatement(CreateAddressType);
                session.execute(cqlQuery);
            } catch (Exception et) {
                System.out.println("Can't create Address type " + et);
            }
            System.out.println("" + CreateUserProfile);
            try {
                SimpleStatement cqlQuery = new SimpleStatement(CreateUserProfile);
                session.execute(cqlQuery);
            } catch (Exception et) {
                System.out.println("Can't create Address Profile " + et);
            }
            session.close();

        } catch (Exception et) {
            System.out.println("Other keyspace or coulm definition error" + et);
        }

    }
    
    /*
     public static void addDefault(Cluster c) throws FileNotFoundException, IOException {
          
            URL Url = new URL("http://localhost:8080/Instagrim/Icons/default.png");
            BufferedImage buff = ImageIO.read(Url);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write( buff, "png", baos );


           
            byte[] fileContent = baos.toByteArray();
            String type = "+URLDecoder.decode(args[argv],/png";
            String filename = "default.png";
            
            String username="majed";

                System.out.println("Length : " + fileContent.length);
                PicModel tm = new PicModel();
                tm.setCluster(c);
                tm.insertPic(fileContent, type, filename, username);

            
     }
     */
}
