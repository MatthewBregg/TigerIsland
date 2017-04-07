
Feature: Placing Pieces
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
  #Scenario: Placing a tiger on a volcano hex
  #  Given Player has a  tiger
  #  And is attempting a build action
  #  And there is a tile of level three or more
  #  When Player attempts to place tiger onto a hex with a volcano on said tile
  #  Then Invalid build action


  Scenario: Placing a villager on a volcano hex
    Given Player has a  villager
    And is attempting a build action
    When Player attempts to place villager onto a hex with a volcano on said tile
    Then Invalid build action

#  Scenario:
#  Placing a tiger on a occupied hex
#    Given
#    Player has a villager or totoro and is attempting a build action
#    When
#    Player attempts to place said villager or totoro on a hex that  is already occupied by a totoro or tiger  or villager
#    Then
#    Invalid build action
#
#
  Scenario:Placing a totoro on a occupied hex
    Given Player has a  totoro
    When Player attempts to place totoro on a hex that is already occupied by a totoro or tiger  or villager
    Then Invalid build action

