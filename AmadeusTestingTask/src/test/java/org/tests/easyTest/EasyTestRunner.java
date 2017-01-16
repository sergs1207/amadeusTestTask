package org.tests.easyTest;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.tests.easy.*;



public class EasyTestRunner {
	
	private WebDriver driver;
    private String outputFile = "/Users/sergiimaksiuta/Documents/Repo/AmadeusTestingTask/AmadeusTestingTask/driver/listAllModelNamesTask1.txt";

	
	
	@Before
	public void startTesting() throws Exception{
       String driverPath = "/Users/sergiimaksiuta/Documents/Repo/AmadeusTestingTask/AmadeusTestingTask/driver/geckodriver";
		System.setProperty("webdriver.gecko.driver", driverPath);
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		driver.get("http://rozetka.com.ua");
		driver.manage().window().maximize();
		}
	@After
	public void stopTesting() throws Exception{
       driver.quit();
		}
	
	@Test
	public void testProfileUserName() throws IOException {
		RozetkaMainPage home = new RozetkaMainPage(driver);
		RozetkaTVTelephoneSmartphonePage tvTelephoneSmartphonePage = home.pressCategory1Link(); 
		RozetkaTelephonePage telephonePage = tvTelephoneSmartphonePage.pressTelephoneLink();
		RozetkaSmartphonePage smartphonePage = telephonePage.pressSmartphoneLink();
		smartphonePage.putFirstSecondThirdPageItemsNamesToFile(outputFile);
		
	}
}
