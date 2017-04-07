
Feature: Placing Totoro
  As a game
  I want to allow players to place tigers and totoro
  So that they can score points. I want these Builds to be valid.


  Scenario: Placing a totoro
    Given A player has at least one totoro
    And Player has a settlement of size five or greater
    And That settlment does not have a totoro
    And There is a non-volcano hex adjacent to the settlement
    And that hex does not have any game pieces on it
    When Player builds totoro on that hex
    Then Totoro is placed on hex
    And player's totoro account is decremented by one


  Scenario: Placing a totoro on a volcano hex
    Given Player has a  totoro
    And is attempting a build action
    When Player attempts to place totoro onto a hex with a volcano.
    Then Invalid build action
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
#Scenario
#  Placing a totoro in a settlement that already has one
#    Given
#    Player has a totoro.
#    And player has a settlement of size 5 or greater with 1+ totoro in it
#    When
#    Player attempts to place a totoro on a hex adjacent to that settlement
#    Then
#    Invalid move. (Also remember that settlements are combined at end of turn.)

