@Regression	@Delete
Feature: Delete User Feature

  @Positive
  Scenario: Delete Registered User
	  Given I have retrieve all user from endpoint get user
	  And I select a registered user
    When I hit endpoint delete using registered user id
    Then response status code is 204
    And I cannot find the user when I re-retrieve all user

  @Negative
  Scenario: Delete Unregistered User
  	Given I have access to endpoint delete
    When I hit endpoint delete using unregistered user id
    Then I will receive a message "Resource not found"
    And response status code is 404

