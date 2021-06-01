Feature: Sign in page test

#  Scenario: Navigate to the sign in page
#    Given the user has navigated to the homepage
#    When he navigates to the sign in form
#    Then he will see the page where he can register or log in
#
#  Scenario Outline: Verify if the call us now number is displayed properly
#    Given the user has navigated to the homepage
#    Then he can confirm that the "<number>" in the header is present
#    Examples:
#      | number             |
#      | 0123-456-789       |
#
#  Scenario: Initiate the account creation flow with the proper email address
#    Given the user has navigated to the account creation page
#    When he enters the "<email>" to the account creation form
#    Then he can initiate the account creation flow successfully

  Scenario Outline: Create an account with correct data
    Given the user has navigated to the account creation page
    When he enters the email to the account creation form and proceeds to the account creation
    And he enters the "<title>"
    #WIP"<lastName>","<password>","<dateOfBirth>", "<company", "<address>","<city>", "<state>", "<postalCode>", "<country>", "<mobilePhone>", "<futureReferenceAddress>"
    Then he can succesfully create an account
    Examples:
      | title |
      | Mrs   |




