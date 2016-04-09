package ir.kooisup.jam;

import javax.annotation.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean
@SessionScoped
public class LoginBean {
	
	private String password;
	private String username;
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
	
	public String doLogin(){
		if(username.equals("admin")&& password.equals("admin")){
			HttpSession hs = Util.getSession();
			hs.setAttribute("username", username);
			RegistrationListener.sendMail();
			return "WebPage/home.xhtml";
		}
		else{
			FacesMessage fm = new FacesMessage("Login Error","Error MSG");
			fm.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, fm);
			return "login.xhtml";
		}
	}
	
	public String doLogout(){
		HttpSession hs = Util.getSession();
		hs.invalidate();
		return "/login.xhtml";
	}

}
