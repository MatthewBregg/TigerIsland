Feature: Deck
  Scenario: Deck Initialization
    Given We have no deck
    When The deck is initialized
    Then The deck contains 48 tiles, 16 unique combinations


  Scenario: Tile Drawing
    Given We have non-empty tile deck
    When A tile is drawn
    Then The tile is removed from the deck.
