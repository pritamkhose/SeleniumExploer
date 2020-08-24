package com.pritam.selenium;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

//https://www.browserstack.com/guide/run-selenium-tests-using-firefox-driver
// https://github.com/mozilla/geckodriver/releases/tag/v0.27.0

public class Firefox {

	public static void main(String[] args) {
		System.out.println("Hello World!");

		// Setting system properties of FirefoxDriver
		// E:\Software\chromedriver_win32 D:\\ChromeDriver
		System.setProperty("webdriver.gecko.driver", "E:\\Software\\Chromedriver\\geckodriver.exe");

		// Instantiate a ChromeDriver class.
		WebDriver driver = new FirefoxDriver(); // Creating an object of FirefoxDriver
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//		google(driver);
//		guru99(driver);
		javaTpoint(driver);


		driver.close();
	}

	private static void javaTpoint(WebDriver driver) {
		// Launch Website
		driver.navigate().to("http://www.javatpoint.com/");

		// Maximize the browser
		driver.manage().window().maximize();

		// Scroll down the webpage by 5000 pixels
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("scrollBy(0, 5000)");

		// Click on the Search button
		driver.findElement(By.linkText("Core Java")).click();

	}

//	https://www.guru99.com/first-webdriver-script.html
	private static void guru99(WebDriver driver) {
		String baseUrl = "http://demo.guru99.com/test/newtours/";
		String expectedTitle = "Welcome: Mercury Tours";
		String actualTitle = "";

		// launch Fire fox and direct it to the Base URL
		driver.get(baseUrl);

		// get the actual value of the title
		actualTitle = driver.getTitle();

		/*
		 * compare the actual title of the page with the expected one and print the
		 * result as "Passed" or "Failed"
		 */
		if (actualTitle.contentEquals(expectedTitle)) {
			System.out.println("Test Passed!");
		} else {
			System.out.println("Test Failed");
		}

	}

	private static void google(WebDriver driver) {
		// Launch Website
		// driver.navigate().to("http://www.javatpoint.com/");
		driver.get("https://www.google.com/");

		// Maximize the browser
		driver.manage().window().maximize();

		// Scroll down the webpage by 5000 pixels
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("scrollBy(0, 5000)");

		driver.findElement(By.name("q")).sendKeys("Browserstack Guide"); // name locator for text box
		WebElement searchbutton = driver.findElement(By.name("btnK"));// name locator for google search
		searchbutton.click();

	}

}
