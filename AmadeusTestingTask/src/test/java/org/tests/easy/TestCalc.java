package org.tests.easy;

import org.junit.*;

import org.junit.rules.Timeout;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.tests.easy.Calculator;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeThat;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class TestCalc {
	private WebDriver driver;
	private String outputFile = "/Users/sergiimaksiuta/Documents/Repo/AmadeusTestingTask/AmadeusTestingTask/driver/listAllModelNamesTask1.txt";
	
	@AfterClass
	public void stopTesting() throws Exception{
       driver.quit();
		}
	@Before
	public void createObject() throws Exception {

		String driverPath = "/Users/sergiimaksiuta/Documents/Repo/AmadeusTestingTask/AmadeusTestingTask/driver/geckodriver";
		System.setProperty("webdriver.gecko.driver", driverPath);
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		driver.get("http://rozetka.com.ua");
		driver.manage().window().maximize();
	}

	@Test
	public void TestScenario1() throws IOException {
		RozetkaMainPage home = new RozetkaMainPage(driver);
		RozetkaTVTelephoneSmartphonePage tvTelephoneSmartphonePage = home.pressCategory1Link();
		RozetkaTelephonePage telephonePage = tvTelephoneSmartphonePage.pressTelephoneLink();
		RozetkaSmartphonePage smartphonePage = telephonePage.pressSmartphoneLink();
		smartphonePage.putFirstSecondThirdPageItemsNamesToFile(outputFile);

		

	}

	@Test
	public void TestScenario2() throws IOException {
		RozetkaMainPage home = new RozetkaMainPage(driver);
		RozetkaTVTelephoneSmartphonePage tvTelephoneSmartphonePage = home.pressCategory1Link();
		RozetkaTelephonePage telephonePage = tvTelephoneSmartphonePage.pressTelephoneLink();
		RozetkaSmartphonePage smartphonePage = telephonePage.pressSmartphoneLink();
		smartphonePage.putFirstSecondThirdPageTopSalesItemsNamesPricesToDB();

		driver.quit();

	}

	@Test
	public void TestScenario3() throws IOException {
		RozetkaMainPage home = new RozetkaMainPage(driver);
		RozetkaTVTelephoneSmartphonePage tvTelephoneSmartphonePage = home.pressCategory1Link();
		RozetkaTelephonePage telephonePage = tvTelephoneSmartphonePage.pressTelephoneLink();
		RozetkaSmartphonePage smartphonePage = telephonePage.pressSmartphoneLink();
		smartphonePage.putFirstSecondThirdPageTopSalesItemsNamesPricesToDB();
		smartphonePage.extractDataFromDBSendEmailReportPeriodically("sergs1207@gmail.com");
		driver.quit();
	}

}
