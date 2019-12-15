package Common;

import java.io.Serializable;

import Common.Enums.*;

public class Request implements Serializable {

	private static final long serialVersionUID = -4393026080251453811L;
	
	private int id;
	private User requestor;
	private SystemENUM system;
	private String description;
	private String changes;
	private String comments;
	private String filePath[];
	private String date;
	private RequestStatus status;
	private RequestStageENUM currentStage;
	private RequestStage stages[];
	private Report report;
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
