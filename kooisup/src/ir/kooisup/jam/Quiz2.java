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
public class Quiz2 {

	//String host = "kooisup.ir/";
		String host = "localhost:8080/quizup/";
	static String winner = "نامعلوم";
	static String myScore = "نامعلوم";
	static String oppScore = "نامعلوم";
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
	boolean isSet = false;

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
		// System.out.println("miam tu quiz1");
		this.quizId = quizId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Quiz2() {
		// TODO Auto-generated constructor stub
		// System.out.println("constructor quiz1 " + quizId);
		// System.out.println("constructor " + category);
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

	public String getOpponent() {
		return opponent;
	}

	public void setOpponent(String opponent) {
		this.opponent = opponent;
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
	private void reset() {
		time = 0;
		score = "0";
		i = 0;
		opponent = "";
	}

	/////////////////////////////

	public String onloadOnline() {
		System.out.println("on load online2 " + quizId);
		DBHandler db = DBHandler.getInstance();
		if (quizId == null) {
			System.out.println("quiz2: quiz id bayad moshakas bashad");

		} else {
			System.out.println("quiz2 : quiz ghadimi ba id ro load konam" + quizId);
			quiz = db.findQuiz(Integer.valueOf(quizId));

			questions = db.loadQuestions(quiz);
			curQuestion = questions.get(i).getText();
			options = questions.get(i).choices;
			Collections.sort(options);
			option1 = options.get(0);
			option2 = options.get(1);
			option3 = options.get(2);
			option4 = options.get(3);
			System.out.println("quiz2 : size qustion is " + questions.size());
		}
		return "th";
	}


	public void ajaxSubmitOnline(AjaxBehaviorEvent event) {

		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		System.out.println("chnage quiz2----");
		if (i < numOfQuestions) {
			//System.out.println("changing event..." + i);
			//System.out.println("selected value is :   " + selectedValue);
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
			String address = "/" + "quiz3.xhtml?id=" + quiz.getQzId();
			if (i == numOfQuestions - 1 || hasFinished()) {

				address = "/" + "offlineResult.xhtml?id=" + quiz.getQzId();
				onlineFinish();

			}
			try {

				ec.redirect(ec.getRequestContextPath() + address);

			} catch (IOException e) { // TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void onlineFinish() {
		DBHandler db = DBHandler.getInstance();
		HttpSession hs = Util.getSession();
		String uid = hs.getAttribute("username").toString();
		db.Ifinished(quiz.getQzId(), uid, time, Integer.parseInt(score));
		quiz = db.findQuiz(quiz.getQzId());
		System.out.println("quiz2: online finish " + uid + " score: " +  score);
		while (!quiz.hasOponentFinished(uid)){
			quiz = db.findQuiz(quiz.getQzId());
		}
		myScore = Integer.toString(quiz.getScore2());
		oppScore = Integer.toString(quiz.getScore1());
		winner = db.getWinner(quiz).getUsername();
		System.out.println("qiz2******har 2 nafar bazi ra tamam kardan");
		reset();
		//time = -100;
	}

	public void onlineIncrement() {
		time++;
		if ((time > 40 || time < 0) || hasFinished()) {
			onlineFinish();
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			String address = "/" + "offlineResult.xhtml?id=" + quiz.getQzId();
			try {
				ec.redirect(ec.getRequestContextPath() + address);
				isSet = false;

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	private boolean hasFinished(){
		
		DBHandler db = DBHandler.getInstance();
		HttpSession hs = Util.getSession();
		String uid = hs.getAttribute("username").toString();
		quiz = db.findQuiz(quiz.getQzId());
		return quiz.hasOponentFinished(uid);
		
	}

}
