package steps;

import static org.junit.Assert.*;

import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import Entities.User;
import helper.ConfigProperties;
import helper.RandomEmailGenerator;
import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class UpdateUserSteps {
	
	private String baseURI = ConfigProperties.getProperty("BASE_URL");
	private String apiToken = ConfigProperties.getProperty("API_TOKEN");
	private Response response;
	private RequestSpecification requestSpec;
	private User user;
	private String bodyRequest;
	
	@Given("I have access to update user endpoint")
	public void i_have_access_to_update_user_endpoint() {
		requestSpec = RestAssured.given();
		requestSpec.baseUri(baseURI+"/users");
		requestSpec.header("Authorization", apiToken);
		requestSpec.header("Content-Type", "application/json");
	}
	
	@Given("I retreive all user data")
	public void i_retreive_all_user_data() {
		response = requestSpec.get();
	}

	@When("I Update a user using this data :")
	public void i_update_a_user_using_this_data(io.cucumber.datatable.DataTable dataTable) throws JsonMappingException, JsonProcessingException {
		this.user = createUserfromJson(response.asString());
		
		Map<String,String> userData = dataTable.asMap(String.class,String.class);
	    user.setName(userData.get("name"));
	    user.setEmail(RandomEmailGenerator.generateRandomEmail());
	    user.setStatus(userData.get("status"));
	    user.setGender(userData.get("gender"));
	    
	    bodyRequest = 
	    	"{ "
	    	+ "\"name\": " 	+ user.getName() 	+ 	", "
	    	+ "\"email\": " + "\"" + user.getEmail() + "\", "
	    	+ "\"status\": "+ user.getStatus() 	+ 	", "
	    	+ "\"gender\": "+ user.getGender() 	+ 	
	    	" }";

	    requestSpec.body(bodyRequest);
	    this.response = requestSpec.patch("/"+user.getId());
	}

	@Then("I get status code {int}")
	public void i_get_status_code(Integer statusCode) {
		Integer actualStatusCode = response.getStatusCode();
		assertEquals(statusCode,actualStatusCode);
	}

	@When("I update user status to {string}")
	public void i_update_user_status_to(String string) throws JsonMappingException, JsonProcessingException {
		i_retreive_all_user_data();
		this.user = createUserfromJson(response.asString());
		requestSpec.baseUri(baseURI+"/users");
		
		bodyRequest = 
		    	"{ "
		    	+ "\"status\": "+ "\"" + string + "\"" + 	
		    	" }";
		
		requestSpec.body(bodyRequest); 
	    response = requestSpec.patch("/"+user.getId());
	}
	
	@When("I update user gender to {string}")
	public void i_update_user_gender_to(String gender) throws JsonMappingException, JsonProcessingException {
		i_retreive_all_user_data();
		this.user = createUserfromJson(response.asString());
		requestSpec.baseUri(baseURI+"/users");
		
		bodyRequest = 
		    	"{ "
		    	+ "\"gender\": "+ "\"" + gender + "\"" + 	
		    	" }";
		
		requestSpec.body(bodyRequest); 
	    response = requestSpec.patch("/"+user.getId());
	}

	@Then("I will receive response message {string}")
	public void i_will_receive_response_message(String message) {
	    String responseToString = response.asString();
	    boolean isMessageCorrect = responseToString.contains(message);
	    assertTrue(isMessageCorrect);
	}

	@When("I update user id to {int}")
	public void i_update_user_id_to(Integer id) throws JsonMappingException, JsonProcessingException {
		i_retreive_all_user_data();
		this.user = createUserfromJson(response.asString());
		requestSpec.baseUri(baseURI+"/users");
		
		bodyRequest = 
		    	"{ "
		    	+ "\"id\": "+ "\"" + id + "\"" + 
		    	" }";
		
		requestSpec.body(bodyRequest);
	    response = requestSpec.patch("/"+user.getId());
	}

	@Then("the user id will remain the same")
	public void the_user_id_will_remain_the_same() {
        boolean isIdSame = response.asString().contains(String.valueOf(user.getId()));
        assertTrue(isIdSame);
	}
	
	public User createUserfromJson(String json) throws JsonMappingException, JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonArray = objectMapper.readTree(response.asString());
        
        User tempUser = null;
        
        for (JsonNode jsonNode : jsonArray) {
        	String id = jsonNode.get("id").asText();
        	String name = jsonNode.get("name").asText();
            String email = jsonNode.get("email").asText();
            String status = jsonNode.get("status").asText();
            String gender = jsonNode.get("gender").asText();
            tempUser = new User(Integer.valueOf(id),name,email,status,gender);
            break;
        }
        
        return tempUser;
	}
}
