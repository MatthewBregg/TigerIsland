Feature: Tiles
  Scenario: Playing a tile when the board is empty
    Given No other tiles are on the board
    When A player attempts to place a tile on the board
    Then The tile is placed at the center of the board AND All the Hexes have level one


  Scenario: Placing a tile on level 1 when the board is not empty.
    Given The board is not empty
    When A player attempts to put a tile down at an unoccupied position AND the position has one edge in contact with another tiles' edge AND the placed tile has all hexes on the same level
    Then The tile is placed at that position on the board at level 1 AND At least one edge is adjacent to an existing tile, AND no tiles overlap