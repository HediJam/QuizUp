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
import java.util.Random;
import java.util.Set;
import java.util.ArrayList;
import java.util.Arrays;



public class DBHandler {
	public static DBHandler instance = null;
	private DB db;
	private DBCollection questions;
	private DBCollection quizs;
	private DBCollection categories;
	private DBCollection users;
	private DBCollection metaDatas;
	
	//private static int lastQzID=0;
	//private static int lastQsID=0;
	
	public static DBHandler getInstance(){
		if (instance == null)
			instance = new DBHandler();
		return instance;
	}
	
	private DBHandler() {
	    MongoClient client = new MongoClient("localhost", 27017);
        db = client.getDB("mydb");
     /*  db.getCollection("users").drop();
        db.getCollection("quizs").drop();
        db.getCollection("questions").drop();
        db.getColclection("categories").drop();*/
        
        users = db.getCollection("users");
        quizs = db.getCollection("quizs");
        questions = db.getCollection("questions");
        categories = db.getCollection("categories");
        metaDatas = db.getCollection("metaDatas");
        users.createIndex(new BasicDBObject("email", 1).append("unique", true));
	
	}
	
	public void basicInit() {
		ArrayList<String> choices =  new ArrayList<String>(Arrays.asList("none","5","4","3"));
		getInstance().insertCategory("math");
		Question qs1 = new Question(0, "1+2=?", "math", "3", choices);
		Question qs2 = new Question(1, "2+2=?", "math", "4", choices);
		Question qs3 = new Question(2, "3+2=?", "math", "5", choices);
		Question qs4 = new Question(3, "4+2=?", "math", "none",choices);
		Question qs5 = new Question(4, "5+2=?", "math", "none", choices);
		Question qs6 = new Question(5, "6+2=?", "math", "none", choices);
		Question qs7 = new Question(6, "7+2=?", "math", "none", choices);
		Question qs8 = new Question(7, "8+2=?", "math", "none", choices);
		Question qs9 = new Question(8, "9+2=?", "math", "none", choices);
		Question qs10 = new Question(9, "10+2=?", "math", "none", choices);
		

		//System.out.println("answer  "+qs1.getAnswer());
		getInstance().insertQuestion(qs1);
		getInstance().insertQuestion(qs2);
		getInstance().insertQuestion(qs3);
		getInstance().insertQuestion(qs4);
		getInstance().insertQuestion(qs5);
		getInstance().insertQuestion(qs6);
		getInstance().insertQuestion(qs7);
		getInstance().insertQuestion(qs8);
		getInstance().insertQuestion(qs9);
		getInstance().insertQuestion(qs10);
	}
	
	public static void main(final String[] args) {
		getInstance().basicInit();
		
		ArrayList<String> choices =  new ArrayList<String>(Arrays.asList("3","4","5","none"));
		getInstance().insertCategory("math");

		
		//System.out.println(getInstance().findQuestions("math"));
		//System.out.println(getInstance().findQuestion(qs1.getQsID()));
		
		Quiz qz = getInstance().createQuiz("math");
		System.out.println(qz);
		Quiz qz2 = getInstance().createQuiz("math");
		System.out.println(qz2);
		Quiz qz3 = getInstance().createQuiz("math");
		System.out.println(qz3);
		
		int id1 = qz.getQzId();
		System.out.println(getInstance().findQuiz(id1));
		getInstance().updateQuiz(qz, "aida", 20, 1);
		System.out.println(getInstance().findQuiz(id1));
		getInstance().updateQuiz(qz, "aria", 21, 2);
		System.out.println(getInstance().findQuiz(id1));
		getInstance().updateQuiz(qz, "asghar", 19, 3);
		
		
		getInstance().insertCategory("math");
		getInstance().insertCategory("phys");
		System.out.println(getInstance().findCategories());
		System.out.println(getInstance().findCategory("math"));
		
		/*
		int id2 = getInstance().createQuiz("physics").getQzId();
		Quiz qz2 = getInstance().findQuiz(id2);
		System.out.println(qz2.getCategory());
		*/
		
		User u = new User("ییوزرنیم", "پسورد", "ایمیل","gender", "country","کد");
			try {
				getInstance().insertUser(u);
				//db.getCollection("users").drop();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
	        if(getInstance().existConfirmedUser("ییوزرنیم", "پسورد")) System.out.println("YES");
	        else System.out.println("NO");
	        
	        if(getInstance().confirmEmail("ایمیل", "کد")) System.out.println("YEES");
	        else System.out.println("NOO");	
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
        	System.out.println("duplicattte username");
        }
        
	}
	
	public boolean confirmEmail(String email, String confirmationCode){
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
		ArrayList<Question> allQuestions = findQuestions(category);
		ArrayList<Integer> allQuestionsIDs = new ArrayList<Integer>();
		for(int i=0; i<allQuestions.size(); i++) 
			allQuestionsIDs.add(allQuestions.get(i).getQsID());
		
		ArrayList<Integer> selectedIDs = new ArrayList<Integer>();
		while(selectedIDs.size()<7) {
			int index=new Random(System.currentTimeMillis()).nextInt(allQuestionsIDs.size());
			if(! selectedIDs.contains(allQuestionsIDs.get(index)))
				selectedIDs.add(allQuestionsIDs.get(index));
		}
		//Quiz quiz = new Quiz(category, 78, selectedIDs);

		Quiz quiz = new Quiz(category, (int) quizs.count() /*lastQzID++*/, selectedIDs);

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
		return new Quiz(((Integer)qz.get("_id")).intValue(), ((Integer)qz.get("score1")).intValue(), ((Integer)qz.get("score2")).intValue(), 
				((Integer)qz.get("finishTime1")).intValue(), ((Integer)qz.get("finishTime2")).intValue(), 
				(String)qz.get("category"), (String)qz.get("uid1"), (String)qz.get("uid2"), 
				(ArrayList<Integer>) qz.get("qsIDs"));
	}
	
	public void updateQuiz(Quiz q, String username, int score, int finishTime) {
		
		BasicDBObject query = new BasicDBObject("_id", q.getQzId());
		DBCursor curs = quizs.find(query);
		
	/*	System.out.println(cursor.next());
  	  DBObject c = cursor.curr();
  	  BasicDBObject set = new BasicDBObject("$set", new BasicDBObject("mailConfirmed", true));
		  users.update(c, set);
		*/  
		try {
			if (curs.hasNext()) {
				DBObject qz = curs.next();
				if(qz.get("uid1").equals("")) {
					quizs.update((DBObject) qz, (BasicDBObject) new BasicDBObject("$set", 
							new BasicDBObject("uid1", username)
							.append("score1", score)
							.append("finishTime1", finishTime)
						));
					
				}
				else if(qz.get("uid2").equals("")) {
					quizs.update((DBObject) qz, (BasicDBObject) new BasicDBObject("$set", 
							new BasicDBObject("uid2", username)
							.append("score2", score)
							.append("finishTime2", finishTime)
						));
				}
				else 
					System.out.println("This quiz has done by two user before");
		
		}
		
		} finally {
			curs.close();
		}
	}
	
	private void insertQuiz(Quiz q) //throws Exception
	{
		if(findCategory(q.getCategory()) == null) {
			System.out.println("category not found");
			return;
		}
        BasicDBObject doc = new BasicDBObject("_id", q.getQzId())
                .append("score1", q.getScore1())
                .append("score2", q.getScore2())
                .append("finishTime1", q.getFinishTime1())
                .append("finishTime2", q.getFinishTime2())
                .append("category", q.getCategory())
                .append("uid1", q.getUid1())
        		.append("uid2", q.getUid2())
        		.append("qsIDs", q.getQsIDs());
        
        try {
        	quizs.insert(doc);
        } catch(MongoException ex) {
        	System.out.println("duplicattte quiz id");
        }
	}


	//QUESTION METHODS
	public Question insertQuestion(String text, String category, String answer, ArrayList<String> choices) {

		Question qs = new Question((int)questions.count()/*lastQsID++*/, text, category, answer, choices);
		insertQuestion(qs);
		return qs;
	}
	
	private void insertQuestion(Question qs)
	{
		if(findCategory(qs.getCategory()) == null) {
			System.out.println("category not found");
			return;
		}

        BasicDBObject doc = new BasicDBObject("_id", qs.getQsID())
                .append("text", qs.getText())
                .append("category", qs.getCategory())
                .append("answer", qs.getAnswer())
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
		return new Question(((Integer)qs.get("_id")).intValue(), (String)qs.get("text"), 
				(String)qs.get("category"), (String)qs.get("answer"),
				(ArrayList<String>) qs.get("choices"));
	}
	
	public ArrayList<Question> findQuestions(String category) {
		ArrayList<Question> questionsArr= new ArrayList<Question>();
		BasicDBObject query =new BasicDBObject("category", category);

		DBCursor curs = questions.find(query);
		try {
			while (curs.hasNext()) {
			DBObject qs = curs.next() ;
			//System.out.println(qs);
			questionsArr.add(new Question(((Integer)qs.get("_id")).intValue(), (String)qs.get("text"), 
					(String)qs.get("category"), (String)qs.get("answer"),
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
			DBObject cat = curs.next() ;
			//System.out.println(qs);
			cats.add((String) cat.get("_id"));
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
