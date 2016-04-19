package ir.kooisup.jam;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.BulkWriteOperation;
import com.mongodb.BulkWriteResult;
import com.mongodb.Cursor;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.ParallelScanOptions;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
 
import org.bson.Document;
import org.bson.conversions.Bson;
 
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;



import com.mongodb.Mongo;
import com.mongodb.MongoException;
 

import static java.util.concurrent.TimeUnit.SECONDS;
import com.mongodb.*;


import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.BulkWriteOperation;
import com.mongodb.BulkWriteResult;
import com.mongodb.Cursor;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.ParallelScanOptions;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
 
import org.bson.Document;
import org.bson.conversions.Bson;
 
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


import com.mongodb.Mongo;
import com.mongodb.MongoException;
 

import static java.util.concurrent.TimeUnit.SECONDS;
import com.mongodb.*;

public class DBHandler {
	public static DBHandler instance = null;
	static DB db;
	static DBCollection users;
	
	public static DBHandler getInstance(){
		if (instance == null)
			instance = new DBHandler();
		return instance;
	}
	
	private DBHandler() {
		initDB();
	}
	
	static void initDB()
	{
        // connect to the local database server
		
//		MongoClient mongoClient = new MongoClient();

        /*
        // Authenticate - optional
        MongoCredential credential = MongoCredential.createMongoCRCredential(userName, database, password);
        MongoClient mongoClient = new MongoClient(new ServerAddress(), Arrays.asList(credential));
        */

       
        MongoClient client = new MongoClient("localhost", 27017);
        db = client.getDB("mydb");
        users = db.getCollection("users");
        
        users.createIndex(new BasicDBObject("email", 1).append("unique", true));
	}
	
	public static boolean confirmMail(String confirmationCode, String name, String password) {
		BasicDBObject query = new BasicDBObject("_id", name).append("password", password);
		DBCursor cursor = users.find(query);

      try {
          while (cursor.hasNext()) {
        	  System.out.println(cursor.next());
        	  DBObject c = cursor.curr();
        	  if(c.get("confirmationCode").equals(confirmationCode) &&  ((Boolean) c.get("mailConfirmed"))==false)
        	  {
        		  System.out.println("change");
        		  BasicDBObject set = new BasicDBObject("$set", new BasicDBObject("mailConfirmed", true));
        		  users.update(c, set);
        		  return true;
        	  }
        	  return false;
        	}
          //System.out.println("username of password wrong");
          //throw;
      } finally {
          cursor.close();
      }
      return false;
		//if (user.mailconfirm==false && code == user.code) return true
		
	}
	
	
	
	//mail unique
	public static void main(final String[] args) {
		initDB();
		User u = new User("eeee", "1", "FM","hgh", "Iran","jj");
		try {
			insertUser(u);
		} catch (Exception e) {
			// TODO: handle exception
		}
        if(existUser("eeee", "1")) System.out.println("YES");
        else System.out.println("NO");
        
        if(confirmMail("-", "eeee", "1")) System.out.println("YEES");
        else System.out.println("NOO");
        
        	
	}
	
	public static void insertUser(User u) throws Exception
	{
		
		System.out.println("enter");
		BasicDBObject query = new BasicDBObject("_id", u.getName()).append("password", u.getPassword());
		DBCursor cursor = users.find(query);
		try {
          if(cursor.hasNext()) {
        	  System.out.println("duplicate email");
        	  //throw;
        	  throw new Exception("code1");
        	  //return;
          }  
          } finally {
              cursor.close();
          }
          
        BasicDBObject doc = new BasicDBObject("_id", u.getName())
                .append("password", u.getPassword())
                .append("gender", u.getGender())
                .append("email",u.getEmail())
                .append("country", u.getCountry())
                .append("mailConfirmed", false);
      
        try {
        	users.insert(doc);
        } catch(MongoException ex) {
        	//ex.printStackTrace();
        	System.out.println("duplicate username");
        	//throw exception
        	throw new Exception("code2");
        }
        
	}
	
	static boolean existUser(String name, String password) {
		BasicDBObject query = new BasicDBObject("_id", name).append("password", password);
		DBCursor cursor = users.find(query);

      try {
          while (cursor.hasNext()) {
              System.out.println(cursor.next());
              return true;
          }
      } finally {
          cursor.close();
      }
      return false;
	}
	
}
