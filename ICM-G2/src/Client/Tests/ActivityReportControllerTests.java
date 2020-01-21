package Client.Tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import Client.ChatClient;
import Client.Controllers.ManagerStatisticsController;
import javafx.scene.control.Label;

public class ActivityReportControllerTests {
	
	
	private ServerStub server;
	private ManagerStatisticsController msc;
	
	@Before
	public void setUp() {
		server=new ServerStub();
		msc = new ManagerStatisticsController();
		msc.initialize();
		msc.setOpenLabel(new Label());
	}
	
	@Test
	public void testPeriodReport_GoodValues() {
		ArrayList<Double> report = server.getReport();
		msc.updatePeropd(report);
		assertEquals("Open requests: 1",msc.getOpenLabel().getText());
		assertEquals("Freezed requests:  2",msc.getFreezeLabel().getText());
		assertEquals("Closed requests: 3",msc.getClosedLabel().getText());
		assertEquals("Rejected requests: 4",msc.getRejectedLabel().getText());
		assertEquals("Number of work days: 5.50",msc.getDaysLabel().getText());
	}
	
	


}


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
