package Common;

import java.io.Serializable;
import java.util.ArrayList;

import Common.Enums.RequestStageENUM;


public class Stage implements Serializable{
	private static final long serialVersionUID = 1L;
	private RequestStageENUM stageName;
	private String plannedDueDate;
	private String extendedDueDate;
	private String actualDate;
	private String ReportFailure;
	private String extensionAsk;
	private Boolean isApproved;
	private Boolean isExtended;
	private ArrayList<String> stageMembers;
	private String member;
	private int requestID;
	



	public Stage() {

	}

	public Stage(RequestStageENUM stageName, String plannedDueDate, Boolean isApproved, Boolean isExtended) {
		this.stageName = stageName;
		this.plannedDueDate = plannedDueDate;
		this.isApproved = isApproved;
		this.isExtended = isExtended;
	}

	public RequestStageENUM getStageName() {
		return stageName;
	}

	public void setStageName(RequestStageENUM stageName) {
		this.stageName = stageName;
	}

	public String getPlannedDueDate() {
		return plannedDueDate;
	}

	public void setPlannedDueDate(String plannedDueDate) {
		this.plannedDueDate = plannedDueDate;
	}

	public Boolean getIsApproved() {
		return isApproved;
	}

	public void setIsApproved(Boolean isApproved) {
		this.isApproved = isApproved;
	}

	public Boolean getIsExtended() {
		return isExtended;
	}

	public void setIsExtended(Boolean isExtended) {
		this.isExtended = isExtended;
	}

	public ArrayList<String> getStageMembers() {
		return stageMembers;
	}

	public void setStageMembers(ArrayList<String> stageMembers) {
		this.stageMembers = stageMembers;
	}

	public String getExtendedDueDate() {
		return extendedDueDate;
	}

	public void setExtendedDueDate(String extendedDueDate) {
		this.extendedDueDate = extendedDueDate;
	}

	public String getReportFailure() {
		return ReportFailure;
	}

	public void setReportFailure(String reportFailure) {
		ReportFailure = reportFailure;
	}

	public String getActualDate() {
		return actualDate;
	}

	public void setActualDate(String actualDate) {
		this.actualDate = actualDate;
	}

	public String getExtensionAsk() {
		return extensionAsk;
	}

	public void setExtensionAsk(String extensionAsk) {
		this.extensionAsk = extensionAsk;
	}
	
	public String getMember() {
		return member;
	}

	public void setMember(String member) {
		this.member = member;
	}

	public int getRequestID() {
		return requestID;
	}

	public void setRequestID(int requestID) {
		this.requestID = requestID;
	}

}