package org.tests.easyTest;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.tests.easy.*;



public class TestRunner {
	
	private WebDriver driver;
	
	
	@Before
	public void startTesting() throws Exception{
       String driverPath = "/Users/sergiimaksiuta/Documents/Repo/AmadeusTestingTask/AmadeusTestingTask/driver/geckodriver";
		System.setProperty("webdriver.gecko.driver", driverPath);
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.MILLISECONDS);
		driver.get("http://rozetka.com.ua");
		driver.manage().window().maximize();
		}
	@After
	public void stopTesting() throws Exception{
       //driver.quit();
		}
	
	@Test
	public void testProfileUserName() {
		RozetkaMainPage home = new RozetkaMainPage(driver);
		RozetkaTVTelephoneSmartphonePage tvTelephoneSmartphonePage = home.pressCategory1Link(); 
		RozetkaTelephonePage telephonePage = tvTelephoneSmartphonePage.pressTelephoneLink();
		RozetkaSmartphonePage smartphonePage = telephonePage.pressSmartphoneLink();
		smartphonePage.getFileList();
		//smartphonePage.
	}
}
