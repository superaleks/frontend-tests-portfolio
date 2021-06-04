Feature: Account creation flow test

  Scenario Outline: Verify if the call us now number is displayed properly
    Given the user has navigated to the homepage
    Then he can confirm that the "<number>" in the header is present
    Examples:
      | number             |
      | 0123-456-789       |

  Scenario: Create an account with correct data
    Given the user has navigated to the account creation page
    When he enters the email to the account creation form and proceeds to the account creation
    And he enters the all the necessary data in the form, generated randomly
    Then he can succesfully create an account


