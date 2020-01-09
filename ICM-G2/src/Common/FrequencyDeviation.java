package Common;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class FrequencyDeviation.
 */
public class FrequencyDeviation implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3L;

	/** The value. */
	private double value;
	
	/** The freq. */
	private int freq;

	/**
	 * Instantiates a new frequency deviation.
	 *
	 * @param value the value
	 * @param freq the freq
	 */
	public FrequencyDeviation(double value, int freq) {
		this.value = value;
		this.freq = freq;
	}

	/**
	 * Gets the freq.
	 *
	 * @return the freq
	 */
	public int getFreq() {
		return freq;
	}

	/**
	 * Sets the freq.
	 *
	 * @param freq the new freq
	 */
	public void setFreq(int freq) {
		this.freq = freq;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public double getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(double value) {
		this.value = value;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return value + "," + freq;
	}
}
