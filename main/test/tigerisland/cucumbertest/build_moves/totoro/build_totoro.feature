
  Feature: Build Totoro
  As a game
  I want to allow players to build totoros
  So that they can score points

  Scenario: Placing a totoro
    Given A player has at least one totoro
    And player has a settlement of size five or greater
    And that settlment does not have a totoro
    And there is a non-volcano hex adjacent to the settlement
    And and that hex does not have any game pieces on it
    When player builds totoro on that hex
    Then totoro is place on that hex
    And player's totoro account is decrement by one