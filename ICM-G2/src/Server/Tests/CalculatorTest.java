/**
 * Testing call for calculator 
 */
package Server.Tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import Common.FrequencyDeviation;
import Server.Calculator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The Class CalculatorTest,
 * for JUnit testing to the calculating class.
 */
public class CalculatorTest {
	
	/** The calculator for testing. */
	Calculator calc;
	
	/** The delta, Using with delta to avoid depreciation when assering equals for doubles. */
	double delta = 0.000000000000001;
	
	/**
	 * Inits the tests.
	 */
	@Before
	public void init() {
		calc = new Server.Calculator();
	}
	
	/**
	 * Test sum for empty array.
	 */
	@Test
	public void testSum_EmptyArray() {
		ArrayList<Double> list = new ArrayList<Double>();
		assertEquals((Double) 0.0, calc.sum(list), delta);
	}
	
	/**
	 * Test sum for positive array.
	 */
	@Test
	public void testSum_PositiveArray() {
		ArrayList<Double> list = new ArrayList<Double>();
		list.add(1.0);
		list.add(2.5);
		list.add(3.2);
		assertEquals((Double) 6.7, calc.sum(list), delta);
	}
	
	/**
	 * Test sum for regular array.
	 */
	@Test
	public void testSum_ReuglarArray() {
		ArrayList<Double> list = new ArrayList<Double>();
		list.add(1.0);
		list.add(-2.5);
		list.add(3.2);
		assertEquals((Double) 1.7, calc.sum(list), delta);
	}
	
	/**
	 * Test median for empty array.
	 */
	@Test
	public void testMedian_EmptyArray() {
		ArrayList<Double> list = new ArrayList<Double>();
		assertEquals(0.0, calc.median(list), delta);
	}
	
	/**
	 * Test median for sorted odd array.
	 */
	@Test
	public void testMedian_SortedOddArray() {
		ArrayList<Double> list = new ArrayList<Double>();
		list.add(1.0);
		list.add(2.5);
		list.add(3.2);
		assertEquals(2.5, calc.median(list), delta);
	}
	
	/**
	 * Test median for sorted even array.
	 */
	@Test
	public void testMedian_SortedEvenArray() {
		ArrayList<Double> list = new ArrayList<Double>();
		list.add(1.0);
		list.add(2.5);
		list.add(3.2);
		list.add(4.2);
		assertEquals(2.85, calc.median(list), delta);
	}
	
	/**
	 * Test median for un-sorted odd array.
	 */
	@Test
	public void testMedian_UnSortedOddArray() {
		ArrayList<Double> list = new ArrayList<Double>();
		list.add(2.5);
		list.add(1.0);
		list.add(3.2);
		assertEquals(2.5, calc.median(list), delta);
	}
	
	/**
	 * Test median for un-sorted even array.
	 */
	@Test
	public void testMedian_UnSortedEvenArray() {
		ArrayList<Double> list = new ArrayList<Double>();
		list.add(2.5);
		list.add(1.0);
		list.add(4.2);
		list.add(3.2);
		assertEquals(2.85, calc.median(list), delta);
	}

	/**
	 * Test standard deviation for empty array.
	 */
	@Test
	public void testStandardDeviation_EmptyArray() {
		ArrayList<Double> list = new ArrayList<Double>();
		assertEquals(0, calc.standardDeviation(list), delta);
	}
	
	/**
	 * Test standard deviation for positive array.
	 */
	@Test
	public void testStandardDeviation_PositiveArray() {
		ArrayList<Double> list = new ArrayList<Double>();
		list.add(2.5);
		list.add(1.0);
		list.add(3.2);
		assertEquals(0.9177266598624138, calc.standardDeviation(list), delta);
	}
	
	/**
	 * Test standard deviation for regular array.
	 */
	@Test
	public void testStandardDeviation_RegularArray() {
		ArrayList<Double> list = new ArrayList<Double>();
		list.add(-2.5);
		list.add(1.0);
		list.add(3.2);
		assertEquals(2.347102232304526, calc.standardDeviation(list), delta);
	}

	/**
	 * Test calcAll function.
	 * It sends a list with the sum, median and standard deviation in that order.
	 * Only needs one test because we checked the sum, median and standard deviation.
	 */
	@Test
	public void testCalcAll() {
		ArrayList<Double> list = new ArrayList<Double>();
		list.add(1.0);
		list.add(2.5);
		list.add(3.2);
		ArrayList<Double> res = new ArrayList<Double>();
		res.add(6.7);
		res.add(2.5);
		res.add(0.9177266598624138);
		assertEquals(res, calc.calcAll(list));
	}

	/**
	 * Test frequency deviation for empty array.
	 */
	@Test
	public void testFreqDeviation_EmptyArray() {
		ArrayList<Double> list = new ArrayList<Double>();
		ObservableList<FrequencyDeviation> res = FXCollections.observableArrayList();
		assertEquals(res, calc.freqDeviation((list)));
	}

	/**
	 * Test frequency deviation for positive array.
	 */
	@Test
	public void testFreqDeviation_PositiveArray() {
		ArrayList<Double> list = new ArrayList<Double>();
		list.add(1.0);
		list.add(2.5);
		list.add(2.5);
		list.add(3.2);
	
		ObservableList<FrequencyDeviation> res = FXCollections.observableArrayList();
		res.add(new FrequencyDeviation(1.0, 1));
		res.add(new FrequencyDeviation(2.5, 2));
		res.add(new FrequencyDeviation(3.2, 1));
		
		ObservableList<FrequencyDeviation> calcRes = calc.freqDeviation(list);
		
		if(res.size() != calcRes.size())//if lists don't have equal size
			assertTrue(false);
		for (int i = 0; i < calcRes.size(); i++) {//check all list
			if (!res.get(i).equals(calcRes.get(i)))
				assertTrue(false);
		}
		assertTrue(true);
	}
	
	
	/**
	 * Test frequency deviation for regular array.
	 */
	@Test
	public void testFreqDeviation_ReqgularArray() {
		ArrayList<Double> list = new ArrayList<Double>();
		list.add(1.0);
		list.add(2.5);
		list.add(2.5);
		list.add(-2.5);
		list.add(3.2);
		
		ObservableList<FrequencyDeviation> res = FXCollections.observableArrayList();
		res.add(new FrequencyDeviation(-2.5, 1));
		res.add(new FrequencyDeviation(1.0, 1));
		res.add(new FrequencyDeviation(2.5, 2));
		res.add(new FrequencyDeviation(3.2, 1));
		
		ObservableList<FrequencyDeviation> calcRes = calc.freqDeviation(list);
		
		if(res.size() != calcRes.size())//if lists don't have equal size
			assertTrue(false);
		for (int i = 0; i < calcRes.size(); i++) {//check all list
			if (!res.get(i).equals(calcRes.get(i)))
				assertTrue(false);
		}
		assertTrue(true);
	}
	
}
