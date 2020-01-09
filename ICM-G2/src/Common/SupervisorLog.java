package Common;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class SupervisorLog.
 */
public class SupervisorLog implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6795167052444112778L;
	
	/** The request ID. */
	private int requestID;
	
	/** The date. */
	private String date;
	
	/** The field. */
	private String field;
	
	/** The what change. */
	private String whatChange;

	/**
	 * Instantiates a new supervisor log.
	 *
	 * @param requestID the request ID
	 * @param date the date
	 * @param field the field
	 * @param whatChange the what change
	 */
	public SupervisorLog(int requestID, String date, String field, String whatChange) {

		this.requestID = requestID;
		this.date = date;
		this.field = field;
		this.whatChange = whatChange;
	}

	/**
	 * Gets the request ID.
	 *
	 * @return the request ID
	 */
	public int getRequestID() {
		return requestID;
	}

	/**
	 * Sets the request ID.
	 *
	 * @param requestID the new request ID
	 */
	public void setRequestID(int requestID) {
		this.requestID = requestID;
	}

	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * Sets the date.
	 *
	 * @param date the new date
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * Gets the field.
	 *
	 * @return the field
	 */
	public String getField() {
		return field;
	}

	/**
	 * Sets the field.
	 *
	 * @param field the new field
	 */
	public void setField(String field) {
		this.field = field;
	}

	/**
	 * Gets the what change.
	 *
	 * @return the what change
	 */
	public String getWhatChange() {
		return whatChange;
	}

	/**
	 * Sets the what change.
	 *
	 * @param whatChange the new what change
	 */
	public void setWhatChange(String whatChange) {
		this.whatChange = whatChange;
	}

}
