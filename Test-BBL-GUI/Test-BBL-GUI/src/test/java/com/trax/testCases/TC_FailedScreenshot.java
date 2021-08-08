package com.trax.testCases;

import java.io.IOException;

import org.testng.annotations.Test;

import com.trax.pageObjects.BaseClass;
import com.trax.pageObjects.HomePage;

public class TC_FailedScreenshot extends BaseClass {

	@Test(alwaysRun = true)
	public void FailedScreenshot() throws IOException, InterruptedException {

		HomePage hp = new HomePage(driver);

		System.out.println("Started executing Test->'FailedScreenshot'");

		hp.chkHomepageURLTitle();
		hp.clickContactAndFail();

		System.out.println("Successfully executed Test->'FailedScreenshot'");

	}
}
