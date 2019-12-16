package Common;

public class Report {
	private String location;
	private String description;
	private String result;
	private String constrains;
	private String risks;
	private int durationAssesment;


	public Report(String location, String Description, String Result,String Constrains,String Risks, int DurationAssesment) {
		this.location=location;
		this.description=Description;
		this.result=Result;
		this.constrains=Constrains;
		this.risks=Risks;
		this.durationAssesment=DurationAssesment;
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
