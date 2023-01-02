Feature: Navigate to bookstore
  Scenario: Go to bookstore after having logged in
    Given user is logged in
    When presses bookstore button
    Then 8 books are shown
