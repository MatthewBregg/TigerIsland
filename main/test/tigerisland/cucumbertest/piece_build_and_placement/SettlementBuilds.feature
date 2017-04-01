Feature: Building Settlements

Scenario:
Founding a settlement
Given Player has at least 1 villager
When Player attempts to place the villager on a non-volcano hex
And the hex has a level of 1
And the hex is unoccupied
  Then
  A new settlement of size 1 is formed on that tile, one villager is subtracted from players villager count, and appears on that tile.

  Scenario
  Expanding a settlement
    Given
    Player has an existing settlement
    And is attempting a build action
    And player has sufficient villagers
    When
    Player completes the expansion
    Then
    Player’s villagers are placed on all connected hexes of that terrain type
    And the villagers placed on each hex are equal to the level of that hex
    And player’s villagers are decremented by an amount equal to the number of villagers placed
