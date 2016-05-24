package ir.kooisup.jam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpSession;

@ManagedBean
@SessionScoped
public class QuizHandler {

	String curQuestion;
	String option1;
	String option2;
	String option3;
	String option4;
	String selectedValue;
	String timer = "0";
	String score = "0";
	String category;
	Quiz quiz;
	int i = 0;
	String quizId;
	int numOfQuestions = 7;
	ArrayList<Question> questions;
	ArrayList<String> options;
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}



	public QuizHandler() {

		System.out.println("man tuye constructoram....");
		System.out.println("quiz ghadimi ba id " + quizId);
		System.out.println("category ghadimi ba id " + category);
		
		/*DBHandler db = DBHandler.getInstance();
		
		if (quizId == null) {
			System.out.println("nulle");
			db.basicInit();
			quiz = db.createQuiz("math");
		} else {
			System.out.println("quiz ghadimi ba id " + quizId);
			quiz = db.findQuiz(Integer.valueOf(quizId));
		}
		
		questions = db.loadQuestions(quiz);
		curQuestion = questions.get(0).getText();
		options = questions.get(0).choices;
		Collections.sort(options);
		option1 = options.get(0);
		option2 = options.get(1);
		option3 = options.get(2);
		option4 = options.get(3);*/
	}

	public String getQuizId() {
		return quizId;
	}

	public void setQuizId(String quizId) {
		System.out.println("set quizID .... "+ quizId) ;
		this.quizId = quizId;
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

	public void ajaxSubmit(AjaxBehaviorEvent event) {
		ExternalContext ec1 = FacesContext.getCurrentInstance().getExternalContext();
		
		String[] value = ec1.getRequestParameterValuesMap().get("id");
		System.out.println(value[0]);
		if (i < numOfQuestions - 1) {
			System.out.println("changing event..." + i + quizId);

			// FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form:opt1");

			System.out.println("selected value is :   " + selectedValue);
			if (questions.get(i).getAnswerIndex() == Integer.valueOf(selectedValue)) {
				System.out.println("javab dorost bud...");
				score = Integer.toString((Integer.parseInt(score) + 1));

			}

			i++;
			timer = Long.toString(System.currentTimeMillis());

			curQuestion = questions.get(i).getText();
			System.out.println(curQuestion);
			options = questions.get(i).choices;
			Collections.sort(options);
			option1 = options.get(0);
			option2 = options.get(1);
			option3 = options.get(2);
			option4 = options.get(3);
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			try {
				selectedValue = null;
				ec.redirect(ec.getRequestContextPath() + "/" + "quiz.xhtml");

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (i == numOfQuestions - 1) {
			setUser();
			System.out.println("Result Page.....");
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			try {
				System.out.println(ec.getRequestContextPath() + "/" + "result.xhtml?id = " + quiz.getQzId());
				ec.redirect(ec.getRequestContextPath() + "/" + "result.xhtml?id=" + quiz.getQzId());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void increment(AjaxBehaviorEvent event) {
		timer = Integer.toString((Integer.parseInt(timer) + 1));
	}

	private void setUser() {
		HttpSession hs = Util.getSession();
		String username = hs.getAttribute("username").toString();
		System.out.println(username);
		DBHandler db = DBHandler.getInstance();
		//db.updateQuiz(quiz.getQsIDs(), username, Integer.valueOf(score), 40);
	}
	
	public void onload(){
		System.out.println("tuye onloadam");
		DBHandler db = DBHandler.getInstance();
		if (quizId == null) {
			System.out.println("nulle");
			db.basicInit();
			quiz = db.createQuiz("MATH");
		} else {
			System.out.println("quiz ghadimi ba id " + quizId);
			quiz = db.findQuiz(Integer.valueOf(quizId));
		}
		
		questions = db.loadQuestions(quiz);
		curQuestion = questions.get(0).getText();
		options = questions.get(0).choices;
		Collections.sort(options);
		option1 = options.get(0);
		option2 = options.get(1);
		option3 = options.get(2);
		option4 = options.get(3);
	}
	
	public void antiBabak(){
		System.out.println("az hamin tiribun elam mikonam ridi :|");
	}

}
