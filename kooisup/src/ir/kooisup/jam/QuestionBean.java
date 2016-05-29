package ir.kooisup.jam;

import java.util.ArrayList;
import java.util.Arrays;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import ir.kooisup.jam.CategoryBean.Category;


@ManagedBean
@ViewScoped
public class QuestionBean {
	
	private DBHandler db = DBHandler.getInstance();
	private ArrayList<Question> questionsList;
	private String questionsCategory;
	private Question currentQuestion;
	
	public QuestionBean(){
		questionsList = new ArrayList<Question>();
		
	}
	
	public void onload(){
		
		
		ArrayList<String> choices =  new ArrayList<String>(Arrays.asList("none","5","4","3"));
		db.insertCategory(questionsCategory);
		Question qs1 = new Question(0, "1+2=?", questionsCategory, "3", choices);
		Question qs2 = new Question(1, "2+2=?", questionsCategory, "4", choices);
		Question qs3 = new Question(2, "3+2=?", questionsCategory, "5", choices);
		Question qs4 = new Question(3, "4+2=?", questionsCategory, "none",choices);
		Question qs5 = new Question(4, "5+2=?", questionsCategory, "none", choices);
		Question qs6 = new Question(5, "6+2=?", questionsCategory, "none", choices);
		Question qs7 = new Question(6, "7+2=?", questionsCategory, "none", choices);
		Question qs8 = new Question(7, "8+2=?", questionsCategory, "none", choices);
		Question qs9 = new Question(8, "9+2=?", questionsCategory, "none", choices);
		Question qs10 = new Question(9, "10+2=?", questionsCategory, "none", choices);
		
		System.out.println("-----> "+questionsCategory);

		//System.out.println("answer  "+qs1.getAnswer());
		db.insertQuestion(qs1);
		db.insertQuestion(qs2);
		db.insertQuestion(qs3);
		db.insertQuestion(qs4);
		db.insertQuestion(qs5);
		db.insertQuestion(qs6);
		db.insertQuestion(qs7);
		db.insertQuestion(qs8);
		db.insertQuestion(qs9);
		db.insertQuestion(qs10);
		
		loadQuestionsList(questionsCategory);
	}
	
	public ArrayList<Question> getQuestionsList(){
		return questionsList;
	}
	
	public void loadQuestionsList(String category){
		questionsList = db.findQuestions(category);
	}

	public String getQuestionsCategory() {
		return questionsCategory;
	}

	public void setQuestionsCategory(String questionsCategory) {
		this.questionsCategory = questionsCategory;
	}
	
	public String deleteAction(Question q) {
    	questionsList.remove(q);
    	return null;
    }

	public Question getCurrentQuestion() {
		return currentQuestion;
	}

	public void setCurrentQuestion(Question currentQuestion) {
		this.currentQuestion = currentQuestion;
	}
	
//	public String addQuestion(){
//    	Question q = new Question();
//    	cat.setName(currentCategory);
//    	categoryList.add(cat);
//    	return null;
//     }
	
}
