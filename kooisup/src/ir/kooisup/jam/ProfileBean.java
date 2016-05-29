package ir.kooisup.jam;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpSession;

@ManagedBean
@SessionScoped
public class ProfileBean {

	public ProfileBean() {
		db = DBHandler.getInstance();
		HttpSession hs = Util.getSession();
		username = hs.getAttribute("username").toString();
		usr =  db.findUser(username);
		setCountry();
		setMail();
		setSex();

	}
	
	private User usr;
	private DBHandler db;
	private String name = "a";
	private String sex = "a";
	private String country = "a";
	private String username="a";
	private String maxWin;
	private String maxWinPeyDarPey = "a";
	private String sumScore;
	private String maxScore;
	private String mail="a";
	public String getMail() {
		return mail;
	}
	public void setMail() {
		this.mail = usr.getEmail();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		db.findUser(username);
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex() {
		this.sex = usr.getGender();
	}
	public String getCountry() {
		return country;
	}
	public void setCountry() {
		this.country = usr.getCountry();
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getMaxWin() {
		int i = db.numOfWin(username);
		return Integer.toString(i);
	}
	public void setMaxWin(String maxWin) {
		this.maxWin = maxWin;
	}
	public String getMaxWinPeyDarPey() {
		int i = db.numOfStronWin(username);
		return Integer.toString(i);
	}
	public void setMaxWinPeyDarPey(String maxWinPeyDarPey) {
		
		this.maxWinPeyDarPey = maxWinPeyDarPey;
	}
	public String getSumScore() {
		int i = db.sumScore(username);
		return Integer.toString(i);
	}
	public void setSumScore(String sumScore) {
		this.sumScore = sumScore;
	}
	public String getMaxScore() {
		int i = db.maxScore(username);
		return Integer.toString(i);
	}
	public void setMaxScore(String maxScore) {
		this.maxScore = maxScore;
	}
	

}
