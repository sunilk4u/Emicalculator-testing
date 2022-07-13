package TestSuites;

import java.io.IOException;	

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Base.Base;
import Pages.Calculator;

public class TestCases extends Base {

	Calculator cw = new Calculator();

	@BeforeTest
	public void invokeBrowser() {
		logger = report.createTest("Executing Test Cases");

		cw.invokeBrowser();
		reportPass("Browser is Invoked");
	}

	@Test(priority = 1)
	public void emicalc() throws InterruptedException {

		cw.emi();
		reportPass("Interest and principal amounts are retrieved");
	}

	@Test(priority = 2)
	public void homeloan() throws IOException, InterruptedException {
		cw.home();
		reportPass("Home loan structured is obtained and stored in excel");
	}

	@Test(priority = 3)
	public void loancalc() throws InterruptedException, IOException {
		cw.loan();
		reportPass("Loan Calcutaor fields are checked and amount changed is obtained");
	}

	@AfterTest
	public void closeBrowser() {
		cw.endReport();
		cw.closeBrowser();
	}

}
