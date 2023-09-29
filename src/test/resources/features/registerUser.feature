@Regression @Post
Feature: Register a new user

	Background: 
	Given I have access to the endpoint GoRest

  @Positive
  Scenario Outline: Register user using valid data
    When I send post request using :
    	| name 		| 	<name> 		|
 #   	| email 	| 	<email> 	|			we'll use random email, we don't use the email below
    	| status 	| 	<status> 	|
    	| gender 	| 	<gender> 	|
    Then I will get response status code 201
    
    Examples: 
    |	name								| status				| gender		|
    |	"Matthew Mischer"		| "active"			| "male"		|
    |	"Ashley Broston"		| "active"			| "female"	|
    |	"Jack the Reaper"		| "active"			| "male"		|
    

  @Negative
  Scenario Outline: Register user using empty fields
    When I send post request with empty fields :
      | name 		| 	<name> 		|
    	| email 	| 	<email> 	|
    	| status 	| 	<status> 	|
    	| gender 	| 	<gender> 	|
    Then I will get error message with name of fields
    And I will get response status code 422

    Examples: 
    |	name								| email								| status				| gender		|
    |	""									| "empty@name.com"		|  "active"			| "male"		|		#empty name
    |	"Britney Hussel"		| ""									|  "active"			| "female"	|		#empty email
    |	"Ashley Broston"		| "empty@status.com"	|  ""						| "female"	|		#empty status
    |	"Jack the Reaper"		| "empty@gender.com"	|  "active"			| ""				|		#empty gender
    
    @Negative
    Scenario: Register user using registered email
    When I send post request using existed email
    Then I will get error message email has been taken
    And I will get response status code 422
    
    @Negative
    Scenario: Register user using invalid gender
    When I send post request using invalid gender
    Then I will get error message can be male or female
    And I will get response status code 422
     
