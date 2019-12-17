package Common;

import java.io.Serializable;

public class Message implements Serializable {

	private static final long serialVersionUID = 1L;

	private int type;
	private String title;
	private String details;
	private ISUser receiver;
	private Request request;

	public Message(int type, String Title, String Details, ISUser Receiver, Request request) {
		this.type=type;
		this.title=Title;
		this.details=Details;
		this.receiver=Receiver;
		this.setRequest(request);
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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

	public ISUser getReceiver() {
		return receiver;
	}

	public void setReceiver(ISUser receiver) {
		this.receiver = receiver;
	}

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	enum SystemEnum {
		SQLCMD;
		public static SystemEnum getByInt(int i) {
			switch (i) {
			case 0:
				return SQLCMD;
			}
			return null;
		}
	}
}
