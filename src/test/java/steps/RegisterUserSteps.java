package steps;

import static org.junit.Assert.*;

import java.util.Map;

import Entities.User;
import helper.ConfigProperties;
import helper.RandomEmailGenerator;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RegisterUserSteps {
	 
	private String baseURI = ConfigProperties.getProperty("BASE_URL");
	private String apiToken = ConfigProperties.getProperty("API_TOKEN");
	private Response response;
	private RequestSpecification requestSpec;
	private User user;
	
	
	@Given("I have access to the endpoint GoRest")
	public void i_have_access_to_endpoint_gorest() {
		requestSpec = RestAssured.given();
		requestSpec.baseUri(baseURI);
		requestSpec.header("Authorization", apiToken);
		requestSpec.header("Content-Type", "application/json");
	}

	@When("I send post request using :")
	public void i_send_post_request_using(DataTable dataTable) {
	    Map<String,String> userData = dataTable.asMap(String.class,String.class);
	    String name = userData.get("name");
	    String email = RandomEmailGenerator.generateRandomEmail();
	    String status = userData.get("status");
	    String gender = userData.get("gender");
	    
	    user = new User(name,email,status,gender);
	    
	    String bodyRequest = 
	    	"{ "
	    	+ "\"name\": " 	+ user.getName() 	+ 	", "
	    	+ "\"email\": " + "\"" + user.getEmail() + "\", "
	    	+ "\"status\": "+ user.getStatus() 	+ 	", "
	    	+ "\"gender\": "+ user.getGender() 	+ 	
	    	" }";

	    requestSpec.body(bodyRequest);
	    this.response = requestSpec.post("/users");
	}

	@Then("I will get response status code {int}")
	public void i_will_get_response_status_code(int statusCode) {
		 int actualStatusCode = response.getStatusCode();
		 assertEquals(statusCode,actualStatusCode);
	}

	@When("I send post request with empty fields :")
	public void i_send_post_request_with_empty_fields(DataTable dataTable) {
		Map<String,String> userData = dataTable.asMap(String.class,String.class);
	    String name = userData.get("name");
	    String email = userData.get("email");
	    String status = userData.get("status");
	    String gender = userData.get("gender");
	    
	    user = new User(name,email,status,gender);
	    
	    String bodyRequest = 
	    	"{ "
	    	+ "\"name\": " 	+ user.getName() 	+ 	", "
	    	+ "\"email\": " + user.getEmail() 	+ 	", "
	    	+ "\"status\": "+ user.getStatus() 	+ 	", "
	    	+ "\"gender\": "+ user.getGender() 	+ 	
	    	" }";

	    requestSpec.body(bodyRequest);
	    this.response = requestSpec.post("/users");
	}

	@Then("I will get error message with name of fields")
	public void i_will_get_error_message_with_name_of_fields() {
		 String[] emptyFields = this.user.checkEmptyAttributes(user);
		 boolean isMessageContainsFieldsName;
		 for(int i=0;i<emptyFields.length;i++) {
			 isMessageContainsFieldsName = response.asString().contains(emptyFields[i]);
			 assertTrue(isMessageContainsFieldsName);
		 }
	}

	@When("I send post request using existed email")
	public void i_send_post_request_using_existed_email() {
//		name, status, and gender is not needed to be valid since 
//		we are checking the existence of registered email
		user = new User("test","tester@gmail.com","active","male");
	    
	    String bodyRequest = 
	    	"{ "
	    	+ "\"name\": " 	+ "\"" + user.getName() + "\", "
	    	+ "\"email\": " + "\"" + user.getEmail() + "\", "
	    	+ "\"status\": "+ "\"" + user.getStatus() + "\", "
	    	+ "\"gender\": "+ "\"" + user.getGender() +	"\"" +
	    	" }";

	    requestSpec.body(bodyRequest);
	    this.response = requestSpec.post("/users");
	}

	@Then("I will get error message email has been taken")
	public void i_will_get_error_message_email_has_been_taken() {
		 String message = "has already been taken";
		 boolean isMessageExisted = response.asString().contains(message);
		 assertTrue(isMessageExisted);
	}

	@When("I send post request using invalid gender")
	public void i_send_post_request_using_invalid_gender() {
//		name, email, and gender is not needed to be valid since 
//		we are checking the validity of gender, accepted gender only male or female
		user = new User("test","tester@gmail.com","active","aihihihi");
	    
	    String bodyRequest = 
	    	"{ "
	    	+ "\"name\": " 	+ "\"" + user.getName() + "\", "
	    	+ "\"email\": " + "\"" + user.getEmail() + "\", "
	    	+ "\"status\": "+ "\"" + user.getStatus() + "\", "
	    	+ "\"gender\": "+ "\"" + user.getGender() +	"\"" +
	    	" }";

	    requestSpec.body(bodyRequest);
	    this.response = requestSpec.post("/users");
	}

	@Then("I will get error message can be male or female")
	public void i_will_get_error_message_can_be_male_or_female() {
		String message = "can be male of female";
		boolean isMessageExisted = response.asString().contains(message);
		assertTrue(isMessageExisted);
	}

}
