Feature: Tiles
  Scenario: Tile Structure
    Given No other tiles are on the board
    When A player attempts to place a tile on the board
    Then The tile is placed at the center of the board AND All the Hexes have level one


