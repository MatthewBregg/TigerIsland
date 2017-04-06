Feature: Tiles
  Scenario: Tile Structure
    Given Forty Eight Tiles have been drawn and there are no more tiles remaining
    When A tile is attempted to be drawn.
    Then No tiles are available to be drawn, a tile is not drawn. The game is over.


