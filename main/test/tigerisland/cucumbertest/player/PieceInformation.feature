Feature: Player Piece Information
  Scenario: Displaying a player’s initial Villager count
  Given Fresh Player with no pieces placed
  When Player attempts to view the amount of villagers available
  Then Player sees 20 of above piece


  Scenario: Displaying  a player’s initial Totoro count
    Given Fresh Player with no pieces placed
    When Player attempts to view the amount of totoros available
    Then Player sees 3 of above piece

  Scenario: Displaying a player’s initial Tiger count
    Given Fresh Player with no pieces placed
    When  Player attempts to view the amount of Tigers available
    Then  Player sees 2 of above piece






