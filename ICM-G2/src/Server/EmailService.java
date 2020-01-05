package Server;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.smtp.SMTPTransport;

import javafx.application.Platform;
import javafx.scene.image.ImageView;

public class EmailService {
	private static EmailService _ins;
	private String SMTP_SERVER;
	private String USERNAME;
	private String PASSWORD;
	private String FROM;

	private EmailService() {
		SMTP_SERVER = "smtp.gmail.com";
		USERNAME = "icm2020g02@gmail.com";
		PASSWORD = "TheBastICMTeam";
		FROM = "icm2020g02@gmail.com";
		new Thread(){
			public void run(){

			while (true)
			{
				Platform.runLater(()->{
					ServerChooseController._ins.getLoadingAnim().setVisible(true);
					ServerChooseController._ins.getSendingMessagesLabel().setVisible(true);
				});
				DataBaseController.genAutoMessages();
				ServerChooseController._ins.getLoadingAnim().setVisible(false);
				ServerChooseController._ins.getSendingMessagesLabel().setVisible(false);
				try {
					Thread.sleep(1800000);
				} catch(InterruptedException e){

				}
			}
		}
		}.start();

	}

	public void sendEmail(EmailMessage mail) {
		Properties prop = System.getProperties();
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.host", SMTP_SERVER); // optional, defined in SMTPTransport
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.port", "587"); // default port 587
		Session session = Session.getInstance(prop, null);
		Message msg = new MimeMessage(session);
		try {

			// from
			msg.setFrom(new InternetAddress(FROM));

			// to
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail.getReciever(), false));

			// CC
			for(String cc : mail.getCC())
				msg.addRecipients(Message.RecipientType.CC, InternetAddress.parse(cc, false));

			// subject
			msg.setSubject(mail.getSubject());

			// content
			msg.setText(mail.getBody());

			msg.setSentDate(new Date());

			// Get SMTPTransport
			SMTPTransport t = (SMTPTransport) session.getTransport("smtp");

			// connect
			t.connect(SMTP_SERVER, USERNAME, PASSWORD);

			// send
			t.sendMessage(msg, msg.getAllRecipients());

			System.out.println("Response: " + t.getLastServerResponse());

			t.close();

		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	public static EmailService getInstannce() {
		if (_ins == null)
			_ins = new EmailService();
		return _ins;
	}
}
