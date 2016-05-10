package ir.kooisup.jam;

import java.io.IOException;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

@ManagedBean
@SessionScoped
public class QuizHandler {
	
	String curQuestion = "question1";
	String option1;
	String option2;
	String option3;
	String option4;
	String selectedValue;
	String timer = "0";
	String score = "0";
	ArrayList<Question> questions;
	ArrayList<String> options;
	
	public QuizHandler() {
		DBHandler db = DBHandler.getInstance();
		Quiz quiz = db.createQuiz("math");
		questions = db.loadQuestions(quiz);
		options = questions.get(0).choices;
		option1 = options.get(0);
		option2 = options.get(1);
		option3 = options.get(2);
		option4 = options.get(3);
	}
	public String getTimer() {
		return timer;
	}
	public void setTimer(String timer) {
		this.timer = timer;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	
	public String getSelectedValue() {
		return selectedValue;
	}
	public void setSelectedValue(String selectedValue) {
		System.out.println("setting .... " + selectedValue);
		this.selectedValue = selectedValue;
	}
	public String getCurQuestion() {
		return curQuestion;
	}
	public void setCurQuestion(String curQuestion) {
		this.curQuestion = curQuestion;
	}
	public String getOption1() {
		return option1;
	}
	public void setOption1(String option1) {
		this.option1 = option1;
	}
	public String getOption2() {
		return option2;
	}
	public void setOption2(String option2) {
		this.option2 = option2;
	}
	public String getOption3() {
		return option3;
	}
	public void setOption3(String option3) {
		this.option3 = option3;
	}
	public String getOption4() {
		return option4;
	}
	public void setOption4(String option4) {
		this.option4 = option4;
	}
	public void ajaxSubmit(AjaxBehaviorEvent event){
	
		System.out.println("changing event...");
		
		FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form:opt1");
		int i = 0;
		for(Question q : questions){
			if( i > 0 ){
				i++;
				options = q.choices;
				option1= "new opt";
				option2 = "new opt2";
				option3 = "new opt3";
				option4 = "new opt4";
				option1 = options.get(0);
				option2 = options.get(1);
				option3 = options.get(2);
				option4 = options.get(3);
				
				if(selectedValue.equals(q.getAnswer())){
					score = Integer.toString((Integer.parseInt(score) + 1));
				}
			}
		}

		timer = Long.toString(System.currentTimeMillis());
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		try {
			ec.redirect(ec.getRequestContextPath() + "/" + "quiz.xhtml" );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void increment(AjaxBehaviorEvent event){
		timer = Integer.toString((Integer.parseInt(timer) + 1));
	}

}
