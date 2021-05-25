Feature: Automation Practice app sign in flow

  Scenario: Navigate to the sign in page
    Given the user has navigated to the homepage
    When he tries to register
    Then he will see the page where he can register or log in

  Scenario Outline: Verify if the call us now number is displayed properly
    Given the user has navigated to the homepage
    Then he can confirm that the "<number>" in the header is present
    Examples:
      | number             |
      | 0123-456-789       |

