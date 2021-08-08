package com.trax.pageObjects;

import static org.testng.Assert.assertTrue;

import java.text.DecimalFormat;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class HomePage extends BaseClass {

	WebDriver ldriver;

	public static float valueOfOutfitPriceFlt;

	public HomePage(WebDriver rdriver) {

		ldriver = rdriver;
		PageFactory.initElements(rdriver, this);
	}

	// objects of the page
	@FindBy(how = How.CSS, using = "a[title='Women']")
	WebElement menuWomen;

	@FindBy(how = How.XPATH, using = "//a[contains(text(),'Blouses') and @title='Blouses']")
	WebElement submenuBlouses;

	@FindBy(how = How.XPATH, using = "//span[contains(text(),'Blouses') and @class='category-name']")
	WebElement lblCategoryBlouse;

	@FindBy(how = How.XPATH, using = "//a[@class='product_img_link']/img[@title='Blouse']")
	WebElement imgSelectedOutfit;

	@FindBy(how = How.XPATH, using = "//span[contains(text(),'Quick view') and @class='quick-view']")
	WebElement lblSelectedOutfit;

	@FindBy(how = How.XPATH, using = "//p[@id='product_reference']/label")
	WebElement lblModel;

	@FindBy(how = How.XPATH, using = "//p[@id='product_condition']/label")
	WebElement lblCondition;

	@FindBy(how = How.XPATH, using = "//p[@id='product_reference']/span")
	WebElement lblModelValue;

	@FindBy(how = How.XPATH, using = "//p[@id='product_condition']/span")
	WebElement lblConditionValue;

	@FindBy(how = How.ID, using = "our_price_display")
	WebElement lblPrice;

	@FindBy(how = How.ID, using = "quantity_wanted")
	WebElement inputQtyWanted;

	@FindBy(how = How.ID, using = "group_1")
	WebElement ddSize;

	@FindBy(how = How.CSS, using = "button[name='Submit']")
	WebElement btnAddToCart;

	@FindBy(how = How.ID, using = "contact-link")
	WebElement divContact;

	@FindBy(how = How.XPATH, using = "//textarea[@id='message']")
	WebElement txtMessage;

	@FindBy(how = How.CSS, using = "a[title*='Log in'][class='login']")
	WebElement lnkSignIn;

	@FindBy(how = How.ID, using = "SubmitLogin")
	WebElement btnSignIn;

	@FindBy(how = How.ID, using = "email")
	WebElement txtEmail;

	@FindBy(how = How.ID, using = "passwd")
	WebElement txtPassword;

	public void chkHomepageURLTitle() {

		String currentURL = ldriver.getCurrentUrl();
		String homepageTitle = ldriver.getTitle();

		// Assert.assertEquals(currentURL, testURL);
		assertTrue(currentURL.contains("http://automationpractice.com"));
		Assert.assertEquals(homepageTitle, "My Store");

	}

	public void hoverMenuWomenAndSelectOutfit() throws InterruptedException {

		Actions builder = new Actions(ldriver);
		builder.moveToElement(menuWomen).build().perform();
		jsClickExecutor(submenuBlouses);
		// submenuBlouses.click();

		lblCategoryBlouse.isDisplayed();

		jsScrollIntoViewExecutor(imgSelectedOutfit);
		imgSelectedOutfit.click();

	}

	public void selectedOutfitAndAddToCart() throws InterruptedException {

		// WebDriverWait wait = new WebDriverWait(ldriver, 30);
		// wait.until(ExpectedConditions.visibilityOf(lblModel));

		/*
		 * lblModel.isDisplayed(); lblCondition.isDisplayed();
		 * 
		 * String valueOfModel = valueofSpan(lblModelValue);
		 * Assert.assertEquals(valueOfModel, "demo_2");
		 * 
		 * String valueOfCondition = valueofSpan(lblConditionValue);
		 * Assert.assertEquals(valueOfCondition, "New");
		 */

		// Capture the price
		String valueOfOutfitPriceStr = valueofSpan(lblPrice);
		System.out.println("valueOfOutfitPrice-->" + valueOfOutfitPriceStr);

		// remove $$ prior to float conversion
		valueOfOutfitPriceStr = valueOfOutfitPriceStr.replace("$", "");

		System.out.println("valueOfOutfitPriceStr-->" + valueOfOutfitPriceStr);

		// String --> Float
		valueOfOutfitPriceFlt = Float.parseFloat(valueOfOutfitPriceStr);

		// Qty
		inputQtyWanted.clear();
		inputQtyWanted.sendKeys("1");

		// Size
		Select ddPhaseValue = new Select(ddSize);
		ddPhaseValue.selectByVisibleText("L");

		btnAddToCart.submit();

	}

	public void clickContactAndFail() throws InterruptedException {

		divContact.click();
		txtMessage.sendKeys("This is dummy Test Case to Show the failed TC-Screeenshots in 'Reports' directory");

		imgSelectedOutfit.click();

	}

	public void clickSignIn() {

		lnkSignIn.click();
		btnSignIn.isDisplayed();

	}

	public void loginUnamePwd(String uname, String pwd) throws InterruptedException {

		txtEmail.sendKeys(uname);
		txtPassword.sendKeys(pwd);
		btnSignIn.submit();

		Thread.sleep(5000);

		String actualURL = ldriver.getCurrentUrl();
		Assert.assertEquals(actualURL, "http://automationpractice.com/index.php?controller=my-account");

	}
}
