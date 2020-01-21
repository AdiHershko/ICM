package Server.Tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import Server.DataBaseController;

public class ActivityHistoryTests {
	double delta = 0.000000000000001;
	
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
	
	@Test
	public void test_AskHistoryWithNoHistory() {
		try {
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
	
	@Test
	public void test_AskHistoryAfterReport() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		String[] dates = {"2020-01-10","2020-01-16" };
		ArrayList<Double> askedRep = DataBaseController.getActivityData(dates);
		DateTime currDate = new DateTime();
		String s = currDate.toString("dd/MM/yyyy HH:mm:ss");
		DataBaseController.saveActivityData(dates, askedRep);

		String[] reportFromHistory = null;
		reportFromHistory = DataBaseController.getReportFromHistory(s);
		for (int i = 0; i < reportFromHistory.length; i++) {
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
