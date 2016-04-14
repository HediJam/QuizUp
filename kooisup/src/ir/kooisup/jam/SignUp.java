package ir.kooisup.jam;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import botdetect.web.jsf.JsfCaptcha;

@ManagedBean
@SessionScoped
public class SignUp {
	public SignUp(){}
	
	private String name;
	private String password;
	private String email;
	private String gender;
	private String country;
	
	private JsfCaptcha captcha;
	private String captchaCode;
	
	public String getCaptchaCode() {
		return captchaCode;
	}

	public void setCaptcha(JsfCaptcha captcha) {
		this.captcha = captcha;
	}

	public JsfCaptcha getCaptcha() {
		return captcha;
	}

	public void setCaptchaCode(String captchaCode) {
		this.captchaCode = captchaCode;
	}
	
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
		System.out.println(gender);
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
		System.out.println(this.country);
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public String getPassword() {
		return password;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String signItUp(){
		//connect to DB
		User u = new User(name, password, email, gender, country);
		if (captcha.validate(captchaCode))
			return "admin";
		return "index";
	}
	
	public String login(){
		if (name.equals("admin") & password.equals("admin")){
            return "admin";
        } else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Incorrect name and Passowrd",
                            "Please enter correct username and Password"));
            return "login";
        }
	}
	
}
