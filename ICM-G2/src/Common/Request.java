package Common;

import java.io.Serializable;

import org.joda.time.DateTime;

import Common.Enums.*;

/**
 * The Class Request.
 * Entity for the request in our DB.
 */
public class Request implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4393026080251453811L;

	/** The id. */
	private int id;
	
	/** The requester ID. */
	private String requestorID;
	
	/** The system. */
	private SystemENUM system;
	
	/** The description. */
	private String description;
	
	/** The changes. */
	private String changes;
	
	/** The change reason. */
	private String changeReason;
	
	/** The comments. */
	private String comments;
	
	/** The file path. */
	private String filePath[];
	
	/** The date. */
	private DateTime date;
	
	/** The status. */
	private RequestStatus status;
	
	/** The current stage. */
	private RequestStageENUM currentStage;
	
	/** The stages. */
	private Stage stages[];
	
	/** The report. */
	private Report report;
	
	/** The is denied. */
	private int isDenied;
	
	/** The current handlers. */
	private String currentHandlers;

	/**
	 * Instantiates a new request.
	 *
	 * @param id the id
	 * @param requestorID the requester ID
	 * @param system the system
	 * @param description the description
	 * @param changes the changes
	 * @param changeReason the change reason
	 * @param date the date
	 */
	public Request(int id, String requestorID, SystemENUM system, String description, String changes,
			String changeReason, DateTime date) {
		this.id = id;
		this.requestorID = requestorID;
		this.system = system;
		this.description = description;
		this.changes = changes;
		this.changeReason = changeReason;
		this.date = date;
		this.currentStage = Enums.RequestStageENUM.Initialization;
		this.status = Enums.RequestStatus.Active;
		this.comments = "";
		this.isDenied = 0;
	}

	/**
	 * Instantiates a new request.
	 *
	 * @param id the id
	 * @param requestorID the requester ID
	 * @param system the system
	 * @param description the description
	 * @param changes the changes
	 * @param changeReason the change reason
	 * @param currentStage the current stage
	 * @param status the status
	 * @param date the date
	 * @param comments the comments
	 * @param currentHandlers the current handlers
	 * @param isDenied the is denied
	 */
	public Request(int id, String requestorID, SystemENUM system, String description, String changes,
			String changeReason, RequestStageENUM currentStage, RequestStatus status, DateTime date, String comments,
			String currentHandlers, int isDenied) {
		this.id = id;
		this.requestorID = requestorID;
		this.system = system;
		this.description = description;
		this.changes = changes;
		this.changeReason = changeReason;
		this.currentStage = currentStage;
		this.status = status;
		this.date = date;
		this.comments = comments;
		this.currentHandlers = currentHandlers;
		this.isDenied = isDenied;
	}

	/**
	 * Gets the current handlers.
	 *
	 * @return the current handlers
	 */
	public String getCurrentHandlers() {
		return currentHandlers;
	}

	/**
	 * Sets the current handlers.
	 *
	 * @param currentHandlers the new current handlers
	 */
	public void setCurrentHandlers(String currentHandlers) {
		this.currentHandlers = currentHandlers;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Gets the requester ID.
	 *
	 * @return the requester ID
	 */
	public String getRequestorID() {
		return requestorID;
	}

	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public DateTime getDate() {
		return date;
	}

	/**
	 * Gets the checks if is denied.
	 *
	 * @return the checks if is denied
	 */
	public int getIsDenied() {
		return isDenied;
	}

	/**
	 * Sets the checks if is denied.
	 *
	 * @param isDenied the new checks if is denied
	 */
	public void setIsDenied(int isDenied) {
		this.isDenied = isDenied;
	}

	/**
	 * Sets the date.
	 *
	 * @param date the new date
	 */
	public void setDate(DateTime date) {
		this.date = date;
	}

	/**
	 * Sets the requester ID.
	 *
	 * @param requestorID the new requester ID
	 */
	public void setRequestorID(String requestorID) {
		this.requestorID = requestorID;
	}

	/**
	 * Gets the system.
	 *
	 * @return the system
	 */
	public SystemENUM getSystem() {
		return system;
	}

	/**
	 * Sets the system.
	 *
	 * @param system the new system
	 */
	public void setSystem(SystemENUM system) {
		this.system = system;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the changes.
	 *
	 * @return the changes
	 */
	public String getChanges() {
		return changes;
	}

	/**
	 * Sets the changes.
	 *
	 * @param changes the new changes
	 */
	public void setChanges(String changes) {
		this.changes = changes;
	}

	/**
	 * Gets the comments.
	 *
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * Sets the comments.
	 *
	 * @param comments the new comments
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * Gets the file path.
	 *
	 * @return the file path
	 */
	public String[] getFilePath() {
		return filePath;
	}

	/**
	 * Sets the file path.
	 *
	 * @param filePath the new file path
	 */
	public void setFilePath(String[] filePath) {
		this.filePath = filePath;
	}

	/**
	 * Gets the status enum.
	 *
	 * @return the status enum
	 */
	public RequestStatus getStatusEnum() {
		return status;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public RequestStatus getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(RequestStatus status) {
		this.status = status;
	}

	/**
	 * Gets the current stage enum.
	 *
	 * @return the current stage enum
	 */
	public RequestStageENUM getCurrentStageEnum() {
		return currentStage;
	}

	/**
	 * Gets the current stage.
	 *
	 * @return the current stage
	 */
	public RequestStageENUM getCurrentStage() {
		return currentStage;
	}

	/**
	 * Sets the current stage.
	 *
	 * @param currentStage the new current stage
	 */
	public void setCurrentStage(RequestStageENUM currentStage) {
		this.currentStage = currentStage;
	}

	/**
	 * Gets the stages.
	 *
	 * @return the stages
	 */
	public Stage[] getStages() {
		return stages;
	}

	/**
	 * Sets the stages.
	 *
	 * @param stages the new stages
	 */
	public void setStages(Stage[] stages) {
		this.stages = stages;
	}

	/**
	 * Gets the report.
	 *
	 * @return the report
	 */
	public Report getReport() {
		return report;
	}

	/**
	 * Sets the report.
	 *
	 * @param report the new report
	 */
	public void setReport(Report report) {
		this.report = report;
	}

	/**
	 * Gets the serialversionuid.
	 *
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * Gets the change reason.
	 *
	 * @return the change reason
	 */
	public String getChangeReason() {
		return changeReason;
	}

	/**
	 * Sets the change reason.
	 *
	 * @param changeReason the new change reason
	 */
	public void setChangeReason(String changeReason) {
		this.changeReason = changeReason;
	}
}
