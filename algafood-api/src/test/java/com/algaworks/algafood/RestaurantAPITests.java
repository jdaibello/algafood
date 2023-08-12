package com.algaworks.algafood;

import static io.restassured.RestAssured.enableLoggingOfRequestAndResponseIfValidationFails;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.algaworks.algafood.domain.model.Kitchen;
import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.repository.KitchenRepository;
import com.algaworks.algafood.domain.repository.RestaurantRepository;
import com.algaworks.algafood.util.DatabaseCleaner;
import com.algaworks.algafood.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@DirtiesContext
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class RestaurantAPITests {
	private static final String BUSINESS_RULE_VIOLATION_PROBLEM_TYPE = "Violação de regra de negócio";
	private static final String INVALID_DATA_PROBLEM_TITLE = "Dados inválidos";
	private static final int NON_EXISTING_RESTAURANT_ID = 100;

	private Restaurant burgerTopRestaurant;
	private String jsonCorrectRestaurant;
	private String jsonRestaurantWithoutShippingFee;
	private String jsonRestaurantWithoutKitchen;
	private String jsonRestaurantWithNonExistingKitchen;

	@LocalServerPort
	private int port;

	@Autowired
	private DatabaseCleaner databaseCleaner;

	@Autowired
	private KitchenRepository kitchenRepository;

	@Autowired
	private RestaurantRepository restaurantRepository;

	@BeforeEach
	public void setUp() {
		enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.basePath = "/restaurants";
		RestAssured.port = port;

		jsonCorrectRestaurant = ResourceUtils.getContentFromResource("/json/correct/new-york-barbecue-restaurant.json");

		jsonRestaurantWithoutShippingFee = ResourceUtils
				.getContentFromResource("/json/incorrect/new-york-barbecue-restaurant-without-shipping-fee.json");

		jsonRestaurantWithoutKitchen = ResourceUtils
				.getContentFromResource("/json/incorrect/new-york-barbecue-restaurant-without-kitchen.json");

		jsonRestaurantWithNonExistingKitchen = ResourceUtils
				.getContentFromResource("/json/incorrect/new-york-barbecue-restaurant-with-non-existing-kitchen.json");

		//databaseCleaner.clearTables();
		prepareData();
	}

	@Test
	void shouldReturnStatus200WhenGetRestaurants() {
		given().accept(ContentType.JSON).when().get().then().statusCode(HttpStatus.OK.value());
	}

	@Test
	void shouldReturnStatus201WhenSaveRestaurant() {
		given().body(jsonCorrectRestaurant).contentType(ContentType.JSON).accept(ContentType.JSON).when().post().then()
				.statusCode(HttpStatus.CREATED.value());
	}

	@Test
	void shouldReturnStatus400WhenSaveRestaurantWithoutShippingFee() {
		given().body(jsonRestaurantWithoutShippingFee).contentType(ContentType.JSON).accept(ContentType.JSON).when()
				.post().then().statusCode(HttpStatus.BAD_REQUEST.value())
				.body("title", equalTo(INVALID_DATA_PROBLEM_TITLE));
	}

	@Test
	void shouldReturnStatus400WhenSaveRestaurantWithoutKitchen() {
		given().body(jsonRestaurantWithoutKitchen).contentType(ContentType.JSON).accept(ContentType.JSON).when().post()
				.then().statusCode(HttpStatus.BAD_REQUEST.value()).body("title", equalTo(INVALID_DATA_PROBLEM_TITLE));
	}

	@Test
	void shouldReturnStatus400WhenSaveRestaurantWithNonExistingKitchen() {
		given().body(jsonRestaurantWithNonExistingKitchen).contentType(ContentType.JSON).accept(ContentType.JSON).when()
				.post().then().statusCode(HttpStatus.BAD_REQUEST.value())
				.body("title", equalTo(BUSINESS_RULE_VIOLATION_PROBLEM_TYPE));
	}

	@Test
	void shouldReturnResponseAndCorrectStatusWhenGetExistingRestaurant() {
		given().pathParam("restaurantId", burgerTopRestaurant.getId()).accept(ContentType.JSON).when()
				.get("/{restaurantId}").then().statusCode(HttpStatus.OK.value())
				.body("name", equalTo(burgerTopRestaurant.getName()));
	}

	@Test
	void shouldReturnStatus404WhenGetNonExistingRestaurant() {
		given().pathParam("restaurantId", NON_EXISTING_RESTAURANT_ID).accept(ContentType.JSON).when()
				.get("/{restaurantId}").then().statusCode(HttpStatus.NOT_FOUND.value());
	}

	private void prepareData() {
		Kitchen brazilianKitchen = new Kitchen();
		brazilianKitchen.setName("Brasileira");
		kitchenRepository.save(brazilianKitchen);

		Kitchen americanKitchen = new Kitchen();
		americanKitchen.setName("Americana");
		kitchenRepository.save(americanKitchen);

		burgerTopRestaurant = new Restaurant();
		burgerTopRestaurant.setName("Burger Top");
		burgerTopRestaurant.setShippingFee(new BigDecimal(10));
		burgerTopRestaurant.setKitchen(americanKitchen);
		restaurantRepository.save(burgerTopRestaurant);

		Restaurant comidaMineiraRestaurant = new Restaurant();
		comidaMineiraRestaurant.setName("Comida Mineira");
		comidaMineiraRestaurant.setShippingFee(new BigDecimal(10));
		comidaMineiraRestaurant.setKitchen(brazilianKitchen);
		restaurantRepository.save(comidaMineiraRestaurant);
	}
}
