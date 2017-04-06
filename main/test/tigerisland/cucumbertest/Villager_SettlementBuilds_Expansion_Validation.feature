Feature: Building Settlements
  As a game, I want to allow players to build settlements

  Scenario: Founding a settlement
    Given Player has at least 1 villager
    When Player attempts to place the villager on a non-volcano hex
    And the hex has a level of 1
    And the hex is unoccupied
    Then A new settlement of size 1 is formed on that tile, one villager is subtracted from players villager count, and appears on that tile




  #  Scenario:
#  Placing a villager/totoro/tiger on a volcano hex
#    Given
#    Player has a villager or totoro or tiger
#    And is attempting a build action
#    When
#    Player attempts to place said villager or totoro or tiger during a build action onto a hex with a volcano.
#    Then
#    Invalid build action
#
#  Scenario:
#  Placing a villager/totoro/tiger on a occupied hex
#    Given
#    Player has a villager or totoro and is attempting a build action
#    When
#    Player attempts to place said villager or totoro on a hex that  is already occupied by a totoro or tiger  or villager
#    Then
#    Invalid build action

#  Scenario
#  Expanding a settlement
#    Given
#    Player has an existing settlement
#    And is attempting a build action
#    And player has sufficient villagers
#    When
#    Player completes the expansion
#    Then
#    Player’s villagers are placed on all connected hexes of that terrain type
#    And the villagers placed on each hex are equal to the level of that hex
#    And player’s villagers are decremented by an amount equal to the number of villagers placed
