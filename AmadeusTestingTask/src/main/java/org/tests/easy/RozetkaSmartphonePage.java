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
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class RozetkaSmartphonePage {
	private WebDriver driver;

	private String showReportTestCase1(String source) {
		String firstRun = generateContent(source, "GTMEventsData.setGoodsData( ", "rozetkaEvents.setGoods");
		String secondRun = generateContent(firstRun, ", title:'", "', paren");

		return secondRun;
	}
	private String showReportTestCase2(String source) {
		String firstRun = generateContent(source, "GTMEventsData.setGoodsData( ", "rozetkaEvents.setGoods");
		String secondRun = generateContentAndPopulateDB(firstRun, ", title:'", "', paren");

		return secondRun;
	}


	private String generateContentAndPopulateDB(String pageContent, String startPhrase, String endPhrase) {
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

	public RozetkaSmartphonePage() {
		// TODO Auto-generated constructor stub
	}

	public void putFirstSecondThirdPageItemsNamesToFile(String fileName) throws IOException {
		FileWriter fw = new FileWriter(fileName);
		
		WebDriverWait wait = new WebDriverWait(driver, 30);

		System.out.println("Page 4 is loaded and ready to grab results");
		System.out.println(showReportTestCase1(driver.getPageSource()));
		fw.write(showReportTestCase1(driver.getPageSource()));
		// ;
		driver.findElement(By.xpath(".//*[@id='page2']/a")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@id='page3']/a")));
		System.out.println(showReportTestCase1(driver.getPageSource()));
		fw.write(showReportTestCase1(driver.getPageSource()));

		driver.findElement(By.xpath(".//*[@id='page3']/a")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@id='page4']/a")));
		System.out.println(showReportTestCase1(driver.getPageSource()));
		fw.write(showReportTestCase1(driver.getPageSource()));
		fw.close();

	}
	public void putFirstSecondThirdPageTopSalesItemsNamesPricesToDB(String dbConnectionString, String dbName, String dbPassword, String dbSchema) {
		WebDriverWait wait = new WebDriverWait(driver, 30);

		System.out.println("Page 4 is loaded and ready to grab results");
		System.out.println(showReportTestCase2(driver.getPageSource()));
		
		// ;
		driver.findElement(By.xpath(".//*[@id='page2']/a")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@id='page3']/a")));
		System.out.println(showReportTestCase2(driver.getPageSource()));
		

		driver.findElement(By.xpath(".//*[@id='page3']/a")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@id='page4']/a")));
		System.out.println(showReportTestCase2(driver.getPageSource()));
		
		
	}
	public String getStringFieldValueFromJSON(String delimiter, String endSymbol, String myTag, String sourceOfJSON){
		myTag+=delimiter;
		int startIndex=0, endIndex=0; 
		
		
		for(int i=0; i<sourceOfJSON.length()-myTag.length();i++){
			
			if (sourceOfJSON.substring(i, i+myTag.length()).equals(myTag)){
				startIndex=i+myTag.length();
				
			}
		}
	
		
		for(int i = startIndex+1; i<sourceOfJSON.length()-endSymbol.length();i++){
			
			if (sourceOfJSON.substring(i, i+endSymbol.length()).equals(endSymbol)){
					endIndex=i;
					
					break;
				}
			}
		
		
			
		return sourceOfJSON.substring(startIndex, endIndex);
				
	}

	
		
//	public static void main(String [] args){
//		RozetkaSmartphonePage myInstance = new RozetkaSmartphonePage();
//		System.out.println(myInstance.getStringFieldValueFromJSON(": ",",","id", "{ id: 12049360, title:'Motorola Moto Z (XT1650-03) Lunar Gray', parent_id:80003, parent:'Мобильные телефоны', producer_id:22, producer:'Motorola', price_usd:'610', price_usd_raw:'610', merchant_id:'1', seller_id:'5', state:'new', state_group_context: 'only new', top_parent_id:80257 ,tag: 'action' }"));
//		System.out.println(myInstance.getStringFieldValueFromJSON(":'","',","title", "{ id: 12049360, title:'Motorola Moto Z (XT1650-03) Lunar Gray', parent_id:80003, parent:'Мобильные телефоны', producer_id:22, producer:'Motorola', price_usd:'610', price_usd_raw:'610', merchant_id:'1', seller_id:'5', state:'new', state_group_context: 'only new', top_parent_id:80257 ,tag: 'action' }"));
//		System.out.println(myInstance.getStringFieldValueFromJSON(":'","',","price_usd_raw", "{ id: 12049360, title:'Motorola Moto Z (XT1650-03) Lunar Gray', parent_id:80003, parent:'Мобильные телефоны', producer_id:22, producer:'Motorola', price_usd:'610', price_usd_raw:'610', merchant_id:'1', seller_id:'5', state:'new', state_group_context: 'only new', top_parent_id:80257 ,tag: 'action' }"));
//		System.out.println(myInstance.getStringFieldValueFromJSON(":'","',","price_usd", "{ id: 12049360, title:'Motorola Moto Z (XT1650-03) Lunar Gray', parent_id:80003, parent:'Мобильные телефоны', producer_id:22, producer:'Motorola', price_usd:'610', price_usd_raw:'610', merchant_id:'1', seller_id:'5', state:'new', state_group_context: 'only new', top_parent_id:80257 ,tag: 'action' }"));
//		System.out.println(myInstance.getStringFieldValueFromJSON(":"," ,","top_parent_id", "{ id: 12049360, title:'Motorola Moto Z (XT1650-03) Lunar Gray', parent_id:80003, parent:'Мобильные телефоны', producer_id:22, producer:'Motorola', price_usd:'610', price_usd_raw:'610', merchant_id:'1', seller_id:'5', state:'new', state_group_context: 'only new', top_parent_id:80257 ,tag: 'action' }"));
//		System.out.println(myInstance.getStringFieldValueFromJSON(": '","'","tag", "{ id: 12049360, title:'Motorola Moto Z (XT1650-03) Lunar Gray', parent_id:80003, parent:'Мобильные телефоны', producer_id:22, producer:'Motorola', price_usd:'610', price_usd_raw:'610', merchant_id:'1', seller_id:'5', state:'new', state_group_context: 'only new', top_parent_id:80257 ,tag: 'action' }"));
//		  
//		
//		
//	}

}
