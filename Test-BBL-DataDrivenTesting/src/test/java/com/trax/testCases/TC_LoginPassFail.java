package com.trax.testCases;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.trax.pageObjects.BaseClass;
import com.trax.pageObjects.HomePage;

public class TC_LoginPassFail extends BaseClass {

	@Test(dataProvider = "data")
	public void LoginPassFail(String data) throws IOException, InterruptedException {

		HomePage hp = new HomePage(driver);

		System.out.println("Started executing Test->'LoginPassFail'");

		hp.clickSignIn();

		String users[] = data.split(",");
		hp.loginUnamePwd(users[0], users[1]);

		System.out.println("Successfully executed Test->'LoginPassFail'");

	}

	@DataProvider(name = "data")
	public String[] readJson() throws IOException, ParseException {	

		JSONParser parser = new JSONParser();
		FileReader reader = new FileReader(
				System.getProperty("user.dir") + "/src/test/java/com/trax/testData/LoginTestData.json");

		Object obj = parser.parse(reader);
		JSONObject userLoginsObj = (JSONObject) obj;
		JSONArray userLoginArray = (JSONArray) userLoginsObj.get("userlogins");

		String arr[] = new String[userLoginArray.size()];
		for (int i = 0; i < userLoginArray.size(); i++)

		{
			JSONObject users = (JSONObject) userLoginArray.get(i);
			String user = (String) users.get("uname");
			String password = (String) users.get("pwd");
			arr[i] = user + "," + password;
		}
		return arr;

	}

}
