Feature: Tile Drawing
  Scenario: Tile Drawing
    Given We have non-empty tile deck
    When A tile is drawn
    Then The tile is removed from the deck.


