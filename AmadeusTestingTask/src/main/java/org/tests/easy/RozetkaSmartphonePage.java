package org.tests.easy;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class RozetkaSmartphonePage {
	private WebDriver driver;

	private String showReport(String source) {
		String firstRun = generateContent(source, "GTMEventsData.setGoodsData( ", "rozetkaEvents.setGoods");
		String secondRun = generateContent(firstRun, ", title:'", "', paren");

		return secondRun;
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

	public RozetkaSmartphonePage(WebDriver driver) {
		this.driver = driver;
	}

	public void putFirstSecondThirdPageItemsNamesToFile(String fileName) throws IOException {
		File fout = new File(fileName);
		FileOutputStream fos = new FileOutputStream(fout);

		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		WebDriverWait wait = new WebDriverWait(driver, 30);

		System.out.println("Page 4 is loaded and ready to grab results");
		bw.write(showReport(driver.getPageSource()));
		// ;
		driver.findElement(By.xpath(".//*[@id='page2']/a")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@id='page3']/a")));
		bw.write(showReport(driver.getPageSource()));

		driver.findElement(By.xpath(".//*[@id='page3']/a")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@id='page4']/a")));
		bw.write(showReport(driver.getPageSource()));
		bw.close();

	}

}
