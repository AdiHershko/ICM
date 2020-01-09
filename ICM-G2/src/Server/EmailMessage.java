package Server;

import java.util.ArrayList;

import org.joda.time.DateTime;

import Common.Enums;
import Common.Request;
import Common.Stage;
import Common.User;

// TODO: Auto-generated Javadoc
/**
 * The Class EmailMessage.
 */
public class EmailMessage {
	
	/** The subject. */
	private String subject;
	
	/** The body. */
	private String body;
	
	/** The reciever. */
	private String reciever;
	
	/** The cc. */
	private ArrayList<String> CC;
	
	/** The r. */
	private Request r;
	
	/** The receiver user. */
	private User receiverUser;

	/**
	 * Instantiates a new email message.
	 *
	 * @param r the r
	 * @param receiverUser the receiver user
	 */
	public EmailMessage(Request r, User receiverUser) {
		this.r = r;
		this.receiverUser = receiverUser;
		CC = new ArrayList<String>();
	}

	/**
	 * Gets the subject.
	 *
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * Sets the subject.
	 *
	 * @param subject the new subject
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * Gets the body.
	 *
	 * @return the body
	 */
	public String getBody() {
		return body;
	}

	/**
	 * Sets the body.
	 *
	 * @param body the new body
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * Gets the reciever.
	 *
	 * @return the reciever
	 */
	public String getReciever() {
		return reciever;
	}

	/**
	 * Sets the reciever.
	 *
	 * @param reciever the new reciever
	 */
	public void setReciever(String reciever) {
		this.reciever = reciever;
	}

	/**
	 * Gets the cc.
	 *
	 * @return the cc
	 */
	public ArrayList<String> getCC() {
		return CC;
	}

	/**
	 * Sets the cc.
	 *
	 * @param cC the new cc
	 */
	public void setCC(ArrayList<String> cC) {
		CC = cC;
	}

	/**
	 * Adds the to CC.
	 *
	 * @param toAdd the to add
	 */
	public void addToCC(String toAdd) {
		CC.add(toAdd);
	}

	/**
	 * Gets the request.
	 *
	 * @return the request
	 */
	public Request getRequest() {
		return r;
	}

	/**
	 * Sets the request.
	 *
	 * @param r the new request
	 */
	public void setRequest(Request r) {
		this.r = r;
	}

	/**
	 * Gets the receiver user.
	 *
	 * @return the receiver user
	 */
	public User getReceiverUser() {
		return receiverUser;
	}

	/**
	 * Sets the receiver user.
	 *
	 * @param receiverUser the new receiver user
	 */
	public void setReceiverUser(User receiverUser) {
		this.receiverUser = receiverUser;
	}

	/**
	 * Builds the 24 h stage msg.
	 */
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

	/**
	 * Builds the exception msg.
	 */
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

	/**
	 * Builds the extension msg.
	 */
	public void buildExtensionMsg() {
		int currstageNum = Enums.RequestStageENUM.getRequestStageENUMByEnum(r.getCurrentStage());
		Stage currentStage = r.getStages()[currstageNum];
		String askedBy = currentStage.getStageMembers().get(1);

		reciever = receiverUser.getMail();
		subject = "Request " + r.getId() + ", stage " + r.getCurrentStage() + " - Extension Asked";
		body = "Hello " + receiverUser.getFirstName() + " " + receiverUser.getLastName() + ",\n";
		body += "In request " + r.getId() + ", stage " + r.getCurrentStage() + " an extension was asked by " + askedBy
				+ ".\n";
		body += "Original due date: " + new DateTime(currentStage.getPlannedDueDate()).toString("dd/MM/yyyy") + "\n";
		body += "Asked exteneded due date: " + currentStage.getExtendedDueDate() + "\n";
		body += "Extension reason:\n" + currentStage.getExtensionAsk();
		body += "\nCopy sent to the manager.\n";
		body += "\nThanks,\nICM, Group 2";
	}

	/**
	 * Builds the closing msg.
	 */
	public void buildClosingMsg() {
		reciever = receiverUser.getMail();
		subject = "Request " + r.getId() + " - Closing";
		body = "Hello " + receiverUser.getFirstName() + " " + receiverUser.getLastName();
		if (r.getStatus() == Enums.RequestStatus.Rejected) {
			body += ",\nYour request (request ID: " + r.getId() + ") was rejected and closed.\n";
		} else {
			body += ",\nYour request (request ID: " + r.getId() + ") was closed.\n";
		}
		body += "You can enter the ICM system in order to look at the process, and contact our supervisor (CC-ed to this mail) for more information.\n";
		body += "\nThanks,\nICM, Group 2";
	}
}
