package Server.Tests;

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

import Server.Calculator;


/**
 * The Class PeriodReportTest.
 */
public class PeriodReportTest {

	/** The db stub. */
	DataBaseControllerStub dbStub;

	/**
	 * Sets the up.
	 *
	 * @throws Exception when setup fails
	 */
	@Before
	public void setUp() throws Exception {
		 dbStub = new DataBaseControllerStub(); // will have 8 requests

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
	ArrayList<String> dateList;
	ArrayList<String> closingList;
	ArrayList<Integer> statusesList;
	Calculator calc;
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
		return calc.calculateActivity(dateList, closingList, statusesList, dt1, dt2);
	}


}
