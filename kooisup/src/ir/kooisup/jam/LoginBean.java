package ir.kooisup.jam;

import java.util.Map;

import javax.annotation.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@ManagedBean
@SessionScoped
public class LoginBean {
	private String password;
	private String username;
	private String uuid;

	private UIComponent loginButton;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		System.out.println("miam tush");
		this.uuid = uuid;
	}

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

	public String doLogin() {

		HttpServletRequest request=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();

		String value=request.getParameter("code");
		System.out.println("uuid is++++++++++:" + value);
		
		System.out.println("uuid is:" + uuid);
		if (username.equals("admin") && password.equals("admin")) {
			// System.out.println("salam");
			// RegistrationListener.sendMail();
			HttpSession hs = Util.getSession();
			hs.setAttribute("username", username);
			return "/WebPage/home.xhtml";
		} else {

			// FacesMessage message = new FacesMessage("Invalid password
			// length");
			// FacesContext context = FacesContext.getCurrentInstance();
			// context.addMessage(loginButton.getClientId(context), message);
			FacesContext.getCurrentInstance().addMessage("myForm:loginButton",
					new FacesMessage("اطلاعات ورودی صحیح نیست"));
			return "th";
		}
	}

	public String doLogout() {
		System.out.println("logOut");
		HttpSession hs = Util.getSession();
		hs.invalidate();
		return "/index.xhtml";
	}

	public String test() {
		System.out.println("logOutljdgkjfsbgnljsnglksdngksdnglkds");

		return "/index.xhtml";
	}
	
	   public void onload() {
		   
		      System.out.println(uuid);

		    }

}
