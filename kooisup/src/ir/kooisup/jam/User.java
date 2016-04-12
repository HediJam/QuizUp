package ir.kooisup.jam;
public class User {
	
	public User(String name, String password, String email, String gender, String country,String uuid){
		this.name = name;
		this.password = password;
		this.email = email;
		this.gender = gender;
		this.country = country;
		this.confirmationCode = uuid;
	}
	
	private String name;
	private String password;
	private String email;
	private String gender;
	private String country;
	private String confirmationCode;
	private boolean mailConfirmed;
	
	public String getName() {
		return name;
	}
	public String getPassword() {
		return password;
	}
	public String getEmail() {
		return email;
	}
	public String getGender() {
		return gender;
	}
	public String getCountry() {
		return country;
	}
	public String getConfirmationCode() {
		return confirmationCode;
	}
	public boolean isMailConfirmed() {
		return mailConfirmed;
	}
	
}