package steps;

import static org.junit.Assert.*;

import helper.ConfigProperties;
import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class GetUserSteps {
	
	private String baseURI = ConfigProperties.getProperty("BASE_URL");
	private String apiToken = ConfigProperties.getProperty("API_TOKEN");
	private Response response;
	private RequestSpecification requestSpec;
	
	private String registeredEmail = "tester@gmail.com";
	private int registeredId = 5224642;
	
	
	@Given("I have access to the baseURI GoRest")
	public void setConfiguration() {
		requestSpec = RestAssured.given();
		requestSpec.baseUri(baseURI);
		requestSpec.header("Authorization", apiToken);
		requestSpec.header("Content-Type", "application/json");
	}
	
	@When("I send a request to API get User")
	public void i_send_a_request_to_api_get_user() {
		this.response = requestSpec.get("/users");
	}

	@Then("I will get response containing all registered user")
	public void i_will_get_response_containing_all_registered_user() {
	    String[] attributes = {"name","email","status","gender"};
	    boolean isResponseContains;
	    for(int i=0;i<attributes.length;i++) {
	    	isResponseContains = response.asString().contains(attributes[i]);
	    	assertTrue(isResponseContains);
	    }
	}

	@When("I send a request to API get User with body containing an registered email")
	public void i_send_a_request_to_api_get_user_with_body_containing_an_registered_email() {
		String bodyRequest =	"{"
									+ "\"email\"" +": "+ "\"" + registeredEmail + "\"" +
								"}";
		this.response = requestSpec.body(bodyRequest).get("/users");
	}

	@Then("I get response containing user with same email")
	public void i_get_response_containing_user_with_same_email() {
	    String responseToString = response.asString();
	    boolean isEmailContained = responseToString.contains(registeredEmail);
	    assertTrue(isEmailContained);
	}

	@When("I send a request to API get User with body containing an registered user id")
	public void i_send_a_request_to_api_get_user_with_body_containing_an_registered_user_id() {
		String bodyRequest =	"{"
									+ "\"id\"" +": " + "\"" + registeredId + "\"" +
								"}";
		this.response = requestSpec.body(bodyRequest).get("/users");
	}

	@Then("I get response containing user with same id")
	public void i_get_response_containing_user_with_same_id() {
		String responseToString = response.asString();
	    boolean isIdContained = responseToString.contains(Integer.toString(registeredId));
	    assertTrue(isIdContained);
	}

	@When("I send a request to API get User with body containing an unregistered email")
	public void i_send_a_request_to_api_get_user_with_body_containing_an_unregistered_email() {
		String unregisteredEmail = "asdfunasdlf@gaslgjalsdfg.com";
		String bodyRequest =	"{"
									+ "\"email\"" +": "+ "\"" + unregisteredEmail + "\"" +
								"}";
		this.response = requestSpec.body(bodyRequest).get("/users");
	}

	@Then("I get empty data")
	public void i_get_empty_data() {
		String responseToString = response.asString();
		assertEquals("[]",responseToString);
	}

	@When("I send a request to API get User with body containing an unregistered user id")
	public void i_send_a_request_to_api_get_user_with_body_containing_an_unregistered_user_id() {
		Integer unregisteredId = 123408917;
		String bodyRequest =	"{"
									+ "\"id\"" +":"+ "\"" + unregisteredId + "\"" +
								"}";
		this.response = requestSpec.body(bodyRequest).get("/users");
	}

	@When("I send a request to API get User with invalid body request")
	public void i_send_a_request_to_api_get_user_with_invalid_body_request() {
	    String invalidBodyRequest = "adsfasfdaf";
	    this.response = requestSpec.body(invalidBodyRequest).get("/users");
	}

	@Then("I get error message {string}")
	public void i_get_error_message(String string) {
	    String responseToString = response.asString();
	    boolean isMessageTheSame = responseToString.contains(string);
	    assertTrue(isMessageTheSame);
	}
	
	@Then("the response status code i get is {int}")
	public void the_response_status_code_i_get_is(int statusCode) {
		int actualStatusCode = response.getStatusCode();
		assertEquals(statusCode,actualStatusCode);
	}

}
