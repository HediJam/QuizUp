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
public class Quiz1 {

	static String winner = "d";
	static String myScore = "d";
	static String oppScore;
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
	static String opponent;

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

		/*
		 * HttpSession hs = Util.getSession(); if (hs == null ||
		 * hs.getAttribute("username") == null) { System.out.println(
		 * "hs is null........" + quizId); ExternalContext ec =
		 * FacesContext.getCurrentInstance().getExternalContext(); String
		 * address = ec.getRequestContextPath() + "/" + "login.xhtml"; try {
		 * ec.redirect(address); } catch (IOException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); } // return
		 * "flat-login-form/index.xhtml?quizId=" + quizId; }
		 */

		System.out.println("on load  " + quizId);
		System.out.println("on load " + category);
		System.out.println("-----------------------------on load " + opponent);

		// permission();
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
		db.updateQuiz(quiz.getQzId(), username, Integer.valueOf(score), 40);
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
			if (i == numOfQuestions - 1) {
				
				address = "/" + "offlineResult.xhtml?id=" + quiz.getQzId();
				DBHandler db = DBHandler.getInstance();
				HttpSession hs = Util.getSession();
				String uid = hs.getAttribute("username").toString();
				db.updateQuiz(quiz.getQzId(), uid, Integer.parseInt(score), time);
				
				quiz = db.findQuiz(quiz.getQzId());
				System.out.println("&&&&&&&&&&&&&&&&&&&&& usernam name " + quiz.numPlayed());

				time = -100;
				if (quiz.numPlayed() == 1) {
					myScore = Integer.toString(quiz.getScore1());
					oppScore = "نامعلوم";
					winner = "نامعلوم";
					System.out.println("&&&&&&&&&&&&&&&nafar avalam ke bazi mikone");
					sendMail();
					System.out.println("after sending mail......");
				} else if (quiz.numPlayed() == 2) {
					myScore = Integer.toString(quiz.getScore2());
					oppScore = Integer.toString(quiz.getScore1());
					winner = db.getWinner(quiz).getUsername();
					System.out.println("&&&&&&&&&&&&&&&&&&&&nafar 2 ke bazi mikone");
					sendMailResult();
					System.out.println("after sending result mail......");
				}
				
				
			}
			try {

				ec.redirect(ec.getRequestContextPath() + address);

			} catch (IOException e) { // TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public String getMyScore() {
		return myScore;
	}

	public void setMyScore(String myScore) {
		this.myScore = myScore;
	}

	public String getOppScore() {
		return oppScore;
	}

	public void setOppScore(String oppScore) {
		this.oppScore = oppScore;
	}

	public String sendMail() {

		String message = "سلام " + "\r\n";
		message += "دوستتان شما را به چالش کوییزآپ دعوت کرده";
		message += "\r\n" + "کلیک کنید" + "\r\n";
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		message += "localhost:8080/" + ec.getRequestContextPath() + "/" + "quiz1.xhtml?id=" + quiz.getQzId();
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
		if (time > 40 || time < 0) {
			System.out.println("tims is ------------ : " + time);
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			reset();
			try {
				ec.redirect(ec.getRequestContextPath() + "/" + "offlineResult.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void opponentStart() {
		System.out.println("opponent accepts...");
	}

	public String getWinner() {
		return winner;
	}

	public void setWinner(String winner) {
		this.winner = winner;
	}

	public String sendMailResult() {

		DBHandler db = DBHandler.getInstance();
		String oppMail = db.getEmailUsers(quiz).get(0);
		String message = "سلام " + "\r\n";
		message += "نتیجه بازی به شرح زیر است";
		message += "\r\n" + "امتیاز شما = " + quiz.getScore1() + "\r\n";
		message += "\r\n" + "امتیاز حریف = " + quiz.getScore2() + "\r\n";
		message += "\r\n" + "برنده نهایی = " + winner + "\r\n";
		System.out.println(message);
		RegistrationListener.sendMailQuiz(oppMail, message, "KooisUp result");

		return "th";
	}

	private void reset() {
		time = 0;
		score = "0";
		i = 0;
		opponent = "";
	}
	

}
