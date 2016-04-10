package ir.kooisup.jam;

import javax.annotation.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean
@SessionScoped
public class LoginBean {
	
	private UIComponent loginButton;
	private String password;
	private UIComponent loginButton1;
	public UIComponent getLoginButton1() {
		return loginButton1;
	}
	public void setLoginButton1(UIComponent loginButton1) {
		this.loginButton1 = loginButton1;
	}

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
	
	public UIComponent getLoginButton() {
		return loginButton;
	}
	public void setLoginButton(UIComponent loginButton) {
		this.loginButton = loginButton;
	}	
	public String doLogin(){
		if(username.equals("admin")&& password.equals("admin")){
			HttpSession hs = Util.getSession();
			hs.setAttribute("username", username);
			return "th";
		}
		else{
			
			FacesMessage message = new FacesMessage("Invalid password length");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(loginButton.getClientId(context), message);
			return "th";
		}
	}

	public String doLogout(){
		HttpSession hs = Util.getSession();
		hs.invalidate();
		return "/login.xhtml";
	}

}
