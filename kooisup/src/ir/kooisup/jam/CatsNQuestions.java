package ir.kooisup.jam;

import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class CatsNQuestions {
	public CatsNQuestions(){
		categories = new ArrayList<String>();
		categories.add("ورزشی");
		categories.add("فیلم");
		categories.add("کتاب");
		selectedCategory = "همینطوری";
	}
	
	public void fetchCategories(){
		DBHandler db = DBHandler.getInstance();
		//categories = new ArrayList<String>(db.fetchCategories());
	}
	
	public void fetchQuestions(){
		DBHandler db = DBHandler.getInstance();
		//questions = new ArrayList<Question>(db.fetchQuestions(selectedCategory));
	}
	
	private String selectedCategory;
	private ArrayList<Question> questions;
	private ArrayList<String> categories;
	
	public ArrayList<String> getCategories() {
		return categories;
	}

	public String getSelectedCategory() {
		return selectedCategory;
	}

	public void setSelectedCategory(String selectedCategory) {
		this.selectedCategory = selectedCategory;
	}
}
