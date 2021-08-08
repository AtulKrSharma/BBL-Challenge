package com.trax.utilities;

//Listener class used to generate Extent reports

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.trax.pageObjects.BaseClass;

public class Reporting extends BaseClass implements ITestListener {

	public ExtentHtmlReporter htmlReporter;
	public ExtentReports extent;
	public ExtentTest parentTest, childTest;

	public String screenshotName = null;
	public static ArrayList<String> methodList = new ArrayList<String>();

	public String getHost() throws UnknownHostException {

		String hostName = InetAddress.getLocalHost().getHostName();
		return hostName;

	}

	@Override
	public void onStart(ITestContext testContext) {

		String reportName = "Extent-Report.html";

		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "//Reports//" + reportName);
		// specify // location // of // the // report
		htmlReporter.loadXMLConfig(System.getProperty("user.dir") + "/extent-config.xml");

		extent = new ExtentReports();

		extent.attachReporter(htmlReporter);
		try {
			extent.setSystemInfo("Hostname", getHost());
		} catch (UnknownHostException e) {

			e.printStackTrace();
		}
		// extent.setSystemInfo("Environment", envName);
		// extent.setSystemInfo("User", userName);

		htmlReporter.config().setDocumentTitle("KORE-TRAX"); // Tile of report
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP); // location of the chart
		htmlReporter.config().setTheme(Theme.DARK);

	}

	@Override
	public void onTestStart(ITestResult result) {

		String instance = result.getInstanceName().toString();
		String methodname = result.getMethod().toString();

		System.out.println("instance-->" + instance);
		System.out.println("methodname-->" + methodname);

		String finalInstance = instance.substring(instance.lastIndexOf("_") + 1).trim();
		System.out.println("finalInstance-->" + finalInstance);

		String finalmethodname = methodname.substring(methodname.indexOf(".") + 1).trim();
		finalmethodname = finalmethodname.substring(0, finalmethodname.lastIndexOf("()")).trim();
		System.out.println("finalmethodname-->" + finalmethodname);

		if ((chkAndAddArray(finalInstance))) {
			return;
		}

		if (finalmethodname.equalsIgnoreCase(finalInstance.toString())) {
			parentTest = extent.createTest(finalmethodname);
		} else {
			parentTest = extent.createTest(finalInstance);
		}
	}

	boolean chkAndAddArray(String instance) {

		if (methodList.contains(instance)) {
			System.out.println(instance + " Value is already present");
			return true;
		} else
			methodList.add(instance);
		System.out.println(instance + "Value is not present, so added");
		return false;
	}

	@Override
	public void onTestSuccess(ITestResult tr) {

		System.out.println("onTestSuccess");

		childTest = parentTest.createNode(tr.getName());
		// childTest.log(Status.PASS, MarkupHelper.createLabel(tr.getName(),
		// ExtentColor.GREEN));

		try {
			screenshotName = captureScreen(tr.getName());
			System.out.println("TEST PASSED- Screenshot taken");
		} catch (IOException e1) {

			e1.printStackTrace();
		}

		String screenshotPath = ".//" + screenshotName + ".png";

		System.out.println(screenshotPath);
		try {
			childTest.pass("Screenshot is below:",
					MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
		} catch (IOException e1) {

			e1.printStackTrace();
		}

	}

	@Override
	public void onTestFailure(ITestResult tr)

	{

		System.out.println("onTestFailure");

		childTest = parentTest.createNode(tr.getName());
		childTest.log(Status.FAIL, MarkupHelper.createLabel(tr.getName(), ExtentColor.RED));
		// childTest.fail(tr.getThrowable());

		try {
			screenshotName = captureScreen(tr.getName());
			System.out.println("TEST FAILED- Screenshot taken");
		} catch (IOException e1) {

			e1.printStackTrace();
		}

		String screenshotPath = ".//" + screenshotName + ".png";

		System.out.println(screenshotPath);
		try {
			childTest.fail("Screenshot is below:",
					MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
		} catch (IOException e1) {

			e1.printStackTrace();

		}

		childTest.fail(tr.getThrowable());

	}

	@Override
	public void onTestSkipped(ITestResult tr) {
		System.out.println("onTestSkipped");

		childTest = parentTest.createNode(tr.getName());
		childTest.log(Status.SKIP, MarkupHelper.createLabel(tr.getName(), ExtentColor.ORANGE));
		childTest.skip(tr.getThrowable());

	}

	@Override
	public void onFinish(ITestContext testContext) {

		extent.flush();
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub

	}

}
