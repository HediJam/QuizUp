package ir.kooisup.jam;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class ResultBean {

	String quizId;
	private String opponent;

	public String getQuizId() {
		return quizId;
	}

	public void setQuizId(String quizId) {
		System.out.println("miam into");
		this.quizId = quizId;
	}

	public String getOpponent() {
		System.out.println("@@@@@@@@@@@@@@@@");
		return opponent;
	}

	public void setOpponent(String opp) {
		System.out.println("********************");
		System.out.println(opp);
		System.out.println("********************");
		this.opponent = opp;
	}

	public ResultBean() {
		// TODO Auto-generated constructor stub
	}

	public void start(String category) throws IOException{
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.redirect("http://localhost:8080/quizup/quiz1.xhtml?category="+category+"&opponent="+opponent);
	}
	
	public String sendMail() {
		
		String message = "سلام " + "\r\n";
		message += "دوستتان شما را به چالش کوییزآپ دعوت کرده";
		message += "\r\n" + "کلیک کنید" + "\r\n";
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		message += "localhost:8080/"+ ec.getRequestContextPath() + "/" + "quiz.xhtml?id=" + quizId;
		System.out.println(message);
		RegistrationListener.sendMailQuiz(opponent, message,"KooisUp invitation");
		
		return "th";
	}

}
