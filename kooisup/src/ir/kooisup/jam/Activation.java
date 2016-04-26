package ir.kooisup.jam;

import javax.annotation.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class Activation {

	private String code;
	private String mail;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public void onload() {
		System.out.println("activation code is" + code);
		System.out.println("activation mail is" + mail);
		if (code != null && mail != null) {
			DBHandler db = DBHandler.getInstance();
			db.confirmEmail(mail,code);
			System.out.println("active shod!");
		}
	}

}
