package ir.kooisup.jam;

import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;

@ManagedBean
@SessionScoped
public class CatsNQuestions {
	public CatsNQuestions(){
		categories = new ArrayList<String>();
		categories.add("ورزشی");
		categories.add("فیلم");
		categories.add("کتاب");


		questions = new ArrayList<Question>();
		ArrayList<String> choices = new ArrayList<String>();
		choices.add("6");
		choices.add("5");
		choices.add("4");
		choices.add("3");
		Question q = new Question(1, "chanta?", "ورزشی", choices.get(0), choices);
		questions.add(q);
	}
	
	public void fetchCategories(){
		DBHandler db = DBHandler.getInstance();
		//categories = new ArrayList<String>(db.fetchCategories());
	}
	
	public void fetchQuestions(){
		DBHandler db = DBHandler.getInstance();
		//questions = new ArrayList<Question>(db.fetchQuestions(selectedCategory));
	}
	
	public void processAction(ActionEvent event) throws AbortProcessingException {
		//access userData bean directly
		CatsNQuestions catsNQuestions = (CatsNQuestions) FacesContext.getCurrentInstance().
				getExternalContext().getSessionMap().get("catsNQuestions");
		catsNQuestions.setSelectedCategory((String)event.getComponent().getAttributes().get("catName"));
	}
	
	public String addQuestion(){
//		DBHandler db = DBHandler.getInstance();
//		db.insertQuestion(text, selectedCategory, choices.get(0), choices);
		ArrayList<String> choices = new ArrayList<String>();
		choices.add(firstChoice);
		choices.add(secChoice);
		choices.add(thirdChoice);
		choices.add(fourthChoice);
		Question q = new Question(2, "chanta?", "ورزشی", choices.get(0), choices);
		questions.add(q);
		return "edit_questions";
	}
	
	private String selectedCategory;
	private ArrayList<Question> questions;
	private ArrayList<String> categories;


	public String getSelectedCategory() {
		return selectedCategory;
	}

	public void setSelectedCategory(String selectedCategory) {
		this.selectedCategory = selectedCategory;
//		DBHandler db = DBHandler.getInstance();
//		questions = new ArrayList<Question>(db.findQuestions(selectedCategory));
	}

	public ArrayList<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(ArrayList<Question> questions) {
		this.questions = questions;
	}
	
	public ArrayList<String> getCategories() {
//		DBHandler db = DBHandler.getInstance();
//		categories = new ArrayList<String>(db.findCategories());
		return categories;
	}
	
	private String text;
	private String firstChoice;
	private String secChoice;
	private String thirdChoice;
	private String fourthChoice;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getFirstChoice() {
		return firstChoice;
	}

	public void setFirstChoice(String firstChoice) {
		this.firstChoice = firstChoice;
	}

	public String getSecChoice() {
		return secChoice;
	}

	public void setSecChoice(String secChoice) {
		this.secChoice = secChoice;
	}

	public String getThirdChoice() {
		return thirdChoice;
	}

	public void setThirdChoice(String thirdChoice) {
		this.thirdChoice = thirdChoice;
	}

	public String getFourthChoice() {
		return fourthChoice;
	}

	public void setFourthChoice(String fourthChoice) {
		this.fourthChoice = fourthChoice;
	}


}
