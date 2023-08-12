package com.algaworks.algafood;

import static io.restassured.RestAssured.enableLoggingOfRequestAndResponseIfValidationFails;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.algaworks.algafood.domain.model.Kitchen;
import com.algaworks.algafood.domain.repository.KitchenRepository;
import com.algaworks.algafood.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@DirtiesContext
public class KitchenAPITest {
	private static final int NON_EXISTING_KITCHEN_ID = 100;

	private Kitchen americanKitchen;
	private int numberOfRegisteredKitchens;
	private String jsonCorrectChineseKitchen;
	private String allowedUserToken;

	@Value("${server.port}")
	private int port;

	@Autowired
	private KitchenRepository kitchenRepository;

	@BeforeEach
	void setUp() {
		enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;

		String clientId = "algafood-mobile";
		String clientSecret = "mobile123";
		String grantType = "password";
		String allowedUserEmail = "joao.ger@algafood.com";
		String allowedUserPassword = "123";

		allowedUserToken = given()
				.auth().preemptive().basic(clientId, clientSecret)
				.contentType(ContentType.URLENC.withCharset("UTF-8"))
				.formParam("grant_type", grantType)
				.formParam("username", allowedUserEmail)
				.formParam("password", allowedUserPassword)
				.when()
				.post("http://localhost:" + port + "/oauth/token")
				.then()
				.statusCode(HttpStatus.OK.value())
				.extract().path("access_token");

		RestAssured.basePath = "/kitchens";

		prepareData();

		jsonCorrectChineseKitchen = ResourceUtils.getContentFromResource("/json/correct/chinese-kitchen.json");
	}

	@Test
	void shouldReturnStatus200WhenGetKitchens() {
		given().header("Authorization", "Bearer " + allowedUserToken).accept(ContentType.JSON).when().get()
				.then().statusCode(HttpStatus.OK.value());
	}

	@Test
	void shouldReturnCorrectQuantityOfKitchensWhenGetKitchens() {
		given().header("Authorization", "Bearer " + allowedUserToken).accept(ContentType.JSON).when().get()
				.then().body("_embedded.kitchens", hasSize(numberOfRegisteredKitchens));
	}

	@Test
	void shouldReturnStatus201WhenSaveKitchen() {
		given().header("Authorization", "Bearer " + allowedUserToken).body(jsonCorrectChineseKitchen)
				.contentType(ContentType.JSON).accept(ContentType.JSON).when().post()
				.then().statusCode(HttpStatus.CREATED.value());
	}

	@Test
	void shouldReturnResponseAndCorrectStatusWhenGetExistingKitchen() {
		given().header("Authorization", "Bearer " + allowedUserToken)
				.pathParam("kitchenId", americanKitchen.getId()).accept(ContentType.JSON).when().get("/{kitchenId}")
				.then().statusCode(HttpStatus.OK.value()).body("name", equalTo(americanKitchen.getName()));
	}

	@Test
	void shouldReturnStatus400WhenGetNonExistingKitchen() {
		given().header("Authorization", "Bearer " + allowedUserToken)
				.pathParam("kitchenId", NON_EXISTING_KITCHEN_ID).accept(ContentType.JSON).when().get("/{kitchenId}")
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
