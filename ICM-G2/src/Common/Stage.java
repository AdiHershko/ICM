package Common;

import java.io.Serializable;
import java.util.ArrayList;

import Common.Enums.RequestStageENUM;

// TODO: Auto-generated Javadoc
/**
 * The Class Stage.
 */
public class Stage implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The stage name. */
	private RequestStageENUM stageName;
	
	/** The planned due date. */
	private String plannedDueDate;
	
	/** The extended due date. */
	private String extendedDueDate;
	
	/** The actual date. */
	private String actualDate;
	
	/** The Report failure. */
	private String ReportFailure;
	
	/** The extension ask. */
	private String extensionAsk;
	
	/** The is approved. */
	private Boolean isApproved;
	
	/** The is extended. */
	private Boolean isExtended;
	
	/** The stage members. */
	private ArrayList<String> stageMembers;
	
	/** The member. */
	private String member;
	
	/** The request ID. */
	private int requestID;
	
	/** The days of extension. */
	private int daysOfExtension;

	/**
	 * Instantiates a new stage.
	 */
	public Stage() {

	}

	/**
	 * Instantiates a new stage.
	 *
	 * @param stageName the stage name
	 * @param plannedDueDate the planned due date
	 * @param isApproved the is approved
	 * @param isExtended the is extended
	 */
	public Stage(RequestStageENUM stageName, String plannedDueDate, Boolean isApproved, Boolean isExtended) {
		this.stageName = stageName;
		this.plannedDueDate = plannedDueDate;
		this.isApproved = isApproved;
		this.isExtended = isExtended;
	}

	/**
	 * Instantiates a new stage.
	 *
	 * @param stageName the stage name
	 * @param plannedDueDate the planned due date
	 * @param isApproved the is approved
	 * @param isExtended the is extended
	 * @param member the member
	 * @param requestID the request ID
	 * @param actualDate the actual date
	 * @param extendedDueDate the extended due date
	 * @param ReportFailure the report failure
	 * @param daysOfExtension the days of extension
	 */
	public Stage(RequestStageENUM stageName, String plannedDueDate, Boolean isApproved, Boolean isExtended,
			String member, int requestID, String actualDate, String extendedDueDate, String ReportFailure,
			int daysOfExtension) {
		this.stageName = stageName;
		this.plannedDueDate = plannedDueDate;
		this.isApproved = isApproved;
		this.isExtended = isExtended;
		this.member = member;
		this.requestID = requestID;
		this.actualDate = actualDate;
		this.extendedDueDate = extendedDueDate;
		this.ReportFailure = ReportFailure;
		this.daysOfExtension = daysOfExtension;
	}

	/**
	 * Gets the days of extension.
	 *
	 * @return the days of extension
	 */
	public int getDaysOfExtension() {
		return daysOfExtension;
	}

	/**
	 * Sets the days of extension.
	 *
	 * @param daysOfExtension the new days of extension
	 */
	public void setDaysOfExtension(int daysOfExtension) {
		this.daysOfExtension = daysOfExtension;
	}

	/**
	 * Gets the stage name.
	 *
	 * @return the stage name
	 */
	public RequestStageENUM getStageName() {
		return stageName;
	}

	/**
	 * Sets the stage name.
	 *
	 * @param stageName the new stage name
	 */
	public void setStageName(RequestStageENUM stageName) {
		this.stageName = stageName;
	}

	/**
	 * Gets the planned due date.
	 *
	 * @return the planned due date
	 */
	public String getPlannedDueDate() {
		return plannedDueDate;
	}

	/**
	 * Sets the planned due date.
	 *
	 * @param plannedDueDate the new planned due date
	 */
	public void setPlannedDueDate(String plannedDueDate) {
		this.plannedDueDate = plannedDueDate;
	}

	/**
	 * Gets the checks if is approved.
	 *
	 * @return the checks if is approved
	 */
	public Boolean getIsApproved() {
		return isApproved;
	}

	/**
	 * Sets the checks if is approved.
	 *
	 * @param isApproved the new checks if is approved
	 */
	public void setIsApproved(Boolean isApproved) {
		this.isApproved = isApproved;
	}

	/**
	 * Gets the checks if is extended.
	 *
	 * @return the checks if is extended
	 */
	public Boolean getIsExtended() {
		return isExtended;
	}

	/**
	 * Sets the checks if is extended.
	 *
	 * @param isExtended the new checks if is extended
	 */
	public void setIsExtended(Boolean isExtended) {
		this.isExtended = isExtended;
	}

	/**
	 * Gets the stage members.
	 *
	 * @return the stage members
	 */
	public ArrayList<String> getStageMembers() {
		return stageMembers;
	}

	/**
	 * Sets the stage members.
	 *
	 * @param stageMembers the new stage members
	 */
	public void setStageMembers(ArrayList<String> stageMembers) {
		this.stageMembers = stageMembers;
	}

	/**
	 * Gets the extended due date.
	 *
	 * @return the extended due date
	 */
	public String getExtendedDueDate() {
		return extendedDueDate;
	}

	/**
	 * Sets the extended due date.
	 *
	 * @param extendedDueDate the new extended due date
	 */
	public void setExtendedDueDate(String extendedDueDate) {
		this.extendedDueDate = extendedDueDate;
	}

	/**
	 * Gets the report failure.
	 *
	 * @return the report failure
	 */
	public String getReportFailure() {
		return ReportFailure;
	}

	/**
	 * Sets the report failure.
	 *
	 * @param reportFailure the new report failure
	 */
	public void setReportFailure(String reportFailure) {
		ReportFailure = reportFailure;
	}

	/**
	 * Gets the actual date.
	 *
	 * @return the actual date
	 */
	public String getActualDate() {
		return actualDate;
	}

	/**
	 * Sets the actual date.
	 *
	 * @param actualDate the new actual date
	 */
	public void setActualDate(String actualDate) {
		this.actualDate = actualDate;
	}

	/**
	 * Gets the extension ask.
	 *
	 * @return the extension ask
	 */
	public String getExtensionAsk() {
		return extensionAsk;
	}

	/**
	 * Sets the extension ask.
	 *
	 * @param extensionAsk the new extension ask
	 */
	public void setExtensionAsk(String extensionAsk) {
		this.extensionAsk = extensionAsk;
	}

	/**
	 * Gets the member.
	 *
	 * @return the member
	 */
	public String getMember() {
		return member;
	}

	/**
	 * Sets the member.
	 *
	 * @param member the new member
	 */
	public void setMember(String member) {
		this.member = member;
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

}