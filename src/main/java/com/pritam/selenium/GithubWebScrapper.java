package com.pritam.selenium;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

// https://www.javatpoint.com/selenium-webdriver-running-test-on-chrome-browser
// https://selenium.dev/maven/
// https://chromedriver.chromium.org/downloads
// https://www.journaldev.com/25837/download-selenium-jars-configure-eclipse

public class GithubWebScrapper {

	static String BASEURL = "https://github.com/";
	static String USERNAME = "pritamkhose"; // "StephenGrider"; // "nelsonic";
	static String ChromeDriverPath = "E:\\Software\\Chromedriver\\chromedriver.exe";
	
	
	static LinkedHashMap<String, Object> outputHM = new LinkedHashMap<String, Object>();

	public static void main(String[] args) {
		// System Property for Chrome Driver
		System.setProperty("webdriver.chrome.driver", ChromeDriverPath);

		// Instantiate a ChromeDriver class.
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		// Maximize the browser
		// driver.manage().window().maximize();

		// Scroll down the webpage by 5000 pixels
		// JavascriptExecutor js = (JavascriptExecutor) driver;

		getOverview(driver);
		getFollowers(driver);
		getFollowing(driver);
		getRepositories(driver);
		getStars(driver);

		saveResult();

		driver.close();
	}

	private static void getStars(WebDriver driver) {
		driver.navigate().to(BASEURL + USERNAME + "?tab=stars");

		WebElement maindiv = driver.findElement(By.xpath("//*"));

		Document doc = Jsoup.parse(maindiv.getAttribute("innerHTML"));
		Elements nl = doc.getElementsByClass("application-main").get(0).children().get(0).children().get(0).children()
				.get(3).children().get(2).children().get(0).children().get(0).children();

		ArrayList<HashMap<String, Object>> aList = new ArrayList<HashMap<String, Object>>();
		for (int i = 1; i < nl.size() -1; i++) {
			HashMap<String, Object> hm = new HashMap<String, Object>();
			String[] s = nl.get(i).children().get(0).text().split(" / ", -1);
			hm.put("username", s[0]);
			hm.put("repository", s[1]);
			hm.put("description", nl.get(i).children().get(1).text());
			hm.put("info", nl.get(i).children().get(2).text());
			
			if (!(hm.isEmpty())) {
				aList.add(hm);
			}
		}

		outputHM.put("stars_top30", aList);

	}

	private static void getRepositories(WebDriver driver) {
		driver.navigate().to(BASEURL + USERNAME + "?tab=repositories");

		WebElement maindiv = driver.findElement(By.xpath("//*"));

		Document doc = Jsoup.parse(maindiv.getAttribute("innerHTML"));
		Elements nl = doc.getElementById("user-repositories-list").children().get(0).children();

		ArrayList<HashMap<String, Object>> aList = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < nl.size(); i++) {
			HashMap<String, Object> hm = new HashMap<String, Object>();
			hm.put("name", nl.get(i).children().get(0).children().get(0).text());
			hm.put("description", nl.get(i).children().get(0).children().get(1).text());

			Element obj = nl.get(i).children().get(0).children().get(2);
			hm.put("info", obj.text());
			hm.put("language", obj.children().get(0).text());
			hm.put("starcount", obj.getElementsByClass("muted-link").text());

			if (!(hm.isEmpty())) {
				aList.add(hm);
			}
		}

		outputHM.put("repositories_top30", aList);
	}

	private static void getFollowers(WebDriver driver) {

		driver.navigate().to(BASEURL + USERNAME + "?tab=followers");

		WebElement maindiv = driver.findElement(By.xpath("//*"));

		Document doc = Jsoup.parse(maindiv.getAttribute("innerHTML"));
		Elements nl = doc.getElementById("js-pjax-container").children().get(0).children().get(3).children().get(2)
				.children();

		ArrayList<HashMap<String, Object>> aList = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < nl.size(); i++) {
			HashMap<String, Object> hm = new HashMap<String, Object>();

			if (nl.get(i).hasClass("d-table")) {
				hm.put("avatarURL", nl.get(i).children().get(0).getElementsByClass("avatar").attr("src"));

				Element a = nl.get(i).children().get(1).children().get(0);
				hm.put("fullname", a.children().get(0).select("span").text());
				hm.put("username", a.children().get(1).select("span").text());

				Elements b = nl.get(i).children().get(1).children();
//				for (int j = 0; j < b.size(); j++) {
//					hm.put("bio " + j, b.get(j).toString());
//				}
				if (b.size() > 1) {
					hm.put("bio", b.get(1).text());
				}
				if (b.size() > 2) {
					String org = b.get(2).select("span").text();
					hm.put("organization", org);
					hm.put("location", b.get(2).text().replace(org, ""));
				}
			}

			if (!(hm.isEmpty())) {
				aList.add(hm);
			}
		}
		outputHM.put("followers_top50", aList);

	}

	private static void getFollowing(WebDriver driver) {

		driver.navigate().to(BASEURL + USERNAME + "?tab=following");

		WebElement maindiv = driver.findElement(By.xpath("//*"));

		Document doc = Jsoup.parse(maindiv.getAttribute("innerHTML"));
		Elements nl = doc.getElementById("js-pjax-container").children().get(0).children().get(3).children().get(2)
				.children();

		ArrayList<HashMap<String, Object>> aList = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < nl.size(); i++) {
			HashMap<String, Object> hm = new HashMap<String, Object>();
//    		hm.put("htmltext "+i, nl.get(i).toString());

			if (nl.get(i).hasClass("d-table")) {
				hm.put("avatarURL", nl.get(i).children().get(0).getElementsByClass("avatar").attr("src"));

				Element a = nl.get(i).children().get(1).children().get(0);
				hm.put("fullname", a.children().get(0).select("span").text());
				hm.put("username", a.children().get(1).select("span").text());

				Elements b = nl.get(i).children().get(1).children();
//				for (int j = 0; j < b.size(); j++) {
//					hm.put("bio " + j, b.get(j).toString());
//				}
				if (b.size() > 1) {
					hm.put("bio", b.get(1).text());
				}
				if (b.size() > 2) {
					String org = b.get(2).select("span").text();
					hm.put("organization", org);
					hm.put("location", b.get(2).text().replace(org, ""));
				}
			}

			if (!(hm.isEmpty())) {
				aList.add(hm);
			}
		}
		outputHM.put("following_top50", aList);

	}

	private static void getOverview(WebDriver driver) {

		// Launch Web site
		driver.navigate().to(BASEURL + USERNAME);

		LinkedHashMap<String, Object> hm = new LinkedHashMap<String, Object>();
		hm.put("fullname", driver.findElement(By.className("p-name")).getText());
		hm.put("username", driver.findElement(By.className("p-nickname")).getText());
		hm.put("user-profile-bio", driver.findElement(By.className("p-note")).getText());

		hm.put("avtarURL", driver.findElement(By.xpath("//*[@id=\"js-pjax-container\"]/div/div[2]/div[2]/div[1]/a/img"))
				.getAttribute("src"));

		String[] overview = driver.findElement(By.className("UnderlineNav-body")).getText().split(" ", -1);
//    	System.out.println(Arrays.toString(overview));
		hm.put("Repositories", overview[1].split("\r", -1)[0].split("\n", -1)[0]);
		hm.put("Projects", overview[2].split("\r", -1)[0].split("\n", -1)[0]);
		hm.put("Stars", overview[3].split("\r", -1)[0].split("\n", -1)[0]);
		hm.put("Followers", overview[4].split("\r", -1)[0].split("\n", -1)[0]);
		hm.put("Following", overview[5].split("\r", -1)[0]);

		WebElement obj;
		try {
			obj = driver.findElement(By.className("p-label"));
			hm.put("location", obj.getText());
		} catch (Exception e) {
		}

		try {
			obj = driver.findElement(By.className("u-email"));
			hm.put("email", obj.getText());
		} catch (Exception e) {
		}

		outputHM = hm;

	}

	private static void saveResult() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		System.out.println(gson.toJson(outputHM));
		try {
			PrintWriter out = new PrintWriter("output.json");
			out.append(gson.toJson(outputHM));
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
