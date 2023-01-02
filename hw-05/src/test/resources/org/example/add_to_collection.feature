Feature: Add to collection
  Scenario: Click on add to collection button while on details page
    Given user is on book, with id "see-book-Git Pocket Guide", details page
    When user presses add to collection button
    Then the following message is displayed: "Book already present in the your collection!"
