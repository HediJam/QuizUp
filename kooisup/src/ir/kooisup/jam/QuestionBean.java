package ir.kooisup.jam;

import java.util.ArrayList;

public class QuestionBean {
	
	private DBHandler db = DBHandler.getInstance();
	private ArrayList<Question> questionsList;
	
	public QuestionBean(){
		questionsList = new ArrayList<Question>();
	}
	
	public ArrayList<Question> getQusetionsList(){
		return questionsList;
	}
	
	public void loadQuestionsList(String category){
		questionsList = db.findQuestions(category);
	}
	
}
