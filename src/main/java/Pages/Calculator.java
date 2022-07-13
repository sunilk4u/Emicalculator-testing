package Pages;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Base.Base;

public class Calculator extends Base{
	
	By car = By.xpath("//a[text()='Car Loan']");
	By amt =By.id("loanamount");
	By slider =By.xpath("//*[@id='loaninterestslider']/span");
	By slider1 =By.xpath("//*[@id='loanamountslider']/span");
	By term =By.id("loanterm");
	By inter =By.xpath("//*[@id='emipaymenttable']/table/tbody/tr[2]/td[3]");
	By prin =By.xpath("//*[@id='emipaymenttable']/table/tbody/tr[2]/td[2]");
	By plus = By.id("year2022");
	By cal =By.id("menu-item-dropdown-2696");
	By home = By.xpath("//a[@title='Home Loan EMI Calculator']");
	By tenure =By.id("homeloanterm");
	By amount =By.id("homeprice");
	By loan = By.xpath("//a[@title='Loan Calculator']");
	By month =By.xpath("//label[text()='Mo ']");
	By year =By.xpath("//label[text()='Yr ']");
	By beforeamt = By.xpath("//*[@id='loansummary-totalamount']/p");
	By scale = By.xpath("//*[@id='loantermsteps']/span/span");
	By loanamt = By.xpath("//a[text()='Loan Amount ']");
	By loanten = By.xpath("//a[text()='Loan Tenure ']");
	By ten = By.xpath("//*[@id='loansummary-tenure']/p/span");
	
	public void emi() throws InterruptedException {
		logger = report.createTest("Getting interest and principle amount for Car Loan.");
		try {
		openURL("websiteURLKey");
		driver.findElement(car).click();
		driver.findElement(amt).clear();
		WebElement sliderXpath1 = driver.findElement(slider1);
		Actions act = new Actions(driver);
		act.dragAndDropBy(sliderXpath1, 483, 100).perform();
		Thread.sleep(5000);
		WebElement sliderXpath = driver.findElement(slider);
		act.dragAndDropBy(sliderXpath, 40, 54).perform();
		driver.findElement(term).clear();
		String interest=driver.findElement(inter).getText();
		String principal=driver.findElement(prin).getText();
		Thread.sleep(10000);
		System.out.println("The interest for the month is "+interest+" and principle amount is "+principal);
		Thread.sleep(5000);
		reportPass("Interest and Principle amount are obtained.");
		}catch(Exception e) {}
	}
	public void home() throws InterruptedException, IOException {
		logger = report.createTest("Obtaining the tables datas and storing it in excel sheet.");
		try {
		driver.findElement(cal).click();
		driver.findElement(home).click();
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(amount));
		driver.findElement(amount).clear();
		driver.findElement(amount).sendKeys("20,00,000");
		Thread.sleep(5000);
		File file = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\Data.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sh = workbook.createSheet("Year");
		WebElement table= driver.findElement(By.xpath("//table[@class='noextras']"));
		List<WebElement> totalrows=table.findElements(By.className("yearlypaymentdetails"));
		for(int row=0;row<=totalrows.size()-5;row++) {
			XSSFRow rowValue = sh.createRow(row);
			List<WebElement> totalcols=totalrows.get(row).findElements(By.tagName("td"));
			for(int col=0; col<totalcols.size(); col++)
			{
				String cellValue = totalcols.get(col).getText();
				System.out.print(cellValue + "\t");
				rowValue.createCell(col).setCellValue(cellValue);
			}
			System.out.println();
		}
		FileOutputStream fos = new FileOutputStream(file);
		workbook.write(fos);
		workbook.close();
		
		Thread.sleep(10000);
		reportPass("Table data are obtained and stored in the excel.");
		}catch(Exception e) {}
	}
	public void scale() {
		List<WebElement> befscale = driver.findElements(scale);
		for (int i = 0; i < befscale.size(); i++) {
			befscale.get(i).getText();
			System.out.print(befscale.get(i).getText()+" ");
		}
	}
	public void loan() throws InterruptedException {
		logger = report.createTest("Check all the fields and slider and check the amount change.");
		try {
		driver.findElement(cal).click();
		driver.findElement(loan).click();
		String beforeamount=driver.findElement(beforeamt).getText();
		System.out.println("*********EMI Calculator*********");
		System.out.println("The amount before the changes is: "+beforeamount);
		driver.findElement(amt).clear();
		driver.findElement(amt).sendKeys("15,00,000");
		WebElement sliderXpath = driver.findElement(slider);
		Actions act = new Actions(driver);
		act.dragAndDropBy(sliderXpath, 40, 54).perform();
		Thread.sleep(3000);
		String afteramount=driver.findElement(beforeamt).getText();
		System.out.println("The amount after the changes is: "+afteramount);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,300)", "");
		System.out.println("Year Scale:");
		scale();
		wait(20,month);
		act.moveToElement(driver.findElement(month)).click().perform();
		System.out.println(" ");
		System.out.println("Month Scale:");
		scale();
		System.out.println(" ");
		System.out.println("*****Loan Amount Calculator*****");
		act.moveToElement(driver.findElement(loanamt)).click().perform();
		System.out.println("Month Scale:");
		scale();
		wait(20,year);
		act.moveToElement(driver.findElement(year)).click().perform();
		System.out.println(" ");
		System.out.println("Year Scale:");
		scale();
		System.out.println(" ");
		System.out.println("*****Loan Tenure Calculator*****");
		act.moveToElement(driver.findElement(loanten)).click().perform();
		System.out.println("Loan Tenure Before: "+driver.findElement(ten).getText());
		WebElement sliderXpath1 = driver.findElement(slider);
		act.dragAndDropBy(sliderXpath1, 40, 14).perform();
		Thread.sleep(5000);
		System.out.println("Loan Tenure After: "+driver.findElement(ten).getText());
		reportPass("Loan Calcutaor fields are checked and amount changed is obtained.");
	    }catch(Exception e) {}
	}
}
