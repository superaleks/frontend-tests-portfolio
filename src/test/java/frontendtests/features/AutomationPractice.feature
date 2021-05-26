Feature: Automation Practice

  Scenario: Navigate to the sign in page
    Given the user has navigated to the homepage
    When he navigates to the sign in form
    Then he will see the page where he can register or log in

  Scenario Outline: Verify if the call us now number is displayed properly
    Given the user has navigated to the homepage
    Then he can confirm that the "<number>" in the header is present
    Examples:
      | number             |
      | 0123-456-789       |

  Scenario Outline: Create an account with a proper email adress
    Given the user has navigated to the account creation page
    When he enters the "<email>" to the account creation form
    Then he can create an account succesfully
    Examples:
      | email                        |
      | aleksandravlukic@yopmail.com |


