package Server;

import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.smtp.SMTPTransport;

import Common.EmailMessage;

public class EmailService {
	private static EmailService _ins;
	 private  String SMTP_SERVER;
	 private  String USERNAME;
	 private  String PASSWORD;
	 private  String FROM;


	private EmailService(){
		SMTP_SERVER = "in-v3.mailjet.com";
		USERNAME = "d52f1ee84cd7e6e0650b269ff04aa6e4";
		PASSWORD = "3589f2867f75be0dddf1b4545a6192f7";
		FROM = "icm2020g02@gmail.com";

	}

	public void sendEmail(EmailMessage mail)
	{
		Properties prop = System.getProperties();
        prop.put("mail.smtp.host", SMTP_SERVER); //optional, defined in SMTPTransport
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.port", "25"); // default port 25
        Session session = Session.getInstance(prop, null);
        Message msg = new MimeMessage(session);
        try {

			// from
            msg.setFrom(new InternetAddress(FROM));

			// to
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(mail.getReciever(), false));

			// cc
            msg.setRecipients(Message.RecipientType.CC,
                    InternetAddress.parse(mail.getCC(), false));

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





	public static EmailService getInstannce(){
		if (_ins==null)
			_ins=new EmailService();
		return _ins;
	}


}
