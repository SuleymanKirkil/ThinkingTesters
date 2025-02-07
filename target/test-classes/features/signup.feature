Feature: Sign Up Functionality
  As a new user of the Contact List application
  I want to be able to create an account
  So that I can manage my contacts

  Background:
    Given I am on the sign up page

  Scenario: Successful sign up with valid information
    When I enter first name "Johny"
    And I enter last name "Doey"
    And I enter sign up email "johny.doey@example.com"
    And I enter sign up password "Test123!"
    And I click the sign up submit button
    Then I should be registered successfully
    And I should be redirected to the contact list page
    Then I sholud delete user through api request

  Scenario: Cancel sign up process
    When I click the cancel button
    Then I should be redirected to the login page

  Scenario Outline: Validation errors for required fields
    When I enter first name "<firstName>"
    And I enter last name "<lastName>"
    And I enter sign up email "<email>"
    And I enter sign up password "<password>"
    And I click the sign up submit button
    Then I should see an error message "<errorMessage>"

    Examples:
      | firstName | lastName | email              | password | errorMessage           |
      |          | Doe      | john@example.com   | Test123! | First Name is required |
      | John     |          | john@example.com   | Test123! | Last Name is required  |
      | John     | Doe      |                    | Test123! | Email is required      |
      | John     | Doe      | john@example.com   |          | Password is required   |

  Scenario Outline: Email validation
    When I enter first name "John"
    And I enter last name "Doe"
    And I enter sign up email "<email>"
    And I enter sign up password "Test123!"
    And I click the sign up submit button
    Then I should see an error message "Invalid email format"

    Examples:
      | email         |
      | invalid.email |
      | @example.com  |
      | john@        |
      | john@.com    |

  Scenario: Sign up with already registered email
    When I enter first name "John"
    And I enter last name "Doe"
    And I enter sign up email "existing@example.com"
    And I enter sign up password "Test123!"
    And I click the sign up submit button
    Then I should see an error message "Email address is already registered"

  Scenario Outline: Password validation
    When I enter first name "John"
    And I enter last name "Doe"
    And I enter sign up email "john.doe@example.com"
    And I enter sign up password "<password>"
    And I click the sign up submit button
    Then I should see an error message "<errorMessage>"

    Examples:
      | password | errorMessage                                          |
      | test     | Password must be at least 8 characters               |
      | 12345678 | Password must contain at least one letter            |
      | abcdefgh | Password must contain at least one number            |

  Scenario: Field length validation
    When I enter first name "ThisIsAVeryLongFirstNameThatExceedsTheMaximumAllowedLength"
    And I enter last name "ThisIsAVeryLongLastNameThatExceedsTheMaximumAllowedLength"
    And I enter sign up email "john.doe@example.com"
    And I enter sign up password "Test123!"
    And I click the sign up submit button
    Then I should see an error message containing "maximum length"
