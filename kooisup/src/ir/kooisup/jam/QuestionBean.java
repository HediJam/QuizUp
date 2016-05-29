package ir.kooisup.jam;

import java.util.ArrayList;
import java.util.Arrays;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.RowEditEvent;

import ir.kooisup.jam.CategoryBean.Category;


@ManagedBean
@ViewScoped
public class QuestionBean {
	
	private DBHandler db = DBHandler.getInstance();
	private ArrayList<Question> questionsList;
	private String questionsCategory;
	private Question currentQuestion;
	private String choice1;
	private String choice2;
	private String choice3;
	private String choice4;
	private String text;
	private String answer = "1";
	private String message;
	
	public QuestionBean(){
		questionsList = new ArrayList<Question>();
		
	}
	
	public void onload(){
		message = null;
		
		text = null;
		answer = null;
		choice1 = null;
		choice2 = null;
		choice3 = null;
		choice4 = null;
		
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
		db.insertQuestion("1+2=?", questionsCategory, "3", choices);
		db.insertQuestion("2+2=?", questionsCategory, "4", choices);
		db.insertQuestion("3+2=?", questionsCategory, "5", choices);
		db.insertQuestion("4+2=?", questionsCategory, "none",choices);
		db.insertQuestion("6+2=?", questionsCategory, "none", choices);
		db.insertQuestion("7+2=?", questionsCategory, "none", choices);
		db.insertQuestion("8+2=?", questionsCategory, "none", choices);
		db.insertQuestion("9+2=?", questionsCategory, "none", choices);
		db.insertQuestion("10+2=?", questionsCategory, "none", choices);
		
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
	
	public void addQuestion(){
		
		if(!check()){
			setMessage("لطفا  فیلد های ستاره دار را پر کنید\n");
			return;
		}
		
		int qsID = questionsList.get(questionsList.size()-1).getQsID() + 1;
		
		ArrayList<String> choices = new ArrayList<String>();
		choices.add(choice1);
		choices.add(choice2);
		choices.add(choice3);
		choices.add(choice4);
		
		answer = "1";
		
		currentQuestion = new Question(qsID, text, questionsCategory, answer, choices);

    	questionsList.add(currentQuestion);

    	db.insertQuestion(text, questionsCategory, answer, choices);
    	setMessage(null);
     }
	

	public void editQuestion(RowEditEvent event){
		
	}

	public boolean check(){
		if( text == null || text.isEmpty() || 
			choice1 == null || choice1.isEmpty() || 
			choice2 == null || choice2.isEmpty() || 
			choice3 == null || choice3.isEmpty() || 
			choice4 == null || choice4.isEmpty() || 
			
			questionsCategory == null || questionsCategory.isEmpty() )
			return false;
			
		return true;	
	}
	
	public String getChoice1() {
		return choice1;
	}

	public void setChoice1(String choice1) {
		this.choice1 = choice1;
	}

	public String getChoice2() {
		return choice2;
	}

	public void setChoice2(String choice2) {
		this.choice2 = choice2;
	}

	public String getChoice4() {
		return choice4;
	}

	public void setChoice4(String choice4) {
		this.choice4 = choice4;
	}

	public String getChoice3() {
		return choice3;
	}

	public void setChoice3(String choice3) {
		this.choice3 = choice3;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
