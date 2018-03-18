package com.qatestlab.homework5;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class TestSuiteBase {

	String getBrowserName;
	WebDriver driver;
	MainPage mp;
	WebDriverWait wait;
	Logger log = LogManager.getLogger("TestHomeWork5");

	@Parameters({ "platform", "browser", "url" })
	@BeforeClass(alwaysRun = true)
	public void setUp(String platform, String browser, String url) {
		if (browser.contains("remote")) { // remote-chrome, remote-firefox, remote-ie, remote-safari
			driver = getRemoteDriver(platform, browser, url);
		} else {
			driver = getDriver(browser, url);
		}
		getBrowserName = browser;
		mp = new MainPage(driver, log);
	}

	public String randomText() {
		Random rand = new Random();
		String randName = "";
		char[] letters = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
				'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
		for (int i = 0; i < 10; i++) {
			char randLet = letters[rand.nextInt(letters.length)];
			randName = randName + randLet;
		}
		return randName;
	}

	public WebDriver getDriver(String browser, String url) {
		switch (browser) {
		case "firefox":
			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "\\drivers\\geckodriver.exe");
			Reporter.log("Running Firefox");
			log.info("Running Firefox");
			driver = new FirefoxDriver();
			break;
		case "chrome":
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + "\\drivers\\chromedriver.exe");
			Reporter.log("Running Chrome");
			log.info("Running Chrome");
			driver = new ChromeDriver();
			break;
		case "ie":
		case "internet explorer":
			System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + "\\drivers\\IEDriverServer.exe");
			InternetExplorerOptions options = new InternetExplorerOptions();
			options.introduceFlakinessByIgnoringSecurityDomains();
			options.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);
			options.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
			Reporter.log("Running Ieternet Explorer");
			log.info("Running Ieternet Explorer");
			driver = new InternetExplorerDriver(options);
			break;
		case "mobile":
			Map<String, String> mobileEmulation = new HashMap<String, String>();
			mobileEmulation.put("deviceName", "Nexus 5");
			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + "\\drivers\\chromedriver.exe");
			Reporter.log("Running mobile device emulation on chrome");
			log.info("Running mobile device emulation on chrome");
			driver = new ChromeDriver(chromeOptions);
			break;
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, 10);
		Reporter.log("Navigating to " + url);
		log.info("Navigating to " + url);
		driver.get(url);
		return driver;
	}

	public WebDriver getRemoteDriver(String platform, String browser, String url) {
		String nodeURL = "http://localhost:4444/wd/hub";
		DesiredCapabilities caps = new DesiredCapabilities();
		log.info("Running remote driver " + browser);
		// Platforms
		switch (platform) {
		case "windows":
			caps.setPlatform(Platform.WINDOWS);
			break;
		case "mac":
			caps.setPlatform(Platform.MAC);
			break;
		case "linux":
			caps.setPlatform(Platform.LINUX);
			break;
		case "win10":
			caps.setPlatform(Platform.WIN10);
			break;
		case "win8":
			caps.setPlatform(Platform.WIN8);
			break;
		case "win8.1":
			caps.setPlatform(Platform.WIN8_1);
			break;
		case "any":
			caps.setPlatform(Platform.ANY);
			break;
		}
		// Browser
		if (browser.contains("chrome")) {
			ChromeOptions options = new ChromeOptions();
			caps.setBrowserName("chrome");
			caps.merge(options);
		}
		if (browser.contains("firefox")) {
			FirefoxOptions options = new FirefoxOptions();
			caps.setBrowserName("firefox");
			caps.merge(options);
		}
		if (browser.contains("ie")) {
			InternetExplorerOptions options = new InternetExplorerOptions();
			caps.setBrowserName("internet explorer");
			caps.merge(options);
		}
		if (browser.contains("safari")) {
			caps = DesiredCapabilities.safari();
			caps.setBrowserName("safari");
		}
		if (browser.contains("mobile")) {
			caps = DesiredCapabilities.chrome();
			Map<String, String> mobileEmulation = new HashMap<String, String>();
			mobileEmulation.put("deviceName", "Nexus 5");
			ChromeOptions options = new ChromeOptions();
			options.setExperimentalOption("mobileEmulation", mobileEmulation);
			caps.setBrowserName("chrome");
			caps.merge(options);
		}

		try {
			driver = new RemoteWebDriver(new URL(nodeURL), caps);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, 20);
		driver.get(url);
		Reporter.log("Navigating to " + url);
		log.info("Navigating to " + url);
		return driver;
	}

	@AfterClass
	public void afterClass() {
		log.info("Test finished\n");
		driver.quit();
	}

}
