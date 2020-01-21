package Client.unittests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;

import Client.Controllers.ManagerStatisticsController;
import javafx.application.Platform;

/**
 * The Class ActivityReportControllerTests. Checking if the controller is
 * creating the correct labels from the report array.
 */
public class ActivityReportControllerTests extends ApplicationTest {

	/** The server stub for testing. */
	private ServerStub server;

	/**
	 * Sets the tests, creating FXML thread and stub server.
	 *
	 * @throws Exception the exception
	 */
	@Before
	public void setUp() throws Exception {
		ApplicationTest.launch(ManagerStatisticsController.class);
		server = new ServerStub();
	}

	/**
	 * After test - killing FXML thread.
	 *
	 * @throws TimeoutException the timeout exception
	 */
	@After
	public void afterTest() throws TimeoutException {
		FxToolkit.hideStage();

	}

	/**
	 * Test period report label has good values after getting the array.
	 */
	@Test
	public void testPeriodReport_GoodValues() {
		ArrayList<Double> report = server.getReport();
		Platform.runLater(new Runnable() {

			public void run() {
				ManagerStatisticsController._ins.updatePeropd(report);
				assertEquals("Open requests: 1", ManagerStatisticsController._ins.getOpenLabel().getText());
				assertEquals("Closed requests: 2", ManagerStatisticsController._ins.getClosedLabel().getText());
				assertEquals("Freezed requests: 3", ManagerStatisticsController._ins.getFreezeLabel().getText());
				assertEquals("Rejected requests: 4", ManagerStatisticsController._ins.getRejectedLabel().getText());
				assertEquals("Number of work days: 5.50", ManagerStatisticsController._ins.getDaysLabel().getText());
			}

		});
	}

}

/**
 * The Class ServerStub.
 */
class ServerStub {
	public ArrayList<Double> report;

	public ServerStub() {
		System.out.println("Server connected");
		report = new ArrayList<>();
		setReportValues();
	}

	private void setReportValues() {
		report.add(1.0);
		report.add(2.0);
		report.add(3.0);
		report.add(4.0);
		report.add(5.5);
	}

	public ArrayList<Double> getReport() {
		return report;
	}

}
