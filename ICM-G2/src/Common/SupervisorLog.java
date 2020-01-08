package Common;

import java.io.Serializable;



public class SupervisorLog implements Serializable {

	private static final long serialVersionUID = 6795167052444112778L;
	private int requestID;
	private String date;
	private String field;
	private String whatChange;

	public SupervisorLog(int requestID, String date, String field, String whatChange) {

		this.requestID = requestID;
		this.date = date;
		this.field = field;
		this.whatChange = whatChange;
	}

	public int getRequestID() {
		return requestID;
	}

	public void setRequestID(int requestID) {
		this.requestID = requestID;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getWhatChange() {
		return whatChange;
	}

	public void setWhatChange(String whatChange) {
		this.whatChange = whatChange;
	}

}
