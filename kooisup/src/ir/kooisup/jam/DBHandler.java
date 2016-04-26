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
        MongoClient client = new MongoClient("localhost", 27017);
        db = client.getDB("mydb");
        users = db.getCollection("users");
        
    //    users.createIndex(new BasicDBObject("email", 1).append("unique", true));
	}
	
	
	/*public static void main(final String[] args) {
		getInstance();
		User u = new User("username5", "pass", "email5","gender", "country","uui");
			try {
				//getInstance().insertUser(u);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
	       // if(existConfirmedUser("username5", "pass")) System.out.println("YES");
	       // else System.out.println("NO");
	        
	        if(confirmEmail("email5", "uui")) System.out.println("YEES");
	        else System.out.println("NOO");
	        
	        	
	}*/
	public void insertUser(User u) throws Exception
	{
		
        BasicDBObject doc = new BasicDBObject("_id", u.getUsername())
                .append("password", u.getPassword())
                .append("email",u.getEmail())
                .append("gender", u.getGender())
                .append("country", u.getCountry())
                .append("confirmationCode", u.getConfirmationCode())
                .append("mailConfirmed", u.isMailConfirmed());
      
        try {
        	users.insert(doc);
        } catch(MongoException ex) {
        	//ex.printStackTrace();
        	System.out.println("duplicattte username");
        	//throw exception
        	throw new Exception("code2");
        }
        
	}
	
	public static boolean confirmEmail(String email, String confirmationCode){
		BasicDBObject query =new BasicDBObject("email", email)
									.append("confirmationCode", confirmationCode);
							
		DBCursor cursor = users.find(query);
	    try {
	          if (cursor.hasNext()) {
	        	  System.out.println(cursor.next());
	        	  DBObject c = cursor.curr();
	        	  BasicDBObject set = new BasicDBObject("$set", new BasicDBObject("mailConfirmed", true));
	    		  users.update(c, set);
	    		  return true;
	        	}
	          	
	      } finally {
	          cursor.close();
	      }
	      return false;
	}
	private static boolean found(BasicDBObject query) {
		DBCursor cursor = users.find(query);
		try {
          if(cursor.hasNext()) {
        	  System.out.println(cursor.next());
        	  return true;
        	 }
          } finally {
              cursor.close();
          }
		return false;
	}
	boolean isUniqueUsername(String username){	
			return !found(new BasicDBObject("_id", username));
	}
	boolean isUniqueEmail(String email){
		return !found(new BasicDBObject("email", email));
	}
	boolean existConfirmedUser(String username, String password) {
		return found(new BasicDBObject("_id", username).
				append("password", password).
				append("mailConfirmed", true));	
	}
}