package Server;

import java.util.ArrayList;


import org.joda.time.DateTime;

import Common.Enums;
import Common.Request;
import Common.Stage;
import Common.User;

public class EmailMessage {
	private String subject;
	private String body;
	private String reciever;
	private ArrayList<String> CC;
	private Request r;
	private User receiverUser;

	public EmailMessage(Request r, User receiverUser) {
		this.r = r;
		this.receiverUser = receiverUser;
		CC = new ArrayList<String>();
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

	public ArrayList<String> getCC() {
		return CC;
	}

	public void setCC(ArrayList<String> cC) {
		CC = cC;
	}

	public void addToCC(String toAdd) {
		CC.add(toAdd);
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

	public void buildExceptionMsg() {
		reciever = receiverUser.getMail();
		subject = "Request " + r.getId() + ", stage " + r.getCurrentStage() + " - exception";
		body = "Hello " + receiverUser.getFirstName() + " " + receiverUser.getLastName()
				+ ",\n You are the stage leader of request " + r.getId() + ", stage " + r.getCurrentStage() + ".\n";
		body += "The due date of this stage is ";
		body += new DateTime(r.getStages()[Enums.RequestStageENUM.getRequestStageENUMByEnum(r.getCurrentStage())]
				.getPlannedDueDate()).toString("dd/MM/yyyy");
		body += ", and it already passed.\n";
		body += "Copy sent to the supervisor and manager.\n";
		body += "\nThanks,\nICM, Group 2";
	}

	public void buildExtensionMsg() {
		int currstageNum = Enums.RequestStageENUM.getRequestStageENUMByEnum(r.getCurrentStage());
		Stage currentStage = r.getStages()[currstageNum];
		String askedBy = currentStage.getStageMembers().get(1);

		reciever = receiverUser.getMail();
		subject = "Request " + r.getId() + ", stage " + r.getCurrentStage() + " - Extension Asked";
		body = "Hello " + receiverUser.getFirstName() + " " + receiverUser.getLastName() + ",\n";
		body += "In request " + r.getId() + ", stage " + r.getCurrentStage() + " an extension was asked by " + askedBy
				+ ".\n";
		body += "Original due date: "
				+ new DateTime(currentStage.getPlannedDueDate()).toString("dd/MM/yyyy")
				+ "\n";
		body += "Asked exteneded due date: "
				+ currentStage.getExtendedDueDate()
				+ "\n";
		body += "Extension reason:\n"+currentStage.getExtensionAsk();
		body += "\nCopy sent to the manager.\n";
		body += "\nThanks,\nICM, Group 2";
	}
	
	public void buildClosingMsg() {
		reciever = receiverUser.getMail();
		subject = "Request " + r.getId() + " - Closing";
		body = "Hello " + receiverUser.getFirstName() + " " + receiverUser.getLastName();
		if (r.getStatus() == Enums.RequestStatus.Rejected) {
			body += ",\nYour request (request ID: "+ r.getId()+") was rejected and closed.\n";
		}
		else {
			body += ",\nYour request (request ID: "+ r.getId()+") was closed.\n";
		}
		body += "You can enter the ICM system in order to look at the process, and contact our supervisor (CC-ed to this mail) for more information.\n";
		body += "\nThanks,\nICM, Group 2";
	}
}
