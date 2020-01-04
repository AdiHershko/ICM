package Server;

import org.joda.time.DateTime;

import Common.Enums;
import Common.Request;
import Common.User;
import Common.Enums.RequestStageENUM;

public class EmailMessage {
	private String subject;
	private String body;
	private String reciever;
	private String CC;
	private Request r;
	private User receiverUser;

	public EmailMessage(Request r, User receiverUser) {
		this.r = r;
		this.receiverUser = receiverUser;
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

	public Request getRequest() {
		return r;
	}

	public void setRequest(Request r) {
		this.r = r;
	}

	public User getReceiverUser() {
		return receiverUser;
	}

	public void setReceiverUser(User receiverUser) {
		this.receiverUser = receiverUser;
	}

	public void build24hStageMsg() {
		reciever = receiverUser.getMail();
		subject = "Request " + r.getId() + ", stage " + r.getCurrentStage() + " - 24h from due date";
		body = "Hello " + receiverUser.getFirstName() + " " + receiverUser.getLastName()
				+ ",\n You are the stage leader of request " + r.getId() + ", stage " + r.getCurrentStage() + ".\n";
		body += "The due date of this stage is ";
		body += new DateTime(r.getStages()[Enums.RequestStageENUM.getRequestStageENUMByEnum(r.getCurrentStage())]
				.getPlannedDueDate()).toString("dd/MM/yyyy");
		body += ", which is less than a day away.\n";
		body += "\nThanks,\nICM, Group 2";
	}
}
