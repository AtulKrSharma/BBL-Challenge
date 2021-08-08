package com.trax.pageObjects;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import com.trax.utilities.ReadConfig;

public class BaseClass {

	static ReadConfig readconfig = new ReadConfig();
	public static String testURL = readconfig.getTestURL();
	public static WebDriver driver;

	@Parameters("browser")
	@BeforeClass(alwaysRun = true)
	public void setup(String br) {

		if (br.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver", readconfig.getWinChromePath());
			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.addArguments("--no-sandbox");
			chromeOptions.addArguments("--disable-dev-shm-usage");
			chromeOptions.addArguments("--disable-notifications");
			chromeOptions.addArguments("--disable-extenstions");
			chromeOptions.addArguments("disable-infobars");
			// chromeOptions.addArguments("force-device-scale-factor=0.65");
			// chromeOptions.addArguments("high-dpi-support=0.65");
			chromeOptions.addArguments("--ignore-ssl-errors=yes");
			chromeOptions.addArguments("--ignore-certificate-errors");
			chromeOptions.addArguments("--allow-insecure-localhost");
			chromeOptions.addArguments("--allow-running-insecure-content");
			chromeOptions.addArguments("--unsafely-treat-insecure-origin-as-secure");

			driver = new ChromeDriver(chromeOptions);

		} else if (br.contentEquals("chromeHeadless")) {

			System.setProperty("webdriver.chrome.driver", readconfig.getWinChromePath());
			ChromeOptions chromeOptions = new ChromeOptions();

			chromeOptions.addArguments("--headless");
			chromeOptions.addArguments("--disable-gpu");
			chromeOptions.addArguments("--no-sandbox");
			chromeOptions.addArguments("--disable-dev-shm-usage");
			chromeOptions.addArguments("--disable-notifications");
			chromeOptions.addArguments("--disable-extenstions");
			chromeOptions.addArguments("disable-infobars");
			chromeOptions.addArguments("--window-size=1920x1080");
			chromeOptions.addArguments("force-device-scale-factor=0.65");
			chromeOptions.addArguments("high-dpi-support=0.65");
			driver = new ChromeDriver(chromeOptions);
		}

		// Implicit wait
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();

		System.out.println("Window height: " + driver.manage().window().getSize().getHeight());
		System.out.println("Window width: " + driver.manage().window().getSize().getWidth());

		driver.get(testURL);
		System.out.println("Target URL of TRAX application: " + driver.getCurrentUrl());

	}

	@AfterClass
	public void tearDown() {

		if (driver != null)
			driver.quit();
	}

	public String captureScreen(String tname) throws IOException {

		String datetime = new SimpleDateFormat("yyyy-MMdd-hhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);

		String screenshotName = tname + "_" + datetime;
		File target = new File(System.getProperty("user.dir") + "/Reports/" + screenshotName + ".png");

		System.out.println(target.toString());
		FileUtils.copyFile(source, target);
		System.out.println("++Screenshot taken++");
		return screenshotName;

	}

	public void jsClickExecutor(WebElement element) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click();", element);

	}

	public void jsScrollBottomOfPage() throws InterruptedException {
		JavascriptExecutor jse = ((JavascriptExecutor) driver);
		jse.executeScript("window.scrollTo(0,document.body.scrollHeight)");
		Thread.sleep(500);
	}

	public void jsScrollTopOfPage() throws InterruptedException {
		JavascriptExecutor jse = ((JavascriptExecutor) driver);
		jse.executeScript("window.scrollTo(document.body.scrollHeight, 0)");
		Thread.sleep(500);
	}

	public void jsScrollIntoViewExecutor(WebElement element) throws InterruptedException {

		JavascriptExecutor jse = ((JavascriptExecutor) driver);
		String js = "arguments[0].scrollIntoView(true);";
		jse.executeScript(js, element);
		Thread.sleep(500);

	}

	public String valueofDiv(WebElement element) {

		String valueofDivElement = element.getAttribute("innerHTML");
		System.out.println("valueofDivElement" + element + "is " + valueofDivElement);

		if (valueofDivElement != null && !valueofDivElement.trim().isEmpty())
			return valueofDivElement;
		else
			return null;

	}

	public String valueofText(WebElement element) {

		String valueofTextElement = element.getAttribute("value");
		System.out.println("valueofTextElement" + element + "is " + valueofTextElement);

		if (valueofTextElement != null && !valueofTextElement.trim().isEmpty())
			return valueofTextElement;
		else
			return null;

	}

	public String valueofSpan(WebElement element) {

		String valueofSpanElement = element.getText();
		System.out.println("valueofSpanElement" + element + "is " + valueofSpanElement);

		if (valueofSpanElement != null && !valueofSpanElement.trim().isEmpty())
			return valueofSpanElement;
		else
			return null;

	}



}
