Feature: Player Piece Information
  Scenario: Displaying a player’s initial Villager count
  Given No villagers have been placed by the player
  When Player attempts to view the amount of villagers available
  Then Player sees 20 villagers


  Scenario: Displaying  a player’s initial Totoro count
    Given No totoro have been placed by the player.
    When Player attempts to view the amount of totoros available
    Then Player sees 3 totoro

  Scenario: Displaying a player’s initial Tiger count
    Given No Tigers have been placed by the player.
    When  Player attempts to view the amount of Tigers available
    Then  Player sees 2 Tigers






