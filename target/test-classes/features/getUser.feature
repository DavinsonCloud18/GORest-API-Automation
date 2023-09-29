@Regression @Get
Feature: Get user details

	Background:
  Given I have access to the baseURI GoRest

  @Positive
  Scenario: Get all users
    When I send a request to API get User 
    Then I will get response containing all registered user
    And the response status code i get is 200

  @Positive
  Scenario: Get a registered user using email
    When I send a request to API get User with body containing an registered email
    Then I get response containing user with same email
    And the response status code i get is 200
    
  @Positive
  Scenario: Get a registered user using id
    When I send a request to API get User with body containing an registered user id
    Then I get response containing user with same id
    And the response status code i get is 200
    
  @Negative
  Scenario: Get an unregistered user using email
    When I send a request to API get User with body containing an unregistered email
    Then I get empty data
    And the response status code i get is 200
  
  @Negative
  Scenario: Get an unregistered user using id
    When I send a request to API get User with body containing an unregistered user id
    Then I get empty data
    And the response status code i get is 200
    
  @Negative
  Scenario: Get user using invalid body request
    When I send a request to API get User with invalid body request
    Then I get error message "Error occurred"
    And the response status code i get is 400

    