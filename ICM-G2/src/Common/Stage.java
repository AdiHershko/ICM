package Common;

import java.util.ArrayList;
import Common.Enums.RequestStageENUM;
import sun.util.calendar.LocalGregorianCalendar.Date;

public class Stage {
	private RequestStageENUM stageName;
	private Date plannedDueDate;
	private Boolean isApproved;
	private Boolean isExtended;
	private ArrayList<ISUser> stageMembers;
	private User stageLeader;

	public Stage(RequestStageENUM stageName, Date plannedDueDate, Boolean isApproved, Boolean isExtended,
			ArrayList<ISUser> stageMembers, User stageLeader) {

		this.stageName = stageName;
		this.plannedDueDate = plannedDueDate;
		this.isApproved = isApproved;
		this.isExtended = isExtended;
		this.stageMembers = stageMembers;
		this.stageLeader = stageLeader;

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

	public ArrayList<ISUser> getStageMembers() {
		return stageMembers;
	}

	public void setStageMembers(ArrayList<ISUser> stageMembers) {
		this.stageMembers = stageMembers;
	}

	public User getStageLeader() {
		return stageLeader;
	}

	public void setStageLeader(User stageLeader) {
		this.stageLeader = stageLeader;
	}

}