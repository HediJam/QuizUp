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
import com.mongodb.MongoException;
import com.mongodb.ParallelScanOptions;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.Arrays;

import org.bson.Document;
import org.bson.conversions.Bson;
 
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;





public class DBHandler {
	public static DBHandler instance = null;
	static DB db;
	static DBCollection users;
	static DBCollection quizs;
	static DBCollection questions;
	static DBCollection categories;
	
	private static int lastQzID=0;
	private static int lastQsID=0;
	
	
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
        db.getCollection("users").drop();
        db.getCollection("quizs").drop();
        db.getCollection("questions").drop();
        db.getCollection("categories").drop();
        
        users = db.getCollection("users");
        quizs = db.getCollection("quizs");
        questions = db.getCollection("questions");
        categories = db.getCollection("categories");
        users.createIndex(new BasicDBObject("email", 1).append("unique", true));
	}
	
	public static void main(final String[] args) {
		ArrayList<String> choices =  new ArrayList<String>(Arrays.asList("4","3","2","1"));
		Question qs1 = new Question(0, "1+2=?", "math", choices);
		Question qs2 = new Question(1, "2+2=?", "math", choices);
		Question qs3 = new Question(2, "3+2=?", "math", choices);
		Question qs4 = new Question(3, "4+2=?", "phys", choices);
		
		getInstance().insertQuestion(qs1);
		getInstance().insertQuestion(qs2);
		getInstance().insertQuestion(qs3);
		getInstance().insertQuestion(qs4);
		
		//System.out.println(getInstance().findQuestions("math"));
		System.out.println(getInstance().findQuestion(qs1.getQsID()));
		
		int id1 = getInstance().createQuiz("math").getQzId();
		System.out.println(getInstance().findQuiz(id1));
		
		getInstance().insertCategory("math");
		getInstance().insertCategory("phys");
		System.out.println(getInstance().findCategories());
		System.out.println(getInstance().findCategory("math"));
		
		
		/*
		int id2 = getInstance().createQuiz("physics").getQzId();
		Quiz qz2 = getInstance().findQuiz(id2);
		System.out.println(qz2.getCategory());
		*/
		
		
		/*
		System.out.println("here");
		ArrayList<Question> arr = new ArrayList<Question>();
		System.out.println(arr);
		*/
		
		User u = new User("ÛŒÛŒÙˆØ²Ø±Ù†ÛŒÙ…", "Ù¾Ø³ÙˆØ±Ø¯", "Ø§ÛŒÙ…ÛŒÙ„","gender", "country","Ú©Ø¯");
			try {
				getInstance().insertUser(u);
				//db.getCollection("users").drop();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
	        if(getInstance().existConfirmedUser("ÛŒÛŒÙˆØ²Ø±Ù†ÛŒÙ…", "Ù¾Ø³ÙˆØ±Ø¯")) System.out.println("YES");
	        else System.out.println("NO");
	        
	        if(confirmEmail("Ø§ÛŒÙ…ÛŒÙ„", "Ú©Ø¯")) System.out.println("YEES");
	        else System.out.println("NOO");
	        
	        	
	}
	
	//USER METHOS
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
	
	private boolean existUser(BasicDBObject query) {
		if(findOne(query, users) == null) return false;
		return true;
	}
	
	private DBObject findOne(BasicDBObject query, DBCollection coll) {
		DBCursor curs = coll.find(query);
		try {
			if (curs.hasNext()) return curs.next();
		
		} finally {
			curs.close();
		}
		return null;
	}
	
	boolean isUniqueUsername(String username){	
			return !existUser(new BasicDBObject("_id", username));
	}
	
	boolean isUniqueEmail(String email){
		return !existUser(new BasicDBObject("email", email));
	}
	
	boolean existConfirmedUser(String username, String password) {
		return existUser(new BasicDBObject("_id", username).
				append("password", password).
				append("mailConfirmed", true));	
	}
	
	//QUIZ METHODS
	public Quiz createQuiz(String category) {
		ArrayList<Question> qssInQz = findQuestions(category);
		ArrayList<Integer> qsIDs = new ArrayList<Integer>();
		for(int i=0; i<qssInQz.size(); i++) 
			qsIDs.add(qssInQz.get(i).getQsID());
		Quiz quiz = new Quiz(category, lastQzID++, qsIDs);
		insertQuiz(quiz);
		return quiz;
	}
	
	public ArrayList<Question> loadQuestions(Quiz quiz) {
		ArrayList<Integer> qsIDs = quiz.getQsIDs();
		ArrayList<Question> qssInQz = new ArrayList<Question>();
		for(int i=0; i<qsIDs.size(); i++)
			qssInQz.add(findQuestion(qsIDs.get(i)));
		return qssInQz;
	}
	

	public Quiz findQuiz(int qzID) {
		DBObject qz = findOne(new BasicDBObject("_id", qzID), quizs);
		if(qz==null)
		{
			System.out.println("Invalid Quiz ID => not found so return null");
			return null;
		}
		return new Quiz( ((Integer) qz.get("_id")).intValue() ,((Integer) qz.get("score1")).intValue(), ((Integer) qz.get("score2")).intValue(), 
				((Integer) qz.get("finishTmie1")).intValue(), ((Integer) qz.get("finishTime2")).intValue(), 
				(String)qz.get("category"), (String)qz.get("uid1"), (String)qz.get("uid2"), 
				(ArrayList<Integer>) qz.get("qsIDs"));
	}
	
	private void insertQuiz(Quiz q) //throws Exception
	{
		
        BasicDBObject doc = new BasicDBObject("_id", q.getQzId())
                .append("score1", q.getScore1())
                .append("score2", q.getScore2())
                .append("finishTime1", q.getFinishTime1())
                .append("finishTime2", q.getFinishTime2())
                .append("category", q.getCategory())
                .append("uid1", q.getUid1())
        		.append("uid1", q.getUid2())
        		.append("qsIDs", q.getQsIDs());
        
        try {
        	quizs.insert(doc);
        } catch(MongoException ex) {
        	System.out.println("duplicattte quiz id");
        }
	}
	
	//QUESTION METHODS
	public Question insertQuestion(String text, String category, ArrayList<String> choices) {
		Question qs = new Question(lastQsID++, text, category, choices);
		insertQuestion(qs);
		return qs;
	}
	
	private void insertQuestion(Question qs)
	{
        BasicDBObject doc = new BasicDBObject("_id", qs.getQsID())
                .append("text", qs.getText())
                .append("category", qs.getCategory())
                .append("choices", qs.getChoices());
        try {
        	questions.insert(doc);
        } catch(MongoException ex) {
        	System.out.println("duplicattte question id");
        }
	}
	
	public Question findQuestion(int qsID) {
		DBObject qs = findOne(new BasicDBObject("_id", qsID), questions);
		if(qs==null) 
		{
			System.out.println("invalid question ID => return null");
			return null;
		}
		return new Question(((Integer) qs.get("_id")).intValue(), (String)qs.get("text"), (String)qs.get("category"), 
				(ArrayList<String>) qs.get("choices"));
	}
	
	public ArrayList<Question> findQuestions(String category) {
		ArrayList<Question> questionsArr= new ArrayList<Question>();
		BasicDBObject query =new BasicDBObject("category", category);

		DBCursor curs = questions.find(query);
		try {
			while (curs.hasNext()) {
			DBObject qs = curs.next() ;
			System.out.println(qs);
			questionsArr.add(new Question( ((Integer) qs.get("_id")).intValue(), (String)qs.get("text"), (String)qs.get("category"), 
					(ArrayList<String>) qs.get("choices")));
		}
		
		} finally {
			curs.close();
		}
		return questionsArr;
	}
	
	//CATEGORY METHOD
	public void insertCategory (String category) {
		BasicDBObject doc = new BasicDBObject("_id", category);
		
        try {
        	categories.insert(doc);
        } catch(MongoException ex) {
        	System.out.println("duplicattte category");
        }
	}
	
	public ArrayList<String> findCategories() {
		ArrayList<String> cats = new ArrayList<String>();
		DBCursor curs = categories.find();
		
		try {
			while (curs.hasNext()) {
			DBObject qs = curs.next() ;
			System.out.println(qs);
			cats.add((String) qs.get("_id"));
		}
		
		} finally {
			curs.close();
		}
		return cats;

	}
	
	public String findCategory(String category) {
		DBObject cat = findOne(new BasicDBObject("_id", category), categories);
		if(cat==null)
		{
			System.out.println("Invalid Category ID => not found so return null");
			return null;
		}
		return category;
	}
}
