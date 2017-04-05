
Feature: Build Tiger
  As a game
  I want to allow players to build Tigers
  So that they can score points

  Scenario: Placing a tiger
    Given A player has at least 1 tiger
    And The player has a settlement that does not have a tiger
    And The build hex has a level >= 3
    And the build hex does not have any game pieces on it
    When Player builds tiger on that hex
    Then Tiger is placed on hex
    And The player's tiger amount is decremented by 1
    And The player's score is increased by 75