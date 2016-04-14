package ir.kooisup.jam;

public class User {
	
	public User(String name, String password, String email, String gender, String country){
		this.name = name;
		this.password = password;
		this.email = email;
		this.gender = gender;
		this.country = country;
	}
	
	private String name;
	private String password;
	private String email;
	private String gender;
	private String country;
	
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
}
