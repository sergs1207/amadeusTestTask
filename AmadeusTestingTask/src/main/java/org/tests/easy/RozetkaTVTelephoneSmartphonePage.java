package org.tests.easy;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RozetkaTVTelephoneSmartphonePage {
	private WebDriver driver;

	public RozetkaTVTelephoneSmartphonePage(WebDriver driver) {
		this.driver = driver;
	}

	public RozetkaTelephonePage pressTelephoneLink() {
		WebDriverWait wait = new WebDriverWait(driver, 30);

		driver.findElement(By.xpath(".//*[@id='menu_categories_left']/li[1]/h4/a")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@id='menu_categories_left']/li[1]/h4/a")));
		System.out.println("Page 2 is loaded and ready to be returned");
		return new RozetkaTelephonePage(driver);
	}

}
