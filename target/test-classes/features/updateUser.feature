@Regression @Update
Feature: Feature for Update User

	Background:
    Given I have access to update user endpoint
    And I retreive all user data

  @Positive
  Scenario Outline: Able to update every fields
    When I Update a user using this data : 
    	| name			| <name>		|
#    	| email			| <email>		|				because email need to be unique, we use autogenerate email
    	| status		| <status>	|
    	| gender		| <gender>	|
    Then I get status code 200
  
  Examples:
  	| name						| status			| gender			|
  	| "mantap slur"		|	"inactive"	| "male"			|		#update all fields	
  
  @Positive
  Scenario Outline: unable to update if status or gender is empty
    When I Update a user using this data : 
    	| name			| <name>		|
#    	| email			| <email>		|				because email need to be unique, we use autogenerate email
    	| status		| <status>	|
    	| gender		| <gender>	|
    Then I get status code 422
  
  Examples:
  	| name						| status			| gender			|
  	| "mantap test"		|	""					| ""					|		#update only name
  	| ""							|	"active"		| ""					|		#update only status
  	| ""							|	""					| "male"			|		#update only gender
  
  
  @Negative
  Scenario: Unable to update status other than to active or inactive
    When I update user status to "idk"
    Then I will receive response message "can't be blank"
    And I get status code 422
	
	@Negative
  Scenario: Unable to update gender other than to male or female
    When I update user gender to "binary"
    Then I will receive response message "can be male of female"
    And I get status code 422
    
  @Negative
  Scenario: Unable to update id user
    When I update user id to 2349573
    Then the user id will remain the same
    And I get status code 200
    
  