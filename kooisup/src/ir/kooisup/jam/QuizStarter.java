
package ir.kooisup.jam;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpSession;
@ManagedBean
@SessionScoped
public class QuizStarter {

	String quizId;
	public QuizStarter() {
		// TODO Auto-generated constructor stub
	}

	public String opponentAccept(){
		System.out.println("opponet accept... " + quizId);
		DBHandler db = DBHandler.getInstance();
		HttpSession hs = Util.getSession();
		db.acceptRequest(Integer.valueOf(quizId), (String) hs.getAttribute("username"));
		return "quiz3.xhtml?id="+quizId;
		//return "index.xhtml";
	}
	public String opponentDecline(){
		System.out.println("opponet decline...");
		return "index.xhtml";
	}
	
	public String getQuizId() {
		return quizId;
	}

	public void setQuizId(String quizId) {
		this.quizId = quizId;
	}

	public void pollingQuiz(){
		// if anyRequest()!= -1
		if(true){
			//return "invitation.xhtml";
			System.out.println("go to invitation");
		}
		
		
	}
}
