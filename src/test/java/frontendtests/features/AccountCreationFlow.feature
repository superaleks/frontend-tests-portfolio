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
#  Scenario Outline: Initiate the account creation flow with the proper email address
#    Given the user has navigated to the account creation page
#    When he enters the "<email>" to the account creation form
#    Then he can initiate the account creation flow successfully
#    Examples:
#      | email                        |
#      | aleksandralukic1@yopmail.com |
#
#  Scenario Outline: Initiate the account creation flow with an incorrectt email address
#    Given the user has navigated to the account creation page
#    When he enters the "<incorrectEmail>" to the account creation form
#    Then he sees an error message
#    Examples:
#      | incorrectEmail               |
#      | aleksandravlukic@yopmail     |

  Scenario Outline: Create an account with correct data
    Given the user has navigated to the account creation page
    When he enters the "<email>" to the account creation form and proceeds to the account creation
    And he enters all of the needed information
    #WIP"<lastName>","<password>","<dateOfBirth>", "<company", "<address>","<city>", "<state>", "<postalCode>", "<country>", "<mobilePhone>", "<futureReferenceAddress>"
    Then he can succesfully create an account
    Examples:
      | email                         | title | firstName  |
      | aleksandralukic5@yopmail.com  | Mrs  | Aleksansdra |




