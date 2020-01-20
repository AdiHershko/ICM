package Server;

import java.util.ArrayList;
import java.util.Collections;

import org.joda.time.DateTime;
import org.joda.time.Duration;

import Common.FrequencyDeviation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The Class Calculator.
 * To calculate the statistics on an ArrayList on values.
 */
public class Calculator {
	
	/** The ins. */
	public static Calculator _ins;

	/**
	 * Gets the instance.
	 *
	 * @return the instance
	 */
	public static Calculator getInstannce() {
		if (_ins == null)
			_ins = new Calculator();
		return _ins;
	}

	/**
	 * Sum.
	 *
	 * @param list the list
	 * @return the double
	 */
	public Double sum(ArrayList<Double> list) {
		Double sum = (double) 0;
		for (Double i : list)
			sum += i;
		return sum;
	}

	/**
	 * Median.
	 *
	 * @param list the list
	 * @return the double
	 */
	public double median(ArrayList<Double> list) {
		int size = list.size();
		if (size == 0) {
			return 0;
		}
		Collections.sort(list);
		if (size % 2 == 1) {
			return list.get(((size + 1) / 2) - 1);
		} else {
			Double tmp1 = list.get((size / 2) - 1);
			Double tmp2 = list.get(size / 2);
			return (tmp1 + tmp2) / 2.0;
		}
	}

	/**
	 * Standard deviation.
	 *
	 * @param list the list
	 * @return the double
	 */
	public double standardDeviation(ArrayList<Double> list) {
		if (list.size() == 0)
			return 0;
		double avg = ((double) sum(list)) / list.size();
		double tmp = 0;
		for (Double i : list)
			tmp += (i - avg) * (i - avg);
		tmp = tmp / list.size();
		tmp = Math.sqrt(tmp);
		return tmp;
	}

	/**
	 * Calculate sum, median and deviation.
	 *
	 * @param list the list
	 * @return the array list
	 */
	public ArrayList<Double> calcAll(ArrayList<Double> list) {
		ArrayList<Double> all = new ArrayList<Double>();
		all.add((double) sum(list));
		all.add(median(list));
		all.add(standardDeviation(list));
		return all;
	}

	/**
	 * Frequency deviation.
	 *
	 * @param list the list
	 * @return the observable list
	 */
	public ObservableList<FrequencyDeviation> freqDeviation(ArrayList<Double> list) {
		ArrayList<Double> values = new ArrayList<Double>();
		for (Double i : list) {
			if (!values.contains(i))
				values.add(i);
		}
		Collections.sort(values);
		ObservableList<FrequencyDeviation> res = FXCollections.observableArrayList();
		for (int i = 0; i < values.size(); i++) {
			FrequencyDeviation f = new FrequencyDeviation(values.get(i), Collections.frequency(list, values.get(i)));
			res.add(f);
		}
		return res;
	}
	
	/**
	 * Calculate activity report (status count, work days count).
	 *
	 * @param Datelist the creation date list
	 * @param ClosingDatelist the closing date list
	 * @param Statuses the requests statuses
	 * @param dt1 the start date
	 * @param dt2 the end date
	 * @return the results array list
	 */
	
	public static ArrayList<Double> calculateActivity(ArrayList<String> Datelist, ArrayList<String> ClosingDatelist, ArrayList<Integer> Statuses, DateTime dt1, DateTime dt2) {
		int size = 5;//5 is the number of asked values
		ArrayList<Double> l = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			l.add(0.0);
		}
		for (int i = 0; i < Statuses.size(); i++) {
			DateTime openingDate = new DateTime(Datelist.get(i));
			int currStatus = Statuses.get(i);
			if (openingDate.isAfter(dt1) && openingDate.isBefore(dt2)) {
				if (currStatus != 4)
					l.set(currStatus, l.get(currStatus)+1);
				if (currStatus == 4)
					l.set(3, l.get(3)+1);
				DateTime closingDate = new DateTime(ClosingDatelist.get(i));
				Duration dur = new Duration(openingDate, closingDate);
				long tmp = dur.getStandardHours();
				l.set(4, l.get(4)+(tmp / 24.0));
			}
		}
		return l;
	}

}
