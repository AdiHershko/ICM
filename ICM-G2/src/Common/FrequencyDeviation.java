package Common;

import java.io.Serializable;

/**
 * The Class FrequencyDeviation.
 * Value and frequency for the statis reports class.
 */
public class FrequencyDeviation implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3L;

	/** The value. */
	private double value;
	
	/** The frequency. */
	private int freq;

	/**
	 * Instantiates a new frequency deviation.
	 *
	 * @param value the value
	 * @param freq the frequency
	 */
	public FrequencyDeviation(double value, int freq) {
		this.value = value;
		this.freq = freq;
	}

	/**
	 * Gets the frequency.
	 *
	 * @return the frequency
	 */
	public int getFreq() {
		return freq;
	}

	/**
	 * Sets the frequency.
	 *
	 * @param freq the new frequency
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
