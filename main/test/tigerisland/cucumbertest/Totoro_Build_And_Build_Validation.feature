
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