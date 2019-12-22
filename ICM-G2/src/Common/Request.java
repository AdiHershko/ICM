package Common;

import java.io.Serializable;
import java.util.ArrayList;

import Common.Enums.*;

public class Request implements Serializable {

	private static final long serialVersionUID = -4393026080251453811L;

	private int id;
	private String requestorID;
	private SystemENUM system;
	private String description;
	private String changes;
	private String changeReason;
	private String comments;
	private String filePath[];
	private String date;
	private RequestStatus status;
	private RequestStageENUM currentStage;
	private RequestStage stages[];
	private Report report;
	private ArrayList<User> currentHandlers;

	public Request(int id,String requestorID, SystemENUM system, String description, String changes, String changeReason, String date) {
		this.id=id;
		this.requestorID = requestorID;
		this.system = system;
		this.description = description;
		this.changes = changes;
		this.changeReason = changeReason;
		this.date = date;
		this.currentStage=Enums.RequestStageENUM.Start;
		this.status=Enums.RequestStatus.Active;
		this.comments="";
		currentHandlers = new ArrayList<User>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRequestorID() {
		return requestorID;
	}

	public void setRequestorID(String requestorID) {
		this.requestorID = requestorID;
	}

	public SystemENUM getSystem() {
		return system;
	}

	public void setSystem(SystemENUM system) {
		this.system = system;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getChanges() {
		return changes;
	}

	public void setChanges(String changes) {
		this.changes = changes;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String[] getFilePath() {
		return filePath;
	}

	public void setFilePath(String[] filePath) {
		this.filePath = filePath;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public RequestStatus getStatusEnum() {
		return status;
	}
	public String getStatus() {
		return status.toString();
	}

	public void setStatus(RequestStatus status) {
		this.status = status;
	}

	public RequestStageENUM getCurrentStageEnum() {
		return currentStage;
	}

	public RequestStageENUM getCurrentStage() {
		return currentStage;
	}

	public void setCurrentStage(RequestStageENUM currentStage) {
		this.currentStage = currentStage;
	}

	public RequestStage[] getStages() {
		return stages;
	}

	public void setStages(RequestStage[] stages) {
		this.stages = stages;
	}

	public Report getReport() {
		return report;
	}

	public void setReport(Report report) {
		this.report = report;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ArrayList<User> getCurrentHandlers() {
		return currentHandlers;
	}

	public void setCurrentHandlers(ArrayList<User> currentHandlers) {
		this.currentHandlers = currentHandlers;
	}

	public String getChangeReason() {
		return changeReason;
	}

	public void setChangeReason(String changeReason) {
		this.changeReason = changeReason;
	}
}
