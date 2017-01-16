package org.tests.easy;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RozetkaTelephonePage {
	private WebDriver driver;

	public RozetkaTelephonePage(WebDriver driver) {
		this.driver = driver;
	}

	public RozetkaSmartphonePage pressSmartphoneLink() {
		WebDriverWait wait = new WebDriverWait(driver, 30);

		driver.findElement(By.xpath(".//*[@id='menu_categories_left']/li[1]/h4/a")).click();
	
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@id='page2']/a")));
		System.out.println("Page 3 is loaded and ready to be returned");
		return new RozetkaSmartphonePage(driver);
	}

}
