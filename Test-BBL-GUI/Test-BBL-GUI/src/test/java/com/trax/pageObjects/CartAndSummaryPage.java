package com.trax.pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class CartAndSummaryPage extends HomePage {

	WebDriver ldriver;

	public static String valueOfStorePartsId, valueOfStorePartsAvlQty;

	public CartAndSummaryPage(WebDriver rdriver) {
		super(rdriver);
		ldriver = rdriver;
		PageFactory.initElements(rdriver, this);
	}

	// pageObjects of page
	@FindBy(how = How.LINK_TEXT, using = "Parts Toronto Warehouse")
	WebElement lnkPartsTorontoWarehouse;

	WebElement txtStorePartsId4;
	@FindBy(how = How.XPATH, using = "(.//table[@class='tsReviewTable']/.//tbody/.//div[@class='selectRow']/a) [last()-24]")
	WebElement txtStorePartsId5;

///

	@FindBy(how = How.CSS, using = "span[title='Continue shopping']")
	WebElement btnContinueShopping;

	@FindBy(how = How.XPATH, using = "(.//a[@title='Proceed to checkout']) [last()]")
	WebElement btnProceedToCheckout;

	@FindBy(how = How.XPATH, using = "//table[@id='cart_summary']/.//tr/td[@id='total_product']")
	WebElement lblSummaryOutfitPrice;

	// methods of the page

	public void proceedToCheckout() {

		// Capture the price
		String valueOfSummaryOutfitPriceStr = valueofSpan(lblSummaryOutfitPrice);
		System.out.println("valueOfOutfitPrice-->" + valueOfSummaryOutfitPriceStr);

		// remove $$ prior to float conversion
		valueOfSummaryOutfitPriceStr = valueOfSummaryOutfitPriceStr.replace("$", "");

		System.out.println("valueOfOutfitPriceStr-->" + valueOfSummaryOutfitPriceStr);

		// String --> Float
		float valueOfSummaryOutfitPriceFlt = Float.parseFloat(valueOfSummaryOutfitPriceStr);

		System.out.println("valueOfSummaryOutfitPriceFlt-->" + valueOfSummaryOutfitPriceFlt);

		Assert.assertEquals(valueOfSummaryOutfitPriceFlt, valueOfOutfitPriceFlt);

	}

}
