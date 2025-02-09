@LoginFeature
Feature: Login Functionality
  As a user of the Contact List application
  I want to be able to log in
  So that I can access my contacts

  Background:
    Given I am on the login page

  @successfullLogin
  Scenario: Successful login with valid credentials
    When I enter valid email for environment
    And I enter valid password for environment
    And I click the login submit button
    Then I should be logged in successfully

  @FailedLogin
  Scenario Outline: Failed login with invalid credentials
    When I enter login email "<email>"
    And I enter login password "<password>"
    And I click the login submit button
    Then I should see a login error message "<error_message>"

    Examples:
      | email              | password | error_message                  |
      | invalid@email.com  | test123  | Invalid email address or password |
      | tester@tester3.com | wrong    | Invalid email address or password |
      | tester@tester3.com | test123  | Invalid email address or password |
      |                    | test123  | Email is required              |
      | test@example.com   |          | Password is required           |

  Scenario: Navigate to sign up page
    When I click on the sign up link
    Then I should be redirected to the sign up page
