package Common;

import java.io.Serializable;
import java.util.Date;

public class Report implements Serializable{
	private static final long serialVersionUID = -4393026080251453861L;
	private String location;
	private String description;
	private String result;
	private String constrains;
	private String risks;
	private int durationAssesment;
	private int requestId;

	


	
	public int getRequestId() {
		return requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getConstrains() {
		return constrains;
	}
	public void setConstrains(String constrains) {
		this.constrains = constrains;
	}
	public String getRisks() {
		return risks;
	}
	public void setRisks(String risks) {
		this.risks = risks;
	}
	public int getDurationAssesment() {
		return durationAssesment;
	}
	public void setDurationAssesment(int durationAssesment) {
		this.durationAssesment = durationAssesment;
	}
}
