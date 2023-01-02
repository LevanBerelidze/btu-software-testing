Feature: Log In
  Scenario: Credentials are correct
    Given user is on log in page
    When enters credentials
    When clicks log in button
    Then log out button appears
