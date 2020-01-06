package Common;

import java.io.Serializable;

import org.joda.time.DateTime;

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
	private DateTime date;
	private RequestStatus status;
	private RequestStageENUM currentStage;
	private Stage stages[];
	private Report report;
	private int isDenied;
	private String currentHandlers;

	public Request(int id,String requestorID, SystemENUM system, String description, String changes, String changeReason, DateTime date) {
		this.id=id;
		this.requestorID = requestorID;
		this.system = system;
		this.description = description;
		this.changes = changes;
		this.changeReason = changeReason;
		this.date = date;
		this.currentStage=Enums.RequestStageENUM.Initialization;
		this.status=Enums.RequestStatus.Active;
		this.comments="";
		this.isDenied=0;
	}
	
	public Request(int id,String requestorID, SystemENUM system, String description, String changes, String changeReason, RequestStageENUM currentStage,RequestStatus status,DateTime date,String comments,String currentHandlers,int isDenied) {
		this.id=id;
		this.requestorID = requestorID;
		this.system = system;
		this.description = description;
		this.changes = changes;
		this.changeReason = changeReason;
		this.currentStage=currentStage;
		this.status=status;
		this.date = date;
		this.comments=comments;
		this.currentHandlers=currentHandlers;
		this.isDenied=isDenied;
	}
	
	
	

	public String getCurrentHandlers() {
		return currentHandlers;
	}

	public void setCurrentHandlers(String currentHandlers) {
		this.currentHandlers = currentHandlers;
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

	public DateTime getDate() {
		return date;
	}


	public int getIsDenied() {
		return isDenied;
	}

	public void setIsDenied(int isDenied) {
		this.isDenied = isDenied;
	}

	public void setDate(DateTime date) {
		this.date = date;
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


	public RequestStatus getStatusEnum() {
		return status;
	}
	public RequestStatus getStatus() {
		return status;
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

	public Stage[] getStages() {
		return stages;
	}

	public void setStages(Stage[] stages) {
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

	public String getChangeReason() {
		return changeReason;
	}

	public void setChangeReason(String changeReason) {
		this.changeReason = changeReason;
	}
}
