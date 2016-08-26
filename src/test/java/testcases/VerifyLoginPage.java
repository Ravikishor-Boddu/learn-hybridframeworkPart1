package testcases;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import factory.BrowserFactory;
import factory.DataProviderFactory;
import pages.HomePage;
import pages.LogInPage;

public class VerifyLoginPage {
	
	WebDriver driver;
	ExtentReports report;
	ExtentTest logger;
	
	@BeforeTest
	public void setUp()
	{
		report=new ExtentReports("./Reports/HomePageReport.html");
		
		logger=report.startTest("Verify Home Page Title");		
	
		driver=BrowserFactory.getBrowser();
		
		driver.get(DataProviderFactory.getConfig().getApplicationURL());
		
		logger.log(LogStatus.INFO, "Application up & running");
		
	}
	
	@Test
	public void testHomePage()
	{
		
		HomePage home=PageFactory.initElements(driver, HomePage.class);
		
		String title=home.getApplicationTitle();
		
		Assert.assertTrue(title.contains("GCR"));
		
		logger.log(LogStatus.PASS, "Home Page Title verified successfully");
		
		home.clickOnLoginLink();
		
		logger.log(LogStatus.INFO, "Clicked On Log In Link");
		
		LogInPage login=PageFactory.initElements(driver, LogInPage.class);
		
		login.logInApplication(DataProviderFactory.getExcel().getData(0, 0, 0),DataProviderFactory.getExcel().getData(0, 0, 1));
		
		login.verifyLogOutLink();
		
		logger.log(LogStatus.PASS, "Log in successfull");
		
		//System.out.println("Application Page Title is: "+title);	
		
	}
	
	@AfterTest
	public void tearDown()
	{
		
		BrowserFactory.closeBrowser(driver);
		
		logger.log(LogStatus.INFO, "Browser closed");
		
		report.endTest(logger);
		
		report.flush();
	}

}
