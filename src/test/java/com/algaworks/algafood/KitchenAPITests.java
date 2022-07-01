package com.algaworks.algafood;

import static io.restassured.RestAssured.enableLoggingOfRequestAndResponseIfValidationFails;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
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
import com.algaworks.algafood.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class KitchenAPITests {
	private static final int NON_EXISTING_KITCHEN_ID = 100;

	private Kitchen americanKitchen;
	private int numberOfRegisteredKitchens;
	private String correctJsonChineseKitchen;

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

		correctJsonChineseKitchen = ResourceUtils.getContentFromResource("/json/correct/chinese-kitchen.json");
	}

	@Test
	void shouldReturnStatus200WhenGetKitchens() {
		given().accept(ContentType.JSON).when().get().then().statusCode(HttpStatus.OK.value());
	}

	@Test
	void shouldReturnCorrectQuantityOfKitchensWhenGetKitchens() {
		given().accept(ContentType.JSON).when().get().then().body("", hasSize(numberOfRegisteredKitchens));
	}

	@Test
	void shouldReturnStatus201WhenSaveKitchen() {
		given().body(correctJsonChineseKitchen).contentType(ContentType.JSON).accept(ContentType.JSON).when().post()
				.then().statusCode(HttpStatus.CREATED.value());
	}

	@Test
	void shouldReturnResponseAndCorrectStatusWhenGetExistingKitchen() {
		given().pathParam("kitchenId", americanKitchen.getId()).accept(ContentType.JSON).when().get("/{kitchenId}")
				.then().statusCode(HttpStatus.OK.value()).body("name", equalTo(americanKitchen.getName()));
	}

	@Test
	void shouldReturnStatus400WhenGetNonExistingKitchen() {
		given().pathParam("kitchenId", NON_EXISTING_KITCHEN_ID).accept(ContentType.JSON).when().get("/{kitchenId}")
				.then().statusCode(HttpStatus.NOT_FOUND.value());
	}

	private void prepareData() {
		Kitchen kitchen1 = new Kitchen();
		kitchen1.setName("Tailandesa");
		kitchenRepository.save(kitchen1);

		americanKitchen = new Kitchen();
		americanKitchen.setName("Americana");
		kitchenRepository.save(americanKitchen);

		numberOfRegisteredKitchens = (int) kitchenRepository.count();
	}
}
