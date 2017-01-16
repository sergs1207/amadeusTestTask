package org.tests.easyTest;

import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FromSelenium {

	String driverPath = "/AmadeusTestingTask/driver/geckodriver";
	public WebDriver driver;

	private String baseUrl;
	private boolean acceptNextAlert = true;
	
	private void showReport(String source){
		String firstRun = generateContent(source, "GTMEventsData.setGoodsData( ",
				"rozetkaEvents.setGoods");
		String secondRun = generateContent(firstRun, ", title:'", "', paren");
		System.out.println(secondRun);

	}

	private String generateContent(String pageContent, String startPhrase, String endPhrase) {
		String newPageContent = "";

		ArrayList<Integer> arrayOfGoodsStartIndex = new ArrayList<Integer>();
		ArrayList<Integer> arrayOfGoodsEndIndex = new ArrayList<Integer>();
		ArrayList<String> arrayOfJSON = new ArrayList<String>();

		for (int i = 0; i < pageContent.length() - startPhrase.length(); i++) {
			if (pageContent.substring(i, i + startPhrase.length()).equals(startPhrase))
				arrayOfGoodsStartIndex.add(i + startPhrase.length());

			if (pageContent.substring(i, i + endPhrase.length()).equals(endPhrase))
				arrayOfGoodsEndIndex.add(i);

		}
		for (int i = 0; i < arrayOfGoodsEndIndex.size(); i++) {
			arrayOfJSON.add(pageContent.substring(arrayOfGoodsStartIndex.get(i), arrayOfGoodsEndIndex.get(i)));

			newPageContent += arrayOfJSON.get(i) + "\n";
		}

		return newPageContent;

	}

	@Test
	public void testFromSelenium() throws Exception {
		System.setProperty("webdriver.gecko.driver",
				"/Users/sergiimaksiuta/Documents/workspace/AmadeusTestingTask/driver/geckodriver");
		WebDriver driver = new FirefoxDriver();
		
		baseUrl = "http://rozetka.com.ua/";

		driver.get(baseUrl + "/");
		WebDriverWait wait = new WebDriverWait(driver, 30);

		driver.findElement(By.xpath("//*[@id='3361']/a")).click();
		
		
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@id='menu_categories_left']/li[1]/h4/a")));
		driver.findElement(By.xpath(".//*[@id='menu_categories_left']/li[1]/h4/a")).click();
		
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@id='menu_categories_left']/li[1]/h4/a")));
		driver.findElement(By.xpath(".//*[@id='menu_categories_left']/li[1]/h4/a")).click();
		
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@id='page2']/a")));

		showReport(driver.getPageSource());
		driver.findElement(By.xpath(".//*[@id='page2']/a")).click();
		
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@id='page3']/a")));
		showReport(driver.getPageSource());
	
		 	}

	// @After
	// public void tearDown() throws Exception {
	//
	// String verificationErrorString = verificationErrors.toString();
	// if (!"".equals(verificationErrorString)) {
	// fail(verificationErrorString);
	// }
	// }

	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	private boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	private String closeAlertAndGetItsText() {
		try {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			if (acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			acceptNextAlert = true;
		}
	}

}
