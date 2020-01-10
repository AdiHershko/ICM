package Common;

import java.io.Serializable;

/**
 * The Class Report.
 * Entity for the request assessment report in our DB.
 */
public class Report implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4393026080251453861L;
	
	/** The location. */
	private String location;
	
	/** The description. */
	private String description;
	
	/** The result. */
	private String result;
	
	/** The constrains. */
	private String constrains;
	
	/** The risks. */
	private String risks;
	
	/** The duration assessment. */
	private int durationAssesment;
	
	/** The request id. */
	private int requestId;

	/**
	 * Instantiates a new report.
	 */
	public Report() {

	}

	/**
	 * Instantiates a new report.
	 *
	 * @param requestId the request id
	 * @param description the description
	 * @param result the result
	 * @param location the location
	 * @param constrains the constrains
	 * @param risks the risks
	 * @param durationAssesment the duration assessment
	 */
	public Report(int requestId, String description, String result, String location, String constrains, String risks,
			int durationAssesment) {
		this.requestId = requestId;
		this.description = description;
		this.result = result;
		this.location = location;
		this.constrains = constrains;
		this.risks = risks;
		this.durationAssesment = durationAssesment;

	}

	/**
	 * Gets the request id.
	 *
	 * @return the request id
	 */
	public int getRequestId() {
		return requestId;
	}

	/**
	 * Sets the request id.
	 *
	 * @param requestId the new request id
	 */
	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}

	/**
	 * Gets the location.
	 *
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * Sets the location.
	 *
	 * @param location the new location
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the result.
	 *
	 * @return the result
	 */
	public String getResult() {
		return result;
	}

	/**
	 * Sets the result.
	 *
	 * @param result the new result
	 */
	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * Gets the constrains.
	 *
	 * @return the constrains
	 */
	public String getConstrains() {
		return constrains;
	}

	/**
	 * Sets the constrains.
	 *
	 * @param constrains the new constrains
	 */
	public void setConstrains(String constrains) {
		this.constrains = constrains;
	}

	/**
	 * Gets the risks.
	 *
	 * @return the risks
	 */
	public String getRisks() {
		return risks;
	}

	/**
	 * Sets the risks.
	 *
	 * @param risks the new risks
	 */
	public void setRisks(String risks) {
		this.risks = risks;
	}

	/**
	 * Gets the duration assessment.
	 *
	 * @return the duration assessment
	 */
	public int getDurationAssesment() {
		return durationAssesment;
	}

	/**
	 * Sets the duration assessment.
	 *
	 * @param durationAssesment the new duration assessment
	 */
	public void setDurationAssesment(int durationAssesment) {
		this.durationAssesment = durationAssesment;
	}
}
