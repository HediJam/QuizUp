package ir.kooisup.jam;

import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


@ManagedBean
@ViewScoped
public class QuestionBean {
	
	private DBHandler db = DBHandler.getInstance();
	private ArrayList<Question> questionsList;
	private String questionsCategory;
	private Question currentQuestion;
	
	public QuestionBean(){
		questionsList = new ArrayList<Question>();
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
