package Common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import Common.Enums.RequestStageENUM;


public class Stage implements Serializable{
	private static final long serialVersionUID = 1L;
	private RequestStageENUM stageName;
	private Date plannedDueDate;
	private Boolean isApproved;
	private Boolean isExtended;
	private ArrayList<String> stageMembers;
	private User stageLeader;

	public Stage() {
		
	}
	
	public Stage(RequestStageENUM stageName, Date plannedDueDate, Boolean isApproved, Boolean isExtended) {
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

	public Date getPlannedDueDate() {
		return plannedDueDate;
	}

	public void setPlannedDueDate(Date plannedDueDate) {
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

	public User getStageLeader() {
		return stageLeader;
	}

	public void setStageLeader(User stageLeader) {
		this.stageLeader = stageLeader;
	}

}