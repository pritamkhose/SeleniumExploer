package com.pritam.appium;

import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.*;

public class CalculatorTest {
	WebDriver driver;

	@BeforeClass
	public void setUp() throws MalformedURLException {
		// Set up desired capabilities and pass the Android app-activity and app-package
		// to Appium
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("BROWSER_NAME", "Android");
		capabilities.setCapability("VERSION", "9");
		capabilities.setCapability("deviceName", "emulator-5554");
		capabilities.setCapability("platformName", "Android");

		capabilities.setCapability("appPackage", "com.android.calculator2");
// This package name of your app (you can get it from apk info app)
		capabilities.setCapability("appActivity", "com.android.calculator2.Calculator"); // This is Launcher activity of
																							// your app (you can get it
																							// from apk info app)
//Create RemoteWebDriver instance and connect to the Appium server
		// It will launch the Calculator App in Android Device using the configurations
		// specified in Desired Capabilities
		driver = new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
	}

	@Test
	public void testCal() throws Exception {
		// locate the Text on the calculator by using By.name()
		WebElement two = driver.findElement(By.name("2"));
		two.click();
		WebElement plus = driver.findElement(By.name("+"));
		plus.click();
		WebElement four = driver.findElement(By.name("4"));
		four.click();
		WebElement equalTo = driver.findElement(By.name("="));
		equalTo.click();
		// locate the edit box of the calculator by using By.tagName()
		WebElement results = driver.findElement(By.tagName("EditText"));
		// Check the calculated value on the edit box
		assert results.getText().equals("6") : "Actual value is : " + results.getText()
				+ " did not match with expected value: 6";
		
		
//		MobileElement el6 = (MobileElement) driver.findElementById("com.android.calculator2:id/digit_2");
//		el6.click();
//		MobileElement el7 = (MobileElement) driver.findElementByAccessibilityId("plus");
//		el7.click();
//		MobileElement el8 = (MobileElement) driver.findElementById("com.android.calculator2:id/digit_5");
//		el8.click();
//		MobileElement el9 = (MobileElement) driver.findElementByAccessibilityId("equals");
//		el9.click();
//		MobileElement el10 = (MobileElement) driver.findElementById("com.android.calculator2:id/result");
//		el10.click();


	}

	@AfterClass
	public void teardown() {
		// close the app
		driver.quit();
	}
}