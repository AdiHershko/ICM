package Server.unittests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import Server.DataBaseController;
/**
 * The Class ActivityHistoryTests.
 * Checking if a report is saved correctly in the real SQLDB.
 */
public class ActivityHistoryTests {
	
	/** The delta, Using with delta to avoid depreciation when asserting equals for doubles. */
	double delta = 0.000000000000001;
	
	/**
	 * Inits the tests.
	 */
	@Before
	public void init() {
		//You need to run the dump before starting the test
		String temp = "jdbc:mysql://";
		temp += "localhost";
		temp += ":";
		temp += "3306";
		temp += "/";
		temp += "icm-g2";
		temp += "?useLegacyDatetimeCode=false&serverTimezone=UTC";
		DataBaseController.setUrl(temp);
		DataBaseController.setPassword("Aa123456");
		DataBaseController.setUsername("root");
		DataBaseController.Connect();
	}
	
	/**
	 * Test ask report from history, when no report is created
	 */
	@Test
	public void test_AskHistoryWithNoHistory() {
		try {//Sleep in order to save the report in a different time from other tests
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		DateTime currDate = new DateTime();
		String s = currDate.toString("dd/MM/yyyy HH:mm:ss");
		
		String[] arr = DataBaseController.getReportFromHistory(s);
		
		if (arr == null)
			assertTrue(true);
		else
			assertTrue(false);
	}
	
	/**
	 * Test ask report form history, after creating a report.
	 */
	@Test
	public void test_AskHistoryAfterReport() {
		try {//Sleep in order to save the report in a different time from other tests
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		String[] dates = {"2020-01-10","2020-01-16" };
		ArrayList<Double> askedRep = DataBaseController.getActivityData(dates);//Saving report in test from DB
		DateTime currDate = new DateTime();
		String s = currDate.toString("dd/MM/yyyy HH:mm:ss");//getting saving time
		DataBaseController.saveActivityData(dates, askedRep);//saving report in BF

		String[] reportFromHistory = null;
		reportFromHistory = DataBaseController.getReportFromHistory(s);//ask report from DB
		for (int i = 0; i < reportFromHistory.length; i++) {//check if same as saved in test
			switch(i) {
			case 0:
				assertEquals(reportFromHistory[i], s);
				break;
			case 1:
				assertEquals(reportFromHistory[i], dates[0]);
				break;
			case 2:
				assertEquals(reportFromHistory[i], dates[1]);
				break;
			case 3:
				assertEquals(Double.valueOf(reportFromHistory[i]), askedRep.get(0), delta);
				break;
			case 4:
				assertEquals(Double.valueOf(reportFromHistory[i]), askedRep.get(1), delta);
				break;
			case 5:
				assertEquals(Double.valueOf(reportFromHistory[i]), askedRep.get(2), delta);
				break;
			case 6:
				assertEquals(Double.valueOf(reportFromHistory[i]), askedRep.get(3), delta);
				break;
			case 7:
				assertEquals(Double.valueOf(reportFromHistory[i]), askedRep.get(4), delta);
				break;
			default:
				break;
			}
		}
	}
}
