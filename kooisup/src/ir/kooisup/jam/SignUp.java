package ir.kooisup.jam;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import botdetect.web.jsf.JsfCaptcha;
@ManagedBean
@SessionScoped
public class SignUp {
	public SignUp(){}
	

	private String username;
	private String password;
	private String rePassword;
	private String email;
	private String gender;
	private String country;
	private JsfCaptcha captcha;
	private String captchaCode;
	private String confirmationCode;
	
	public String getRePassword() {
		return rePassword;
	}

	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String signItUp(){
		
		
		System.out.println("---------------------------");
		System.out.println(captchaCode);
		System.out.println(country);
		System.out.println(gender);
		System.out.println(username);
		System.out.println(email);
		System.out.println("pass : " + password);
		System.out.println("rePass :" + rePassword);
		
		try {
			
			DBHandler db = DBHandler.getInstance();
			if(!db.isUniqueUsername(username)){
				FacesContext.getCurrentInstance().addMessage("registeration:signupButton", new FacesMessage("نام کاربری تکراری"));
				return "#";
			}
			Pattern pattern;
			Matcher matcher;
			String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
			pattern = Pattern.compile(EMAIL_PATTERN);
			matcher = pattern.matcher(email);
			if(!matcher.matches()){
				FacesContext.getCurrentInstance().addMessage("registeration:signupButton", new FacesMessage("ایمیل نامعتبر"));
				return "#";
			}
			
			if(!db.isUniqueEmail(email)){
				FacesContext.getCurrentInstance().addMessage("registeration:signupButton", new FacesMessage("ایمیل تکراری"));
				return "#";
			}
			if(!password.equals(rePassword)){
				FacesContext.getCurrentInstance().addMessage("registeration:signupButton", new FacesMessage("تکرار رمز درست نیست"));
				return "#";
			}
			//if (captcha.validate(captchaCode)){
			if(true){
				String uuid = UUID.randomUUID().toString();
				RegistrationListener.sendMail(email, uuid);
				User u = new User(username, password, email, gender, country, uuid);
				db.insertUser(u);
				FacesContext.getCurrentInstance().addMessage("registeration:signupButton", new FacesMessage("با مراجعه به ایمیل خود ثبت نامتان را نهایی کنید"));
				return "#";
			}
			else{
				FacesContext.getCurrentInstance().addMessage("registeration:signupButton", new FacesMessage("عبارت داخل تصویر درست وارد نشده است"));
				return "#";
			}
			
		}catch(Exception e){
			System.err.println("error in DB connection");
		}
		
		return "index";
	}
	
	public void antiBabak(){
		System.out.println("az hamin tiribun elam mikonam ridi :|");
	}
}
