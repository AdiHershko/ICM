package Common;

import java.io.Serializable;

public class Message implements Serializable {

	private static final long serialVersionUID = -1650275113508444918L;
	private int requestID;
	private String title;
	private String details;
	private String reciever;

	public Message(int requestID, String title, String details, String reciever) {
		this.requestID = requestID;
		this.title = title;
		this.details = details;
		this.reciever = reciever;
	}

	public int getRequestID() {
		return requestID;
	}

	public void setRequestID(int requestID) {
		this.requestID = requestID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getReciever() {
		return reciever;
	}

	public void setReciever(String reciever) {
		this.reciever = reciever;
	}

}
