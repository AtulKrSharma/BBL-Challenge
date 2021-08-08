package com.trax.testCases;

import java.io.IOException;

import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import com.google.gson.JsonObject;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.response.ValidatableResponseOptions;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class TC_POSTRestAssured {

	@Test()
	public void POSTRestAssured() throws IOException, InterruptedException {

		System.out.println("Started executing Test->'TC_POSTRestAssured'");

		JSONObject req = new JSONObject();
		req.put("name", "Atul");
		req.put("job", "QALeads");

		System.out.println(req.toString());
		given().header("Content-Type", "application/json").body(req.toJSONString()).when()
				.post("https://reqres.in/api/users").then().statusCode(201).and().assertThat()
				.body(containsStringIgnoringCase("createdAt"), containsString("id"), containsString("name"));

		System.out.println("Started executing Test->'TC_POSTRestAssured'");

	}
}
