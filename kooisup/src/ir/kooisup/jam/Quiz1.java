package ir.kooisup.jam;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import javax.faces.application.NavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpSession;

@ManagedBean
@SessionScoped
public class Quiz1 implements Serializable {

	String curQuestion;
	String option1;
	String option2;
	String option3;
	String option4;
	String quizId;
	String category;
	static Quiz quiz;
	static ArrayList<Question> questions = new ArrayList<Question>();
	ArrayList<String> options = new ArrayList<String>();
	static String score = "0";
	static int time = 0;
	String selectedValue;
	static int i = 0;
	int numOfQuestions = 7;
	String opponent;

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public String getSelectedValue() {
		return selectedValue;
	}

	public void setSelectedValue(String selectedValue) {
		this.selectedValue = selectedValue;
	}

	public String getQuizId() {
		return quizId;
	}

	public void setQuizId(String quizId) {
		System.out.println("miam tu  quiz1");
		this.quizId = quizId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Quiz1() {
		// TODO Auto-generated constructor stub
		System.out.println("constructor quiz1 " + quizId);
		System.out.println("constructor " + category);
	}

	public String onload() {

		HttpSession hs = Util.getSession();
		if (hs == null || hs.getAttribute("username") == null) {
			// return "flat-login-form/index.xhtml";
			System.out.println("hs is null........" + quizId);
			// ExternalContext ec =
			// FacesContext.getCurrentInstance().getExternalContext();
			// String address = ec.getRequestContextPath() + "/" +
			// "flat-login-form/index.xhtml?quizId=" + quizId;
			// return
			// "http://localhost:8080/quizup/flat-login-form/index.xhtml?quizId="
			// + quizId;
			return "flat-login-form/index.xhtml?quizId=" + quizId;
		}

		System.out.println("on load  " + quizId);
		System.out.println("on load " + category);
		
		permission();
		System.out.println("tuye onloadam");
		DBHandler db = DBHandler.getInstance();
		if (quizId == null) {
			System.out.println("quiz id is null in onload");
			db.basicInit();
			quiz = db.createQuiz("math");
			quizId = Integer.toString(quiz.getQzId());
			System.out.println("quiz id after creating  " + quizId);

		} else {
			System.out.println("quiz ghadimi ba id ro load konam" + quizId);
			quiz = db.findQuiz(Integer.valueOf(quizId));
		}

		questions = db.loadQuestions(quiz);
		curQuestion = questions.get(i).getText();
		options = questions.get(i).choices;
		Collections.sort(options);
		option1 = options.get(0);
		option2 = options.get(1);
		option3 = options.get(2);
		option4 = options.get(3);
		System.out.println("size qustion is " + questions.size());
		return "th";
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

	private void setUser() {
		HttpSession hs = Util.getSession();
		String username = hs.getAttribute("username").toString();
		System.out.println(username);
		DBHandler db = DBHandler.getInstance();
		db.updateQuiz(quiz, username, Integer.valueOf(score), 40);
	}

	private void permission() {

		HttpSession hs = Util.getSession();
		if (hs == null) {
			System.out.println("hs is null........" + quizId);
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			String address = ec.getRequestContextPath() + "/" + "login.xhtml?quizId=" + quizId;
			try {
				System.out.println("ghabl az redirect" + address);
				ec.redirect("http://localhost:8080/quizup/quiz1.xhtml?id=190");
				System.out.println("bad az redirect" + address);
				// ec.redirect(address);
			} catch (IOException e) {
				System.out.println("IO execption dadam");
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void ajaxSubmit(AjaxBehaviorEvent event) {

		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		System.out.println("chnage----");
		if (i < numOfQuestions) {
			System.out.println("changing event..." + i);
			System.out.println("selected value is :   " + selectedValue);
			if (i < numOfQuestions - 1) {
				questions.get(i).getAnswerIndex();
				Integer.valueOf(selectedValue);
				if (questions.get(i).getAnswerIndex() == Integer.valueOf(selectedValue)) {
					System.out.println("javab dorost bud...");
					score = Integer.toString((Integer.parseInt(score) + 1));
					System.out.println("score is .... " + score);
				}

				i++;
			}
			selectedValue = null;
			String address = "/" + "quiz1.xhtml?id=" + quiz.getQzId();
			if (i == numOfQuestions - 1 || time == 40) {
				i = 0;
				score = "0";
				time = 0;
				address = "/" + "result.xhtml?id=" + quiz.getQzId();
				sendMail();
			}
			try {

				ec.redirect(ec.getRequestContextPath() + address);

			} catch (IOException e) { // TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public String sendMail() {

		String message = "سلام " + "\r\n";
		message += "دوستتان شما را به چالش کوییزآپ دعوت کرده";
		message += "\r\n" + "کلیک کنید" + "\r\n";
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		message += "localhost:8080/" + ec.getRequestContextPath() + "/" + "quiz1.xhtml?id=" + quizId;
		System.out.println(message);
		RegistrationListener.sendMailQuiz(opponent, message, "KooisUp invitation");

		return "th";
	}

	public String getOpponent() {
		return opponent;
	}

	public void setOpponent(String opponent) {
		this.opponent = opponent;
	}

	public void increment() {
        time++;
        if(time == 40){
        	ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        	try {
				ec.redirect(ec.getRequestContextPath() + "/" + "result.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }
	
	public void opponentStart(){
		System.out.println("opponent accepts...");
	}


}

