package ir.kooisup.jam;

import java.io.IOException;

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
	private String exit = "none";
	private String hide = "";
	private String admin = "none";
	private String parameter;
	ssString host = "kooisup.ir/";
	//String host = "localhost:8080/quizup/";

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		System.out.println("tu set parameter");
		this.parameter = parameter;
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

	public String doLogin() {

		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();

		DBHandler db = DBHandler.getInstance();
		if(username.equals("admin") && password.equals("admin")){
			HttpSession hs = Util.getSession();
			hs.setAttribute("username", "admin");
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			admin ="";
			hide = "none";
			try {
				//ec.redirect(ec.getRequestContextPath() + "/" + "edit_category.xhtml");
				ec.redirect(ec.getRequestContextPath() + "/" + "index.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "th";
		}
		else if (db.existConfirmedUser(username, password)) {
			HttpSession hs = Util.getSession();
			hs.setAttribute("username", username);
			/*if(username.equals("admin") && password.equals("admin"))
				admin = "";
			else
				exit = "";*/
			exit = "";
			hide = "none";
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			try {
				if (parameter == null) {
					System.out.println("parameter is null");
					ec.redirect(ec.getRequestContextPath() + "/" + "index.xhtml");
				} else {
					ec.redirect(ec.getRequestContextPath() + "/" + "quiz1.xhtml?id=" + parameter);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return host + "index.xhtml";
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
		exit = "none";
		admin = "none";
		hide = "";
		return "/index.xhtml";
	}

	public String doLogoutAdmin(){
		admin = "none";
		exit = "none";
		hide = "";
		return "/index.xhtml";
	}
	public String getExit() {
		return exit;
	}

	public String getHide() {
		return hide;
	}
	
	public String getAdmin(){
		return admin;
	}

	public String test() {
		System.out.println("logOut");
		//System.out.println("dfkgjerogjnoerjngjoerngowrngojwnrgoerngn" + parameter);
		return "/index.xhtml";
	}

}
