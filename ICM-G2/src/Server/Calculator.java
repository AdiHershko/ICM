package Server;

import java.util.ArrayList;
import java.util.Collections;

import Common.FrequencyDeviation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Calculator {
	private static Calculator _ins;

	public static Calculator getInstannce() {
		if (_ins == null)
			_ins = new Calculator();
		return _ins;
	}

	public Double sum(ArrayList<Double> list) {
		Double sum = (double) 0;
		for (Double i : list)
			sum += i;
		return sum;
	}

	public double median(ArrayList<Double> list) {
		Collections.sort(list);
		int size = list.size();
		if (size % 2 == 1) {
			return list.get(((size + 1) / 2) - 1);
		} else {
			Double tmp1 = list.get((size / 2) - 1);
			Double tmp2 = list.get(size / 2);
			return (tmp1 + tmp2) / 2.0;
		}
	}

	public double standardDeviation(ArrayList<Double> list) {
		double avg = ((double) sum(list)) / list.size();
		double tmp = 0;
		for (Double i : list)
			tmp += (i - avg) * (i - avg);
		tmp = tmp / list.size();
		tmp = Math.sqrt(tmp);
		return tmp;
	}

	public ArrayList<Double> calcAll(ArrayList<Double> list) {
		ArrayList<Double> all = new ArrayList<Double>();
		all.add((double) sum(list));
		all.add(median(list));
		all.add(standardDeviation(list));
		return all;
	}

	public ObservableList<FrequencyDeviation> freqDeviation(ArrayList<Double> list) {
		ArrayList<Double> values = new ArrayList<Double>();
		for (Double i : list) {
			if (!values.contains(i))
				values.add(i);
		}
		Collections.sort(values);
		ObservableList<FrequencyDeviation> res = FXCollections.observableArrayList();
		for (int i = 0; i < values.size(); i++) {
			FrequencyDeviation f = new FrequencyDeviation(values.get(i),Collections.frequency(list, values.get(i)));
			res.add(f);
		}
		return res;
	}

}
