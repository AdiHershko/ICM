package Common;

import java.io.Serializable;

public class FrequencyDeviation implements Serializable{
	
	private static final long serialVersionUID = 3L;

	private double value;
	private int freq;

	public FrequencyDeviation(double value, int freq) {
		this.value = value;
		this.freq = freq;
	}

	public int getFreq() {
		return freq;
	}

	public void setFreq(int freq) {
		this.freq = freq;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value+","+freq;
	}
}
