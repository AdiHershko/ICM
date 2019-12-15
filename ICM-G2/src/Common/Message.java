package Common;

import java.io.Serializable;

public class Message implements Serializable {

	private static final long serialVersionUID = 1L;

	private int type;
	private String Title;
	private String Details;
	private ISUser Receiver;
	private Request request;
	
	public Message(int type, String Title, String Details, ISUser Receiver, Request request) {
		this.type=type;
		this.Title=Title;
		this.Details=Details;
		this.Receiver=Receiver;
		this.request=request;
	}
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDetails() {
		return Details;
	}

	public void setDetails(String details) {
		Details = details;
	}

	public ISUser getReceiver() {
		return Receiver;
	}

	public void setReceiver(ISUser receiver) {
		Receiver = receiver;
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
