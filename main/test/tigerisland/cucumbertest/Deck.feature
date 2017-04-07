Feature: Deck
  Scenario: Deck Finite
    Given Forty Eight Tiles have been drawn and there are no more tiles remaining
    When A tile is attempted to be drawn.
    Then No tiles are available to be drawn, a tile is not drawn. The game is over.

  Scenario: Deck Initialization
    Given We have no deck
    When The deck is initialized
    Then The deck contains 48 tiles, 16 unique combinations


  Scenario: Tile Drawing
    Given We have non-empty tile deck
    When A tile is drawn
    Then The tile is removed from the deck.

