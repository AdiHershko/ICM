package Common;

import java.io.Serializable;

import Common.Enums.RequestStatus;
import Common.Enums.Systems;

public class Request implements Serializable {

	private static final long serialVersionUID = -4393026080251453811L;
	
	private User requestor;
	private int id;
	private String description;
	private String changes;
	private RequestStatus status;
	private Systems system;
	
	public Request(User requestor,String description,String changes,Systems system)
	{
		this.requestor=requestor;
		this.description=description;
		this.changes=changes;
		this.system=system;
		status=RequestStatus.Active;
	}

	public User getRequestor() {
		return requestor;
	}

	public void setRequestor(User requestor) {
		this.requestor = requestor;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public RequestStatus getStatus() {
		return status;
	}

	public void setStatus(RequestStatus status) {
		this.status = status;
	}

	public Systems getSystem() {
		return system;
	}

	public void setSystem(Systems system) {
		this.system = system;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
	


}
