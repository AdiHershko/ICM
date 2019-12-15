package Common;

public class Report {
	private String location;
	private String Description;
	private String Result;
	private String Constrains;
	private String Risks;
	private int DurationAssesment;
	
	
	public Report(String location, String Description, String Result,String Constrains,String Risks, int DurationAssesment) {
		this.location=location;
		this.Description=Description;
		this.Result=Result;
		this.Constrains=Constrains;
		this.Risks=Risks;
		this.DurationAssesment=DurationAssesment;
	}
	
	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getResult() {
		return Result;
	}
	public void setResult(String result) {
		Result = result;
	}
	public String getConstrains() {
		return Constrains;
	}
	public void setConstrains(String constrains) {
		Constrains = constrains;
	}
	public String getRisks() {
		return Risks;
	}
	public void setRisks(String risks) {
		Risks = risks;
	}
	public int getDurationAssesment() {
		return DurationAssesment;
	}
	public void setDurationAssesment(int durationAssesment) {
		DurationAssesment = durationAssesment;
	}
}
