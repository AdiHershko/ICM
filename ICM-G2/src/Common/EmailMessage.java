package Common;

import java.util.List;

public class EmailMessage {
	private String subject;
	private String body;
	private String reciever;
	private String from;
	private String CC;


	public EmailMessage(String subject,String body,String reciever,String CC)
	{
		this.subject=subject;
		this.body=body;
		this.reciever=reciever;
		this.CC=CC;
	}


	public String getSubject() {
		return subject;
	}


	public void setSubject(String subject) {
		this.subject = subject;
	}


	public String getBody() {
		return body;
	}


	public void setBody(String body) {
		this.body = body;
	}


	public String getReciever() {
		return reciever;
	}


	public void setReciever(String reciever) {
		this.reciever = reciever;
	}



	public String getCC() {
		return CC;
	}


	public void setCC(String cC) {
		CC = cC;
	}


}
