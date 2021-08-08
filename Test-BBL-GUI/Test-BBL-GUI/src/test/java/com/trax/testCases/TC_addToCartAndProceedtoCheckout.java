package com.trax.testCases;

import java.io.IOException;

import org.testng.annotations.Test;

import com.trax.pageObjects.CartAndSummaryPage;

//Test Case:1

public class TC_addToCartAndProceedtoCheckout extends TC_selectOutfitTest {

	@Test(dependsOnMethods = { "selectOutfitTest" }, alwaysRun = true)
	public void addToCartAndProceedtoCheckout() throws IOException, InterruptedException {

		// Objects of the pages needed by the this workflow
		CartAndSummaryPage csp = new CartAndSummaryPage(driver);

		System.out.println("Started executing Test->'TC_addToCartAndProceedtoCheckout'");

		csp.proceedToCheckout();

		System.out.println("Successfully executed Test->'TC_addToCartAndProceedtoCheckout'");

	}
}
