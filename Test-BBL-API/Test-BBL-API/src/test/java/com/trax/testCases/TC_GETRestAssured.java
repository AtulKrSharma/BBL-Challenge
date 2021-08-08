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
import junit.framework.Assert;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class TC_GETRestAssured {

	@Test()
	public void GETRestAssured() {

		System.out.println("Started executing Test->'GETRestAssured'");

		String apiURL = "https://swapi.dev/api/people/1/";

		// equal to - single matcher
		// has items - multiple matchers
		given().get(apiURL).then().statusCode(200).body("name", equalTo("Luke Skywalker"))
				.body("films", hasItems("https://swapi.dev/api/films/1/", "https://swapi.dev/api/films/6/")).and().log()
				.body();

		System.out.println("Successfully executed Test->'GETRestAssured'");

	}
}
