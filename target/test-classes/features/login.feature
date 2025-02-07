@LoginFeature
Feature: Login Functionality
  As a user of the Contact List application
  I want to be able to log in
  So that I can access my contacts

  Background:
    Given I am on the login page

  @successfullLogin
  Scenario: Successful login with valid credentials
    When I enter valid email "tester@tester3.com"
    And I enter valid password "SELman02"
    And I click the login submit button
    Then I should be logged in successfully

  @FailedLogin
  Scenario Outline: Failed login with invalid credentials
    When I enter login email "<email>"
    And I enter login password "<password>"
    And I click the login submit button
    Then I should see a login error message "<error_message>"

    Examples:
      | email            | password | error_message                  |
      | invalid@test.com | test123  | Incorrect username or password |
      | test@test.com    | wrong    | Incorrect username or password |
      |                  | test123  | Incorrect username or password |
      | test@test.com    |          | Incorrect username or password |

  Scenario: Navigate to sign up page
    When I click on the sign up link
    Then I should be redirected to the sign up page
