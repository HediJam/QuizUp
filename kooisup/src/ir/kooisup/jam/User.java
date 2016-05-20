package ir.kooisup.jam;

public class User {

	public User(String username, String password, String email, String gender, String country, String uuid) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.gender = gender;
		this.country = country;
		this.confirmationCode = uuid;
		this.mailConfirmed = false;
	}

	private String username;
	private String password;
	private String email;
	private String gender;
	private String country;
	private String confirmationCode;
	private boolean mailConfirmed;

	public String getUsername() {
		return username;
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

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", email=" + email + ", gender=" + gender
				+ ", country=" + country + ", confirmationCode=" + confirmationCode + ", mailConfirmed=" + mailConfirmed
				+ "]";
	}

	public void antiBabak() {
		System.out.println("az hamin tiribun elam mikonam ridi :|");
	}
}
