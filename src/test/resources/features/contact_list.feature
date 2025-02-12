Feature: Contact List Management
  As a logged in user
  I want to manage my contacts
  So that I can keep track of my contact information

  Background:
    Given I am logged in with valid credentials
    And I am on the contact list page

  @smoke @regression
  Scenario: View contact list page elements
    Then I should see the "Contact List App" header
    And I should see the "Add a New Contact" button
    And I should see the "Logout" button
    And I should see the contact table if contacts exist

  @smoke @regression @currentTest
  Scenario: Add a new contact with required fields
    When I click on "Add a New Contact" button
    And I fill in the following required contact details:
      | firstName | lastName | email           |
      | John      | Doe      | john@email.com  |
    And I click on "Submit" button
    Then I should see the contact in the list
    And the contact details should match the input

  @regression @currentTest
  Scenario: Add a new contact with all fields
    When I click on "Add a New Contact" button
    And I fill in the following contact details:
      | firstName | lastName | email           | phone        | address1    | address2  | city     | state | postalCode | country |
      | Jane      | Smith    | jane@email.com  | 1234567890  | 123 St     | Apt 4     | New York | NY    | 10001      | USA     |
    And I click on "Submit" button
    Then I should see the contact in the list
    And the contact details should match the input

# @regression @currentTest
# Scenario: View contact details
#   Given I have at least one contact in the list
#   When I click on a contact's name
#   Then I should see all the contact details
#   And the details should be correctly displayed

# @regression
# Scenario: Edit contact information
#   Given I have at least one contact in the list
#   When I click on a contact's name
#   And I click on "Edit Contact" button
#   And I update the following fields:
#      | firstName | phone      |
#      | Updated | 9876543210 |
#   And I click on "Submit" button
#   Then I should see the updated contact in the list
#   And the contact details should reflect the changes

# @regression
# Scenario: Delete a contact
#   Given I have at least one contact in the list
#   When I click on a contact's name
#   And I click on "Delete Contact" button
#   And I confirm the deletion
#   Then the contact should be removed from the list
#   And I should not see the deleted contact

# @regression
# Scenario: Cancel contact deletion
#   Given I have at least one contact in the list
#   When I click on a contact's name
#   And I click on "Delete Contact" button
#   But I cancel the deletion
#   Then the contact should remain in the list

# @regression
# Scenario: Validate required fields when adding contact
#   When I click on "Add a New Contact" button
#   And I click on "Submit" button without filling any fields
#   Then I should see error messages for required fields
#   And the contact should not be added to the list

# @regression
# Scenario: Validate email format when adding contact
#   When I click on "Add a New Contact" button
#   And I fill in the following contact details:
#      | firstName | lastName   | email   |
#     | Test      | User       | invalid |
#   And I click on "Submit" button
#   Then I should see an error message for invalid email
#   And the contact should not be added to the list

# @regression
# Scenario: Search functionality
#   Given I have multiple contacts in the list
#   When I enter a search term in the search box
#   Then I should only see contacts matching the search term
#   And non-matching contacts should be hidden

# @regression
# Scenario: Sort contacts
#   Given I have multiple contacts in the list
#   When I click on a column header
#   Then the contacts should be sorted by that column
#   And clicking again should reverse the sort order

# @regression
# Scenario: Logout from contact list
#   When I click on "Logout" button
#   Then I should be redirected to the login page
#   And I should not be able to access the contact list without logging in

# @regression
# Scenario Outline: Add contacts with different phone number formats
#   When I click on "Add a New Contact" button
#   And I fill in the following contact details:
#      | firstName | lastName | email   | phone   |
#      | Test              | Phone           | <email> | <phone> |
#   And I click on "Submit" button
#   Then the contact should be added successfully
#   And the phone number should be saved correctly

#   Examples:
#      | email             | phone           |
#      | test1@example.com | 123-456-7890    |
#      | test2@example.com | (123) 456-7890  |
#      | test3@example.com | 1234567890      |
#      | test4@example.com | +1-123-456-7890|

# @regression
# Scenario: Contact list pagination
#   Given I have more than 10 contacts in the list
#   Then I should see pagination controls
#   When I click on the next page
#   Then I should see the next set of contacts
#   And previous page should be accessible
