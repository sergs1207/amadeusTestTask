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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class RozetkaSmartphonePage {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://h04.hvosting.ua/testclub";
	static final String USER = "testclub";
	static final String PASS = "testclub";

	private WebDriver driver;

	public RozetkaSmartphonePage(WebDriver driver) {
		this.driver = driver;
	}

	private String showReportTestCase1(String source) {
		String firstRun = generateContent(source, "GTMEventsData.setGoodsData( ", "rozetkaEvents.setGoods");
		String secondRun = generateContent(firstRun, ", title:'", "', paren");
		return secondRun;
	}

	private void showReportTestCase2(String source) {
		generateContentAndPopulateDB(source, "GTMEventsData.setGoodsData( ", "rozetkaEvents.setGoods");
	}

	private void generateContentAndPopulateDB(String pageContent, String startPhrase, String endPhrase) {

		Connection conn = null;
		Statement stmt = null;

		ArrayList<Integer> arrayOfGoodsStartIndex = new ArrayList<Integer>();
		ArrayList<Integer> arrayOfGoodsEndIndex = new ArrayList<Integer>();
		ArrayList<String> arrayOfJSON = new ArrayList<String>();

		for (int i = 0; i < pageContent.length() - startPhrase.length(); i++) {
			if (pageContent.substring(i, i + startPhrase.length()).equals(startPhrase))
				arrayOfGoodsStartIndex.add(i + startPhrase.length());

			if (pageContent.substring(i, i + endPhrase.length()).equals(endPhrase))
				arrayOfGoodsEndIndex.add(i);

		}
		// OPEN CONNECTION TO DB
		try {
			Class.forName("com.mysql.jdbc.Driver");

			// STEP 3: Open a connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// STEP 4: Execute a query
			stmt = conn.createStatement();

			// PLACE YOUR INSERTS HERE
			for (int i = 0; i < arrayOfGoodsEndIndex.size(); i++) {
				String tempTitle = "";
				String tempPrice = "";
				String tempTag = "";
				String sql = "";
				arrayOfJSON.add(pageContent.substring(arrayOfGoodsStartIndex.get(i), arrayOfGoodsEndIndex.get(i)));
				tempTag = getStringFieldValueFromJSON(": '", "'", "tag", arrayOfJSON.get(i));

				if (tempTag.equals("popularity")) {
					tempTitle = getStringFieldValueFromJSON(":'", "',", "title", arrayOfJSON.get(i));
					tempPrice = getStringFieldValueFromJSON(":'", "',", "price_usd", arrayOfJSON.get(i));

					sql = "INSERT INTO titleandprice VALUES ('" + tempTitle + "', '" + tempPrice + "')";
					System.out.println(sql);
					stmt.executeUpdate(sql);
				}
			}

		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
			} // do nothing
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try

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

	public String getStringFieldValueFromJSON(String delimiter, String endSymbol, String myTag, String sourceOfJSON) {
		myTag += delimiter;
		int startIndex = 0, endIndex = 0;

		for (int i = 0; i < sourceOfJSON.length() - myTag.length(); i++) {

			if (sourceOfJSON.substring(i, i + myTag.length()).equals(myTag)) {
				startIndex = i + myTag.length();

			}
		}

		for (int i = startIndex + 1; i < sourceOfJSON.length() - endSymbol.length(); i++) {

			if (sourceOfJSON.substring(i, i + endSymbol.length()).equals(endSymbol)) {
				endIndex = i;

				break;
			}
		}

		return sourceOfJSON.substring(startIndex, endIndex);

	}

	public void putFirstSecondThirdPageItemsNamesToFile(String fileName) throws IOException {
		FileWriter fw = new FileWriter(fileName);

		WebDriverWait wait = new WebDriverWait(driver, 30);

		System.out.println("DEBUG - Page 4 is loaded and ready to grab results");
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

	public void putFirstSecondThirdPageTopSalesItemsNamesPricesToDB() {
		WebDriverWait wait = new WebDriverWait(driver, 30);

		System.out.println("DEBUG - Page 4 is loaded and ready to grab results");
		showReportTestCase2(driver.getPageSource());

		// ;
		driver.findElement(By.xpath(".//*[@id='page2']/a")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@id='page3']/a")));
		showReportTestCase2(driver.getPageSource());

		driver.findElement(By.xpath(".//*[@id='page3']/a")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@id='page4']/a")));
		showReportTestCase2(driver.getPageSource());

	}

	public void extractDataFromDBSendEmailReportPeriodically(String emailForReports) {

		Connection conn = null;
		Statement stmt = null;
		String emailBody = "";

		
		try {
			
			Class.forName("com.mysql.jdbc.Driver");

			
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
		
			stmt = conn.createStatement();

			String sql = "SELECT * FROM titleandprice";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String title = rs.getString("title");
				String price = rs.getString("price");

				emailBody += "Title: " + title + ", Price: " + price + "\n";

			}
			rs.close();

		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
			} // do nothing
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try

		// END OPEN CONNECTION TO DB
		// SEND E_MAIL WITH REPORT

		final String username = "enigma.in.my.heart@gmail.com";
		final String password = "incorporated";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailForReports));
			message.setSubject("Report on data extraction from database");
			message.setText("Dear Tester," + "\n\n PLease find report attached!\n" + emailBody);

			Transport.send(message);

			

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

		

	}

	

}
