package Common;

import java.io.Serializable;

public class Message implements Serializable {

	private static final long serialVersionUID = 1L;

	private String title;
	private String details;
	private String receiver;
	private Request request;
	

	public Message(int type, String Title, String Details, String Receiver, Request request) {
		this.title=Title;
		this.details=Details;
		this.receiver=Receiver;
		this.setRequest(request);
	
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

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

}
