
package ir.kooisup.jam;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
@ManagedBean
@SessionScoped
public class QuizStarter {

	public QuizStarter() {
		// TODO Auto-generated constructor stub
	}

	public String opponentAccept(){
		System.out.println("opponet accept...");
		return "quiz1.xhtml";
	}
	public String opponentDecline(){
		System.out.println("opponet decline...");
		return "index.xhtml";
	}
	
	public void pollingQuiz(){
		// if anyRequest()!= -1
		if(true){
			//return "invitation.xhtml";
			System.out.println("go to invitation");
		}
		
		
	}
}
