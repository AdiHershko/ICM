/**
 * Testing call for calculator 
 */
package Server.unittests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
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
	DataBaseControllerStub dbStub;
	
	/** The delta, Using with delta to avoid depreciation when asserting equals for doubles. */
	double delta = 0.000000000000001;
	
	/**
	 * Inits the tests.
	 */
	@Before
	public void init() {
		calc = new Server.Calculator();
		 dbStub = new DataBaseControllerStub(); // will have 8 requests
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
	
	/**
	 * Test when no requests in range.
	 */
	@Test
	public void test_NoRequestsInDates() { //checking period that have no requests
		String[] dates = {"2020-01-10","2020-01-12" };
		ArrayList<Double> calcRes;
		ArrayList<Double> res =  new ArrayList<>(); //expected
		for (int i = 0 ;i<5;i++) //adding 0 for each activity and 0 for total work days
			res.add(0.0);
		calcRes=dbStub.getActivityData(dates);
		if(res.size() != calcRes.size())//if lists don't have equal size
			assertTrue(false);
		for (int i = 0; i < calcRes.size(); i++) {//check all list
			if (!res.get(i).equals(calcRes.get(i)))
				assertTrue(false);
		}
		assertTrue(true);
	}

	/**
	 * Test when all requests are in range.
	 */
	@Test
	public void test_AllRequestsInDates(){ //checking a period that have all 8 requests within it
		DateTime today = new DateTime();
		Duration dur;
		long tmp;
		String[] dates = {"2020-01-13","2020-01-20" };
		ArrayList<Double> calcRes;
		ArrayList<Double> res =  new ArrayList<>(); //expected
		calcRes=dbStub.getActivityData(dates); //actual list
		res.add(2.0);
		res.add(2.0);
		res.add(1.0);
		res.add(3.0);
		double wd = 0;
		wd += 1.0;//1 request
		wd += 2.0;//2 request
		dur = new Duration(new DateTime("2020-01-14T12:00:00.000+02:00"), today);
		tmp = dur.getStandardHours();
		wd += (tmp / 24.0);//3 request
		wd += (tmp / 24.0);//4 request
		wd += 2.0;//5 request
		dur = new Duration(new DateTime("2020-01-18T12:00:00.000+02:00"), today);
		tmp = dur.getStandardHours();
		wd += (tmp / 24.0);//6 request
		wd += 4.0;//7 request
		wd += (tmp / 24.0);//8 request

		res.add(wd); //adding work days to the list

		if(res.size() != calcRes.size())//if lists don't have equal size
			assertTrue(false);
		for (int i = 0; i < calcRes.size(); i++) {//check all list
			if (!res.get(i).equals(calcRes.get(i)))
				assertTrue(false);
		}
		assertTrue(true);
	}

	/**
	 * Testing when not all requests are in range
	 * only the first 4 requests (opened in 13/1/2020) are in the range
	 */
	@Test
	public void test_SomeRequestsInDates1(){
		DateTime today = new DateTime();
		Duration dur;
		long tmp;
		String[] dates = {"2020-01-13","2020-01-15" };
		ArrayList<Double> calcRes;
		ArrayList<Double> res =  new ArrayList<>(); //expected
		calcRes=dbStub.getActivityData(dates);
		res.add(1.0);
		res.add(1.0);
		res.add(1.0);
		res.add(1.0);
		double wd = 0;
		wd += 1.0;//1 request
		wd += 2.0;//2 request
		dur = new Duration(new DateTime("2020-01-14T12:00:00.000+02:00"), today);
		tmp = dur.getStandardHours();
		wd += (tmp / 24.0);//3 request
		wd += (tmp / 24.0);//4 request

		// no need to add the rest because they are not in the range

		res.add(wd); //adding work days to the list

		if(res.size() != calcRes.size())//if lists don't have equal size
			assertTrue(false);
		for (int i = 0; i < calcRes.size(); i++) {//check all list
			if (!res.get(i).equals(calcRes.get(i)))
				assertTrue(false);
		}
		assertTrue(true);

	}


	/**
	 * Testing when not all requests are in range
	 * only the last 4 requests (opened in 18/1/2020) are in the range.
	 */
	@Test
	public void test_SomeRequestsInDates2(){
		DateTime today = new DateTime();
		Duration dur;
		long tmp;

		String[] dates = {"2020-01-17","2020-01-19" };
		ArrayList<Double> calcRes;
		ArrayList<Double> res =  new ArrayList<>();
		calcRes=dbStub.getActivityData(dates);
		System.out.println(calcRes);
		res.add(1.0);
		res.add(1.0);
		res.add(0.0);
		res.add(2.0);
		double wd = 0;
		wd += 2.0;//5 request
		dur = new Duration(new DateTime("2020-01-18T12:00:00.000+02:00"), today);
		tmp = dur.getStandardHours();
		wd += (tmp / 24.0);//6 request
		wd += 4.0;//7 request
		wd += (tmp / 24.0);//8 request

		//no need to add the first 4 because they are not in the range

		res.add(wd); //adding work days to list

		if(res.size() != calcRes.size())//if lists don't have equal size
			assertTrue(false);
		for (int i = 0; i < calcRes.size(); i++) {//check all list
			if (!res.get(i).equals(calcRes.get(i)))
				assertTrue(false);
		}
		assertTrue(true);

	}
	
}

/**
 * The stub database, it will have 8 requests, 4 from 14/1/2020 and 4 from 18/1/2020.
 * 2 requests from 14/1/2020 and 2 request from the 18/1/2020 are still open.
 **/
class DataBaseControllerStub implements IDataBaseController{
	
	/** The requests dates and statuses lists. */
	ArrayList<String> dateList;
	ArrayList<String> closingList;
	ArrayList<Integer> statusesList;
	
	/** The calculator. */
	Calculator calc;
	
	/**
	 * Instantiates a new data base controller stub with a request list.
	 */
	public DataBaseControllerStub() {
		calc = new Calculator();
		dateList = new ArrayList<>();
		dateList.add("2020-01-14T12:00:00.000+02:00");
		dateList.add("2020-01-14T12:00:00.000+02:00");
		dateList.add("2020-01-14T12:00:00.000+02:00");
		dateList.add("2020-01-14T12:00:00.000+02:00");
		dateList.add("2020-01-18T12:00:00.000+02:00");
		dateList.add("2020-01-18T12:00:00.000+02:00");
		dateList.add("2020-01-18T12:00:00.000+02:00");
		dateList.add("2020-01-18T12:00:00.000+02:00");
		closingList = new ArrayList<>();
		closingList.add("2020-01-15T12:00:00.000+02:00");
		closingList.add("2020-01-16T12:00:00.000+02:00");
		closingList.add(null);
		closingList.add(null);
		closingList.add("2020-01-20T12:00:00.000+02:00");
		closingList.add(null);
		closingList.add("2020-01-22T12:00:00.000+02:00");
		closingList.add(null);
		statusesList = new ArrayList<>();
		statusesList.add(1);
		statusesList.add(4);
		statusesList.add(0);
		statusesList.add(2);
		statusesList.add(1);
		statusesList.add(0);
		statusesList.add(4);
		statusesList.add(3);
	}
	
	/**
	 * Runs the original calculator calculateActivity function on our requests list.
	 */
	@SuppressWarnings("static-access")
	@Override
	public ArrayList<Double> getActivityData(String[] strArr) {
		DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd");
		DateTime dt1 = null, dt2 = null;
		try {
			dt1 = dtf.parseDateTime(strArr[0]);
			dt2 = dtf.parseDateTime(strArr[1]);
		} catch (IllegalArgumentException e) {
		}
		ArrayList<Double> report = calc.calculateActivity(dateList, closingList, statusesList, dt1, dt2);
		return report;
	}
}
