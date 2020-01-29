package com.pritam.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

// https://www.javatpoint.com/selenium-webdriver-running-test-on-chrome-browser
// https://selenium.dev/maven/
// https://chromedriver.chromium.org/downloads
// https://www.journaldev.com/25837/download-selenium-jars-configure-eclipse

public class Javatpoint {
	public static void main(String[] args) {
		System.out.println("Hello World!");

		// System Property for Chrome Driver
		// E:\Software\chromedriver_win32 D:\\ChromeDriver
		System.setProperty("webdriver.chrome.driver", "E:\\Software\\Chromedriver\\chromedriver.exe");

		// Instantiate a ChromeDriver class.
		WebDriver driver = new ChromeDriver();

		// Launch Website
		driver.navigate().to("http://www.javatpoint.com/");

		// Maximize the browser
		driver.manage().window().maximize();

		// Scroll down the webpage by 5000 pixels
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("scrollBy(0, 5000)");

		// Click on the Search button
		driver.findElement(By.linkText("Core Java")).click();

		driver.close();
	}
}
