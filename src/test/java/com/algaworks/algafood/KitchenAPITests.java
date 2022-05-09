package com.algaworks.algafood;

import static io.restassured.RestAssured.enableLoggingOfRequestAndResponseIfValidationFails;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.algaworks.algafood.domain.model.Kitchen;
import com.algaworks.algafood.domain.repository.KitchenRepository;
import com.algaworks.algafood.util.DatabaseCleaner;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class KitchenAPITests {

	@LocalServerPort
	private int port;

	@Autowired
	private DatabaseCleaner databaseCleaner;

	@Autowired
	private KitchenRepository kitchenRepository;

	@BeforeEach
	void setUp() {
		enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.basePath = "/kitchens";
		RestAssured.port = port;

		// TODO: Fix error when cleaning tables
		databaseCleaner.clearTables();
		prepareData();
	}

	@Test
	void shouldReturnStatus200WhenGetKitchens() {
		given().accept(ContentType.JSON).when().get().then().statusCode(HttpStatus.OK.value());
	}

	@Test
	void shouldHave2KitchensWhenGetKitchens() {
		given().accept(ContentType.JSON).when().get().then().body("", hasSize(2));
	}

	@Test
	void shouldReturnStatus201WhenSaveKitchen() {
		given().body("{ \"name\": \"Chinesa\" }").contentType(ContentType.JSON).accept(ContentType.JSON).when().post()
				.then().statusCode(HttpStatus.CREATED.value());
	}

	private void prepareData() {
		Kitchen kitchen1 = new Kitchen();
		kitchen1.setName("Tailandesa");
		kitchenRepository.save(kitchen1);

		Kitchen kitchen2 = new Kitchen();
		kitchen2.setName("Americana");
		kitchenRepository.save(kitchen2);
	}
}
