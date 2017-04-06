
Feature: PlacingPieces
  As a game
  I want to allow players to place tigers and totoro
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

  Scenario: Placing a totoro
    Given A player has at least one totoro
    And Player has a settlement of size five or greater
    And That settlment does not have a totoro
    And There is a non-volcano hex adjacent to the settlement
    And that hex does not have any game pieces on it
    When Player builds totoro on that hex
    Then Totoro is placed on hex
    And player's totoro account is decremented by one