package ir.kooisup.jam;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class ResultBean {

	String quizId;
	String opponent;

	public String getQuizId() {
		return quizId;
	}

	public void setQuizId(String quizId) {
		System.out.println("miam into");
		this.quizId = quizId;
	}

	public String getOpponent() {
		return opponent;
	}

	public void setOpponent(String opponent) {
		this.opponent = opponent;
	}

	public ResultBean() {
		// TODO Auto-generated constructor stub
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
	
	public void antiBabak(){
		System.out.println("az hamin tiribun elam mikonam ridi :|");
	}
}
