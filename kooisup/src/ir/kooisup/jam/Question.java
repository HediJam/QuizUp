package ir.kooisup.jam;

import java.util.ArrayList;

public class Question {
	/*public Question(String text, ArrayList<String> choices){
		this.text = text;
		this.choices = new ArrayList<String>(choices);
	}*/
	
	public Question(int qsID, String text, String category, String answer, ArrayList<String> choices) {
		this.qsID=qsID;
		this.text=text;
		this.category=category;
		this.choices=choices;
		this.answer=answer;
	}

	private String text;
	private String answer;
	private String category;
	private int qsID;
	ArrayList<String> choices;
	
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
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getQsID() {
		return qsID;
	}
	public void setQsID(int qsID) {
		this.qsID = qsID;
	}
	public ArrayList<String> getChoices() {
		return choices;
	}
	public void setChoices(ArrayList<String> choices) {
		this.choices = choices;
	}
	@Override
	public String toString() {
		return "Question [text=" + text + ", answer=" + answer + ", category=" + category + ", qsID=" + qsID
				+ ", choices=" + choices + "]";
	}
	
	
}
