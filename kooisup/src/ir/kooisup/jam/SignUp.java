package ir.kooisup.jam;

import java.util.UUID;

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
		
		try {
			
			DBHandler db = DBHandler.getInstance();
			if(!db.isUniqueUsername(username)){
				FacesContext.getCurrentInstance().addMessage("registeration:signupButton", new FacesMessage("نام کاربری تکراری"));
				return "chert";
			}
			if(!db.isUniqueEmail(email)){
				FacesContext.getCurrentInstance().addMessage("registeration:signupButton", new FacesMessage("ایمیل تکراری"));
				return "chert";
			}
			if (captcha.validate(captchaCode)){
				String uuid = UUID.randomUUID().toString();
				RegistrationListener.sendMail(email, uuid);
				User u = new User(username, password, email, gender, country, uuid);
				db.insertUser(u);
				FacesContext.getCurrentInstance().addMessage("registeration:signupButton", new FacesMessage("با مراجعه به ایمیل خود ثبت نامتان را نهایی کنید"));
			}
			else{
				FacesContext.getCurrentInstance().addMessage("registeration:signupButton", new FacesMessage("عبارت داخل تصویر درست وارد نشده است"));
				return "chert";
			}
			
		}catch(Exception e){
			System.err.println("error in DB connection");
		}
		
		
		//connect to DB
	/*	RegistrationListener.sendMail("hedieh_jam@live.com");
		RegistrationListener.sendMail("sjfjsa");
		String uuid = RegistrationListener.uuid; 
		User u = new User(name, password, email,"ssaf","asdff","hghgh");
		System.out.println(name+password);
		try {
			System.out.println(name);
			//System.out.println(u.getName());
			//DBHandler.getInstance().insertUser(u);
			//System.out.println("djsnfjsd" + DBHandler.getInstance().existUser(name, password));
		}catch(Exception e){
			
		}
		System.out.println("horaaaaaaaaaaaa");
		/*if (captcha.validate(captchaCode)){
			try {
				DBHandler.getInstance().insertUser(u);
			} catch (Exception e) {
				if (e.getMessage().equals("code1")){
		            FacesContext.getCurrentInstance().addMessage(
		                    null,
		                    new FacesMessage(FacesMessage.SEVERITY_WARN,
		                            "ایمیل وارد شده قبلا در سیستم ثبت شده است.",
		                            "Please enter correct username and Password"));
				}
				else if (e.getMessage().equals("code2")){
		            FacesContext.getCurrentInstance().addMessage(
		                    null,
		                    new FacesMessage(FacesMessage.SEVERITY_WARN,
		                            "نام کاربری قبلا در سیستم ثبت شده است.",
		                            "Please enter correct username and Password"));
				}
				else {
					
				}
			}
			return "admin";
		}*/
		return "index";
	}
}
