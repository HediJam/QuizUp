
package ir.kooisup.jam;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
@ManagedBean
@SessionScoped
public class QuizStarter {

	String quizId;
	public QuizStarter() {
		// TODO Auto-generated constructor stub
	}

	public void opponentAccept(){
		System.out.println("opponet accept... " + quizId);
		DBHandler db = DBHandler.getInstance();
		HttpSession hs = Util.getSession();
		db.acceptRequest(Integer.valueOf(quizId), (String) hs.getAttribute("username"));
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		try {
			System.out.println("ghable az redirect be quiz3");
			ec.redirect(ec.getRequestContextPath() +  "/quiz3.xhtml?id=" + quizId);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//return "quiz3.xhtml?id="+quizId;
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
