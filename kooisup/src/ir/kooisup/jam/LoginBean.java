package ir.kooisup.jam;

import javax.annotation.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@ManagedBean
@SessionScoped
public class LoginBean {
	private String password;
	private String username;
	private String exit ="none";


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	public String doLogin() {

		HttpServletRequest request=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();

		DBHandler db = DBHandler.getInstance();
		if(db.existConfirmedUser(username,password)){
			HttpSession hs = Util.getSession();
			hs.setAttribute("username", username);
			exit="";
			//return "quizup/index.xhtml";
			//return "th";
			return "http://localhost:8080/quizup/index.xhtml";
		} else {
			FacesContext.getCurrentInstance().addMessage("myForm:loginButton",
					new FacesMessage("اطلاعات ورودی صحیح نیست"));
			return "th";
		}
	}

	public String doLogout() {
		System.out.println("logOut");
		HttpSession hs = Util.getSession();
		hs.invalidate();
		exit="none";
		return "/index.xhtml";
	}

	public String getExit(){
		return exit;
	}
	
	public String test() {
		System.out.println("logOutljdgkjfsbgnljsnglksdngksdnglkds");

		return "/index.xhtml";
	}

}
