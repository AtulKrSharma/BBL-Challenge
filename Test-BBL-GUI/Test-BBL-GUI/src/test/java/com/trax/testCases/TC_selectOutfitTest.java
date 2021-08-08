package com.trax.testCases;

import java.io.IOException;

import org.testng.annotations.Test;

import com.trax.pageObjects.BaseClass;
import com.trax.pageObjects.HomePage;

public class TC_selectOutfitTest extends BaseClass {

	@Test(alwaysRun = true)
	public void selectOutfitTest() throws IOException, InterruptedException {

		HomePage hp = new HomePage(driver);

		System.out.println("Started executing Test->'selectOutfitTest'");

		hp.chkHomepageURLTitle();
		hp.hoverMenuWomenAndSelectOutfit();
		hp.selectedOutfitAndAddToCart();

		System.out.println("Successfully executed Test->'selectOutfitTest'");

	}
}
