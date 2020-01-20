package Server.Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Server.Calculator;

class PeriodReportTest {
	Calculator calc;
	DataBaseControllerStub dbStub;

	@BeforeEach
	void setUp() throws Exception {
		 dbStub = new DataBaseControllerStub();
		 calc = new Calculator();
		
	}

	@Test
	void test() {
		fail("Not yet implemented");
	}
	
	private class DataBaseControllerStub implements IDataBaseController{
		ArrayList<String> dateList = new ArrayList<>(); 
		@Override
		public ArrayList<Double> getActivityData(String[] strArr) {
			
			return calc.calculateActivity(Datelist, ClosingDatelist, Statuses, dt1, dt2)
		}


		
	}

}
