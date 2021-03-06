Feature: Tile_Placement

#  Scenario: Placing a tile onto another tile to add a new level
#    Given It’s the player’s turn
#    And the board is not empty
#    When Player places the new tile on top of the old tiles
#    Then The tile is placed on the board
#    And all of the placed hexes have a level of ( 1 plus the level of the hex below )
#    And all of the placed hexes are on the same level
#    And all villagers on any covered hexes are destroyed and removed from the game
#
#  Scenario:Fail to place tile due to overlap with tiles of different levels or game board (level 0)
#    Given the board is not empty
#    When A player attempts to put a tile down at an unoccupied position
#    And 1 or more of the hexes would not be at the same level of the other tile hexes at that position.
#    Then Invalid placement, tile is not placed.
#
#  Scenario :Fail to place tile due to no adjacent edge
#    Given the board is not empty
#    When A player attempts to put a tile down at an unoccupied position
#    And the position does not have any edges touching existing tiles
#    Then Invalid placement, tile is not placed.


#  Scenario: A tile cannot be placed directly on top of another tile
#    Given The board is not empty, and there is one tile placed somewhere
#    When A player attempts to place a tile on top of said tile
#    Then Failure occurs

