Feature: PlacingPieces
  As a game
  I want to allow players to place tigers
  So that they can score points. I want these Builds to be valid.

  Scenario: Placing a tiger
    Given A player has at least 1 tiger
    And The player has a settlement that does not have a tiger
    And The build hex has a level >= 3
    And the build hex does not have any game pieces on it
    When Player builds tiger on that hex
    Then Tiger is placed on hex
    And The player's tiger amount is decremented by 1
    And The player's score is increased by 75

#  Scenario:
#  Placing a tiger on a volcano hex
#    Given
#    Player has a  tiger
#    And is attempting a build action
#    When
#    Player attempts to place said villager or totoro or tiger during a build action onto a hex with a volcano.
#    Then
#    Invalid build action
#
#
#  Scenario:
#  Placing a tiger on a occupied hex
#    Given
#    Player has a villager or totoro and is attempting a build action
#    When
#    Player attempts to place said villager or totoro on a hex that  is already occupied by a totoro or tiger  or villager
#    Then
#    Invalid build action

#  Scenario
#  Placing a totoro in a settlement that already has one
#    Given
#    Player has a totoro.
#    And player has a settlement of size 5 or greater with 1+ totoro in it
#    When
#    Player attempts to place a totoro on a hex adjacent to that settlement
#    Then
#    Invalid move. (Also remember that settlements are combined at end of turn.)
#
