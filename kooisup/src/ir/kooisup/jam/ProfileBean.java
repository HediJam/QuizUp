package ir.kooisup.jam;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class ProfileBean {

	public ProfileBean() {
		// TODO Auto-generated constructor stub
	}
	
	private String name = "a";
	private String sex = "a";
	private String country = "a";
	private String username="a";
	private String maxWin="a";
	private String maxWinPeyDarPey = "a";
	private String sumScore="a";
	private String maxScore="a";
	private String mail="a";
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getMaxWin() {
		return maxWin;
	}
	public void setMaxWin(String maxWin) {
		this.maxWin = maxWin;
	}
	public String getMaxWinPeyDarPey() {
		return maxWinPeyDarPey;
	}
	public void setMaxWinPeyDarPey(String maxWinPeyDarPey) {
		this.maxWinPeyDarPey = maxWinPeyDarPey;
	}
	public String getSumScore() {
		return sumScore;
	}
	public void setSumScore(String sumScore) {
		this.sumScore = sumScore;
	}
	public String getMaxScore() {
		return maxScore;
	}
	public void setMaxScore(String maxScore) {
		this.maxScore = maxScore;
	}
	

}
