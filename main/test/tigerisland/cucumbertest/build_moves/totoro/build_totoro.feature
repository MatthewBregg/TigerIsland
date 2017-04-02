
  Feature: Build Totoro
  As a game
  I want to allow players to build totoros
  So that they can score point

  Scenario: Placing a totoro
    Given I A player has at least one totoro
    And Player has a settlement of size five or greater
    And That settlment does not have a totoro
    And There is a non-volcano hex adjacent to the settlement
    And And that hex does not have any game pieces on it
    When I Player builds totoro on that hex
    Then I Totoro is place on hex
    And player's totoro account is decrement by one