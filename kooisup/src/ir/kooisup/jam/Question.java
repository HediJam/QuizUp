package ir.kooisup.jam;

import java.util.ArrayList;

public class Question {
	public Question(String text, ArrayList<String> choices){
		this.text = text;
		this.choices = new ArrayList<String>(choices);
	}
	
	private String text;
	ArrayList<String> choices;
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	public ArrayList<String> getChoices() {
		return choices;
	}
	public void setChoices(ArrayList<String> choices) {
		this.choices = choices;
	}
}