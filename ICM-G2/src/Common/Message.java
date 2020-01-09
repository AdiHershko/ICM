package Common;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class Message.
 */
public class Message implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1650275113508444918L;
	
	/** The request ID. */
	private int requestID;
	
	/** The title. */
	private String title;
	
	/** The details. */
	private String details;
	
	/** The reciever. */
	private String reciever;

	/**
	 * Instantiates a new message.
	 *
	 * @param requestID the request ID
	 * @param title the title
	 * @param details the details
	 * @param reciever the reciever
	 */
	public Message(int requestID, String title, String details, String reciever) {
		this.requestID = requestID;
		this.title = title;
		this.details = details;
		this.reciever = reciever;
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
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 *
	 * @param title the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the details.
	 *
	 * @return the details
	 */
	public String getDetails() {
		return details;
	}

	/**
	 * Sets the details.
	 *
	 * @param details the new details
	 */
	public void setDetails(String details) {
		this.details = details;
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

}
