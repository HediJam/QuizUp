package ir.kooisup.jam;
import  java.lang.*;
import com.mongodb.BasicDBList;
import org.bson.Document;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Sorts.ascending;
import static java.util.Arrays.asList;

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
import java.util.Random;
import java.util.Set;
import java.util.ArrayList;
import java.util.Arrays;

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
	private DB db;
	private DBCollection questions;
	private DBCollection quizs;
	private DBCollection categories;
	private DBCollection users;
	private DBCollection requests;
	
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
        //db.getCollection("users").drop();
        db.getCollection("quizs").drop();
        db.getCollection("questions").drop();
        db.getCollection("categories").drop();
        db.getCollection("requests").drop();
        
        
        users = db.getCollection("users");
        quizs = db.getCollection("quizs");
        questions = db.getCollection("questions");
        categories = db.getCollection("categories");
        requests = db.getCollection("requests");
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
	
	public static void main(final String[] args) throws Exception {
		getInstance().basicInit();
		
		
		System.out.println(getInstance().findQuestion(9));
		ArrayList<String> choices =  new ArrayList<String>(Arrays.asList("none","5","4","3"));
		getInstance().updateQuestion(9, new Question(9, "20-2=?", "phys", "1", choices));
		System.out.println(getInstance().findQuestion(9));
		getInstance().removeQuestion(9);
		System.out.println(getInstance().findQuestion(9));
		

		//System.out.println(getInstance().findQuestions("math"));
		//System.out.println(getInstance().findQuestion(qs1.getQsID()));
		
		Quiz qz = getInstance().createQuiz("math");
		System.out.println(qz);
		Quiz qz2 = getInstance().createQuiz("math");
		System.out.println(qz2);
		Quiz qz3 = getInstance().createQuiz("math");
		Quiz qz4 = getInstance().createQuiz("math");
		System.out.println(qz3);
		
		qz.setUid1("1");
		qz.setUid2("2");
		qz2.setUid1("1");
		qz2.setUid2("2");
		qz3.setUid1("1");
		qz3.setUid2("2");
		qz4.setUid1("3");
		qz4.setUid2("4");
		
		qz.setScore1(11);
		qz.setScore2(10);
		qz2.setScore1(10);
		qz2.setScore2(10);
		qz3.setScore1(9);
		qz3.setScore2(10);
		qz4.setScore1(9);
		qz4.setScore2(10);
		
		qz.setFinishTime1(2);
		qz.setFinishTime2(3);
		qz2.setFinishTime1(3);
		qz2.setFinishTime2(2);
		qz3.setFinishTime1(2);
		qz3.setFinishTime2(3);
		qz4.setFinishTime1(2);
		qz4.setFinishTime2(3);
		
		getInstance().updateQuiz(0, qz);
		getInstance().updateQuiz(1, qz2);
		getInstance().updateQuiz(2, qz3);
		getInstance().updateQuiz(3, qz4);
		
		System.out.println(getInstance().findQuiz(0));
		System.out.println(getInstance().findQuiz(1));
		System.out.println(getInstance().findQuiz(2));
		
		System.out.println("numOfWin");
		System.out.println(getInstance().numOfWin("2"));
		System.out.println(getInstance().sumScore("2"));
		System.out.println(getInstance().maxScore("2"));
		
		
		
		int id1 = qz.getQzId();
		getInstance().insertUser(new User("1", "پسورد", "em1","gender", "country","کد"));
		getInstance().insertUser(new User("2", "پسورد", "em2","gender", "country","کد"));
		
		System.out.println(getInstance().findQuiz(id1));
		getInstance().updateQuiz(id1, "u1", 20, 1);
		System.out.println(getInstance().findQuiz(id1));
		getInstance().updateQuiz(id1, "u2", 21, 2);
		System.out.println(getInstance().findQuiz(id1));
		System.out.println("winneeeeeeeer");
		System.out.println(getInstance().getWinner(qz));
		
		getInstance().updateQuiz(id1, "asghar", 21, 1);
		
		System.out.println(getInstance().getEmailUsers(qz));
		
		getInstance().insertCategory("math");
		getInstance().insertCategory("phys");
		System.out.println(getInstance().findCategories());
		System.out.println(getInstance().findCategory("math"));
		

		getInstance().onlineRequest(0, "0");
		System.out.println(getInstance().findQuiz(0));
		getInstance().onlineRequest(1, "1");
		System.out.println(getInstance().findQuiz(1));
		getInstance().onlineRequest(2, "2");
		System.out.println(getInstance().findQuiz(2));
		
		System.out.println(getInstance().anyRequest());
		getInstance().acceptRequest(0, "00");
		System.out.println(getInstance().findQuiz(0));
		System.out.println(getInstance().anyRequest());
		
		System.out.println(getInstance().hasAccepted(1));
		getInstance().acceptRequest(1, "11");
		System.out.println(getInstance().hasAccepted(1));
		System.out.println(getInstance().findQuiz(1));
		
		System.out.println(getInstance().anyRequest());
		getInstance().acceptRequest(2, "22");
		System.out.println(getInstance().findQuiz(2));
		System.out.println(getInstance().anyRequest());
		System.out.println(getInstance().hasAccepted(3));
		
		System.out.println(getInstance().hasOponentFinish(1, "1"));
		System.out.println(getInstance().hasOponentFinish(1, "11"));
		System.out.println(getInstance().findQuiz(1).numFinished());
		
		getInstance().Ifinished(1, "1", 3, 11);
		System.out.println(getInstance().findQuiz(1));
		System.out.println(getInstance().hasOponentFinish(1, "1"));
		System.out.println(getInstance().hasOponentFinish(1, "11"));
		System.out.println(getInstance().findQuiz(1).numFinished());
		
		getInstance().Ifinished(1, "11", 4, 10);
		System.out.println(getInstance().findQuiz(1));
		System.out.println(getInstance().hasOponentFinish(1, "1"));
		System.out.println(getInstance().hasOponentFinish(1, "11"));
		System.out.println(getInstance().findQuiz(1).numFinished());
		
		System.out.println(getInstance().findQuiz(1).getScore("1"));
		System.out.println(getInstance().findQuiz(1).getScore("11"));
		
		
		System.out.println(getInstance().findQuestions("math"));
		System.out.println(getInstance().findCategories());
		getInstance().updateCategory("math", "MATH");
		System.out.println(getInstance().findQuestions("math"));
		System.out.println(getInstance().findQuestions("MATH"));
		System.out.println(getInstance().findCategories());
		System.out.println(getInstance().findQuiz(1));
		
		getInstance().removeCategory("MATH");
		System.out.println(getInstance().findQuestions("MATH"));
		System.out.println(getInstance().findCategories());
		System.out.println(getInstance().findQuiz(1));
		
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
	
	public User findUser(String uid) {
		DBObject user = findOne(new BasicDBObject("_id", uid), users);
		if(user==null)
		{
			System.out.println("Invalid user ID => not found so return null");
			return null;
		}
			   
		return new User((String)user.get("_id"), (String)user.get("password"), 
				(String)user.get("email"), (String)user.get("gender"), 
				(String)user.get("country"), (String)user.get("confirmationCode"), ((Boolean)user.get("mailConfirmed")).booleanValue());
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
	
	public void updateQuiz(int qzID, Quiz q) {
		
		BasicDBObject query = new BasicDBObject("_id", qzID);
		DBCursor curs = quizs.find(query);
		
		BasicDBObject newQuiz = new BasicDBObject("$set", 
				new BasicDBObject("score1", q.getScore1())
                .append("score2", q.getScore2())
                .append("finishTime1", q.getFinishTime1())
                .append("finishTime2", q.getFinishTime2())
                .append("category", q.getCategory())
                .append("uid1", q.getUid1())
        		.append("uid2", q.getUid2())
        		.append("qsIDs", q.getQsIDs()));
		try {
			if (curs.hasNext()) {
				DBObject qz = curs.next();
				quizs.update((DBObject) qz, (BasicDBObject) newQuiz);		
			}
		} finally {
			curs.close();
		}
	}
	
	public void updateQuiz(int qzID, String username, int score, int finishTime) {
		Quiz q=findQuiz(qzID);
		if(q.numPlayed()==0) {
			q.setUid1(username);
			q.setFinishTime1(finishTime);
			q.setScore1(score);
		}
		else if(q.numPlayed()==1) {
			q.setUid2(username);
			q.setFinishTime2(finishTime);
			q.setScore2(score);
		}
		else 
			System.out.println("This quiz has done by two user before");
		updateQuiz(qzID, q);
	}
	/*
	public void updateQuiz(Quiz q, String username, int score, int finishTime) {
		
		BasicDBObject query = new BasicDBObject("_id", q.getQzId());
		DBCursor curs = quizs.find(query);
		
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
	*/
	
	public User getWinner(Quiz q) {
		Quiz qz = findQuiz(q.getQzId());
		if(qz==null) {
			System.out.println("not found quiz");
			return null;
		}
		String uid=qz.winner();
		return findUser(uid);
	}
	
	public ArrayList<String> getEmailUsers(Quiz qz) {
		ArrayList<String> emails =  new ArrayList<String>();
		Quiz q=findQuiz(qz.getQzId());
		emails.add(findUser(q.getUid1()).getEmail());
		emails.add(findUser(q.getUid2()).getEmail());
		return emails;
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

	public void updateQuestion(int qsID, Question qs) {
		BasicDBObject query = new BasicDBObject("_id", qsID);
		DBCursor curs = questions.find(query);
		
		BasicDBObject newQuestion = new BasicDBObject("$set", 
				new BasicDBObject("text", qs.getText())
                .append("category", qs.getCategory())
                .append("answer", qs.getAnswer())
                .append("choices", qs.getChoices()));;
		try {
			if (curs.hasNext()) {
				DBObject q = curs.next();
				questions.update((DBObject) q, (BasicDBObject) newQuestion);		
			}
		} finally {
			curs.close();
		}
	}
	
	public void removeQuestion(int qsID) {
		BasicDBObject query = new BasicDBObject("_id", qsID);
		DBCursor curs = questions.find(query);
		try {
			if (curs.hasNext()) {
				questions.remove(curs.next());
			}
		} finally {
			curs.close();
		}
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
	
	public void updateCategory(String category, String newCategory) {
		BasicDBObject categoryQuery = new BasicDBObject("_id", category);
		DBCursor curs = categories.find(categoryQuery);
		try {
			if (curs.hasNext()) {
				categories.remove(curs.next());
				}
		} finally {
			curs.close();
		}
		getInstance().insertCategory(newCategory);
		
		BasicDBObject questionQuery = new BasicDBObject("category", category);
		curs = questions.find(questionQuery);
		try {
			while (curs.hasNext()) {
				DBObject q = curs.next();
				questions.update((DBObject) q, (BasicDBObject)  new BasicDBObject("$set", 
						new BasicDBObject("category", newCategory)));		
			}
		} finally {
			curs.close();
		}
		
		BasicDBObject quizQuery = new BasicDBObject("category", category);
		curs = quizs.find(questionQuery);
		try {
			while (curs.hasNext()) {
				DBObject q = curs.next();
				quizs.update((DBObject) q, (BasicDBObject)  new BasicDBObject("$set", 
						new BasicDBObject("category", newCategory)));		
			}
		} finally {
			curs.close();
		}
	}
	
	public void removeCategory(String category) {
		BasicDBObject categoryQuery = new BasicDBObject("_id", category);
		DBCursor curs = categories.find(categoryQuery);
		try {
			if (curs.hasNext()) {
				categories.remove(curs.next());
				}
		} finally {
			curs.close();
		}
	}
	
	//REQUEST METHODS
	private void insertRequest(int qzID/*, String uid1*/) {
			
		BasicDBObject doc = new BasicDBObject("_id", qzID)
	               .append("accept", false);
	     try {
	        	requests.insert(doc);
	     } catch(MongoException ex) {
	    	 System.out.println("duplicattte quiz id");
	     }
	}
	
	public void onlineRequest(int qzID, String uid1) {
		insertRequest(qzID/*, uid1*/);
		Quiz q=findQuiz(qzID);
		q.setUid1(uid1);
		updateQuiz(q.getQzId(), q);
	}
	
	public int anyRequest() {
			DBCursor curs = requests.find(new BasicDBObject("accept", false));
			try {
				if (curs.hasNext()) {
				DBObject rq = curs.next() ;
				return ((Integer)rq.get("_id")).intValue();
			}
			
			} finally {
				curs.close();
			}
			return -1;
		}
	
	public void acceptRequest(int qzID, String uid2) {
		DBCursor curs = requests.find(new BasicDBObject("_id", qzID)
										.append("accept", false));
		try {
			if (curs.hasNext()) {
				//requests.remove(curs.next());
				DBObject rq = curs.next();
				requests.update((DBObject) rq, (BasicDBObject) new BasicDBObject("$set", 
						new BasicDBObject("accept", true)));		
		
				Quiz q=findQuiz(qzID);
				q.setUid2(uid2);
				updateQuiz(q.getQzId(), q);
			} 
			else {
				System.out.println("no such quiz id");
			}
		
		} finally {
			curs.close();
		}
	}
	
	public boolean hasAccepted(int qzID) {
		DBObject rq = findOne(new BasicDBObject("_id", qzID), requests);
		if(rq==null) 
		{
			System.out.println("invalid quiz ID => return null");
			return false;
		}
		return ((Boolean)rq.get("accept")).booleanValue();
	}
	
	public void Ifinished(int qzID, String uid, int finishTime, int score) {
		Quiz q=findQuiz(qzID);
		if(uid.equals(q.getUid1())) 
		{
			q.setFinishTime1(finishTime);
			q.setScore1(score);
		}
		else if(uid.equals(q.getUid2())) 
		{
			q.setFinishTime2(finishTime);
			q.setScore2(score);
		}
		updateQuiz(q.getQzId(), q);
	}
	
	public boolean hasOponentFinish(int qzID, String uid) {
		Quiz q=findQuiz(qzID);
		return q.hasOponentFinished(uid);
	}
	
	//PROFILE METHODS
	public int numOfWin(String uid) {
		int res=0;
		DBCursor curs = quizs.find(new BasicDBObject("$or", asList(new BasicDBObject("uid1", uid),
		        		new BasicDBObject("uid2", uid))));
		try {
			while (curs.hasNext()) {
				DBObject qz = curs.next() ;
				Quiz quiz = new Quiz(((Integer)qz.get("_id")).intValue(), ((Integer)qz.get("score1")).intValue(), ((Integer)qz.get("score2")).intValue(), 
					((Integer)qz.get("finishTime1")).intValue(), ((Integer)qz.get("finishTime2")).intValue(), 
					(String)qz.get("category"), (String)qz.get("uid1"), (String)qz.get("uid2"), 
					(ArrayList<Integer>) qz.get("qsIDs"));
				//System.out.println("id "+quiz.getQzId());
				//System.out.println("win "+quiz.winner());
				if(quiz.winner().equals(uid)) res++;
		}
		
		} finally {
			curs.close();
		}
		return res;
	}
	
	private int max(int x, int y) {
		if(x>=y) return x;
		return y;
	}
	public int maxScore(String uid) {
		int res=0;
		DBCursor curs = quizs.find(new BasicDBObject("$or", asList(new BasicDBObject("uid1", uid),
		        		new BasicDBObject("uid2", uid))));
		try {
			while (curs.hasNext()) {
				DBObject qz = curs.next() ;
				Quiz quiz = new Quiz(((Integer)qz.get("_id")).intValue(), ((Integer)qz.get("score1")).intValue(), ((Integer)qz.get("score2")).intValue(), 
					((Integer)qz.get("finishTime1")).intValue(), ((Integer)qz.get("finishTime2")).intValue(), 
					(String)qz.get("category"), (String)qz.get("uid1"), (String)qz.get("uid2"), 
					(ArrayList<Integer>) qz.get("qsIDs"));
				//System.out.println("id "+quiz.getQzId());
				//System.out.println("win "+quiz.winner());
				res = max(res, quiz.getScore(uid));
		}
		
		} finally {
			curs.close();
		}
		return res;
	}
	public int sumScore(String uid) {
		int res=0;
		DBCursor curs = quizs.find(new BasicDBObject("$or", asList(new BasicDBObject("uid1", uid),
		        		new BasicDBObject("uid2", uid))));
		try {
			while (curs.hasNext()) {
				DBObject qz = curs.next() ;
				Quiz quiz = new Quiz(((Integer)qz.get("_id")).intValue(), ((Integer)qz.get("score1")).intValue(), ((Integer)qz.get("score2")).intValue(), 
					((Integer)qz.get("finishTime1")).intValue(), ((Integer)qz.get("finishTime2")).intValue(), 
					(String)qz.get("category"), (String)qz.get("uid1"), (String)qz.get("uid2"), 
					(ArrayList<Integer>) qz.get("qsIDs"));
				//System.out.println("id "+quiz.getQzId());
				//System.out.println("win "+quiz.winner());
				res +=quiz.getScore(uid);
		}
		
		} finally {
			curs.close();
		}
		return res;
	}
	
}
