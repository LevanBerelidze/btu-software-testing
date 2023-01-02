Feature: Open book details
  Scenario: Click on the book list item after navigating to the store
    Given user is on the bookstore page
    When presses the list item with id "see-book-Git Pocket Guide"
    Then book title is "Git Pocket Guide"
