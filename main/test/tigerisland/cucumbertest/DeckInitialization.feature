Feature: Deck Initialization
  Scenario: Deck Initialization
    Given We have no deck
    When The deck is initialized
    Then The deck contains 48 tiles, 16 unique combinations