package ir.kooisup.jam;
import java.util.Properties;
import java.util.UUID;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class RegistrationListener {
	
	static String uuid;

	public String getUuid() {
		return uuid;
	}

	public static void sendMail(String mail) {

		final String username = "kooisup.ir@gmail.com";
		final String password = "d7100game";
		

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("kooisup.ir"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(mail));
			message.setSubject("KooisUp-confirmation mail");
			uuid = UUID.randomUUID().toString();
			message.setText("Hello"
				+ "\n\n please click on " + "\n\n http://localhost:8080/quizup/flat-login-form/index.xhtml?code=" + uuid);

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}