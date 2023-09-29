package steps;

import static org.junit.Assert.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import Entities.User;
import helper.ConfigProperties;
import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class DeleteUserSteps {

	private String baseURI = ConfigProperties.getProperty("BASE_URL");
	private String apiToken = ConfigProperties.getProperty("API_TOKEN");
	private Response response;
	private RequestSpecification requestSpec;
	private User user;
	
	@Given("I have retrieve all user from endpoint get user")
	public void i_have_retrieve_all_user_from_endpoint_get_user(){
		requestSpec = RestAssured.given();
		requestSpec.baseUri(baseURI+"/users");
		requestSpec.header("Authorization", apiToken);
		requestSpec.header("Content-Type", "application/json");
		
		response = requestSpec.get();
	}

	@Given("I select a registered user")
	public void i_select_a_registered_user() throws JsonMappingException, JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonArray = objectMapper.readTree(response.asString());

        for (JsonNode jsonNode : jsonArray) {
        	String id = jsonNode.get("id").asText();
        	String name = jsonNode.get("name").asText();
            String email = jsonNode.get("email").asText();
            String status = jsonNode.get("status").asText();
            String gender = jsonNode.get("gender").asText();
            user = new User(Integer.valueOf(id),name,email,status,gender);
            break;
        }
	}

	@When("I hit endpoint delete using registered user id")
	public void i_hit_endpoint_delete_using_registered_user_id() {
		requestSpec.baseUri(baseURI+"/users");
		response = requestSpec.delete("/"+user.getId());
	}

	@Then("I cannot find the user when I re-retrieve all user")
	public void i_cannot_find_the_user_when_i_re_retrieve_all_user() {
		i_have_retrieve_all_user_from_endpoint_get_user();
	    String responseToString = response.asString();
	    boolean isUserStillExist = responseToString.contains(String.valueOf(user.getId()));
	    assertFalse(isUserStillExist);
	}

	@Given("I have access to endpoint delete")
	public void i_have_access_to_endpoint_delete() {
		requestSpec = RestAssured.given();
		requestSpec.baseUri(baseURI+"/users");
		requestSpec.header("Authorization", apiToken);
		requestSpec.header("Content-Type", "application/json");
	}

	@When("I hit endpoint delete using unregistered user id")
	public void i_hit_endpoint_delete_using_unregistered_user_id() {
		requestSpec.baseUri(baseURI+"/users/"+1234901234);
		response = requestSpec.delete();
	}

	@Then("I will receive a message {string}")
	public void i_will_receive_a_message(String message) {
	    String responseToString = response.asString();
	    boolean isMessageSame = responseToString.contains(message);
	    assertTrue(isMessageSame);
	}

	@Then("response status code is {int}")
	public void response_status_code_is(int statusCode) {
	    int actualStatusCode = response.getStatusCode();
	    assertEquals(statusCode,actualStatusCode);
	}

}
