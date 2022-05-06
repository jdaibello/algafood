package com.algaworks.algafood;

import static io.restassured.RestAssured.enableLoggingOfRequestAndResponseIfValidationFails;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class KitchenAPITests {

	@LocalServerPort
	private int port;

	@BeforeEach
	void setUp() {
		enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.basePath = "/kitchens";
		RestAssured.port = port;
	}

	@Test
	void shouldReturnStatus200WhenGetKitchens() {
		given().accept(ContentType.JSON).when().get().then().statusCode(HttpStatus.OK.value());
	}

	@Test
	void shouldHave4KitchensWhenGetKitchens() {
		given().accept(ContentType.JSON).when().get().then().body("", hasSize(4)).body("name",
				hasItems("Indiana", "Tailandesa"));
	}
}
